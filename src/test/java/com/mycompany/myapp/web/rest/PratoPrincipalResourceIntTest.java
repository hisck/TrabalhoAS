package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.UniticketApp;

import com.mycompany.myapp.domain.PratoPrincipal;
import com.mycompany.myapp.repository.PratoPrincipalRepository;
import com.mycompany.myapp.service.PratoPrincipalService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PratoPrincipalResource REST controller.
 *
 * @see PratoPrincipalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UniticketApp.class)
public class PratoPrincipalResourceIntTest {

    private static final String DEFAULT_NOME_DO_PRATO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DO_PRATO = "BBBBBBBBBB";

    @Autowired
    private PratoPrincipalRepository pratoPrincipalRepository;

    @Autowired
    private PratoPrincipalService pratoPrincipalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPratoPrincipalMockMvc;

    private PratoPrincipal pratoPrincipal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PratoPrincipalResource pratoPrincipalResource = new PratoPrincipalResource(pratoPrincipalService);
        this.restPratoPrincipalMockMvc = MockMvcBuilders.standaloneSetup(pratoPrincipalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PratoPrincipal createEntity(EntityManager em) {
        PratoPrincipal pratoPrincipal = new PratoPrincipal()
            .nomeDoPrato(DEFAULT_NOME_DO_PRATO);
        return pratoPrincipal;
    }

    @Before
    public void initTest() {
        pratoPrincipal = createEntity(em);
    }

    @Test
    @Transactional
    public void createPratoPrincipal() throws Exception {
        int databaseSizeBeforeCreate = pratoPrincipalRepository.findAll().size();

        // Create the PratoPrincipal
        restPratoPrincipalMockMvc.perform(post("/api/prato-principals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pratoPrincipal)))
            .andExpect(status().isCreated());

        // Validate the PratoPrincipal in the database
        List<PratoPrincipal> pratoPrincipalList = pratoPrincipalRepository.findAll();
        assertThat(pratoPrincipalList).hasSize(databaseSizeBeforeCreate + 1);
        PratoPrincipal testPratoPrincipal = pratoPrincipalList.get(pratoPrincipalList.size() - 1);
        assertThat(testPratoPrincipal.getNomeDoPrato()).isEqualTo(DEFAULT_NOME_DO_PRATO);
    }

    @Test
    @Transactional
    public void createPratoPrincipalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pratoPrincipalRepository.findAll().size();

        // Create the PratoPrincipal with an existing ID
        pratoPrincipal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPratoPrincipalMockMvc.perform(post("/api/prato-principals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pratoPrincipal)))
            .andExpect(status().isBadRequest());

        // Validate the PratoPrincipal in the database
        List<PratoPrincipal> pratoPrincipalList = pratoPrincipalRepository.findAll();
        assertThat(pratoPrincipalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeDoPratoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pratoPrincipalRepository.findAll().size();
        // set the field null
        pratoPrincipal.setNomeDoPrato(null);

        // Create the PratoPrincipal, which fails.

        restPratoPrincipalMockMvc.perform(post("/api/prato-principals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pratoPrincipal)))
            .andExpect(status().isBadRequest());

        List<PratoPrincipal> pratoPrincipalList = pratoPrincipalRepository.findAll();
        assertThat(pratoPrincipalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPratoPrincipals() throws Exception {
        // Initialize the database
        pratoPrincipalRepository.saveAndFlush(pratoPrincipal);

        // Get all the pratoPrincipalList
        restPratoPrincipalMockMvc.perform(get("/api/prato-principals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pratoPrincipal.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDoPrato").value(hasItem(DEFAULT_NOME_DO_PRATO.toString())));
    }
    
    @Test
    @Transactional
    public void getPratoPrincipal() throws Exception {
        // Initialize the database
        pratoPrincipalRepository.saveAndFlush(pratoPrincipal);

        // Get the pratoPrincipal
        restPratoPrincipalMockMvc.perform(get("/api/prato-principals/{id}", pratoPrincipal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pratoPrincipal.getId().intValue()))
            .andExpect(jsonPath("$.nomeDoPrato").value(DEFAULT_NOME_DO_PRATO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPratoPrincipal() throws Exception {
        // Get the pratoPrincipal
        restPratoPrincipalMockMvc.perform(get("/api/prato-principals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePratoPrincipal() throws Exception {
        // Initialize the database
        pratoPrincipalService.save(pratoPrincipal);

        int databaseSizeBeforeUpdate = pratoPrincipalRepository.findAll().size();

        // Update the pratoPrincipal
        PratoPrincipal updatedPratoPrincipal = pratoPrincipalRepository.findById(pratoPrincipal.getId()).get();
        // Disconnect from session so that the updates on updatedPratoPrincipal are not directly saved in db
        em.detach(updatedPratoPrincipal);
        updatedPratoPrincipal
            .nomeDoPrato(UPDATED_NOME_DO_PRATO);

        restPratoPrincipalMockMvc.perform(put("/api/prato-principals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPratoPrincipal)))
            .andExpect(status().isOk());

        // Validate the PratoPrincipal in the database
        List<PratoPrincipal> pratoPrincipalList = pratoPrincipalRepository.findAll();
        assertThat(pratoPrincipalList).hasSize(databaseSizeBeforeUpdate);
        PratoPrincipal testPratoPrincipal = pratoPrincipalList.get(pratoPrincipalList.size() - 1);
        assertThat(testPratoPrincipal.getNomeDoPrato()).isEqualTo(UPDATED_NOME_DO_PRATO);
    }

    @Test
    @Transactional
    public void updateNonExistingPratoPrincipal() throws Exception {
        int databaseSizeBeforeUpdate = pratoPrincipalRepository.findAll().size();

        // Create the PratoPrincipal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPratoPrincipalMockMvc.perform(put("/api/prato-principals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pratoPrincipal)))
            .andExpect(status().isBadRequest());

        // Validate the PratoPrincipal in the database
        List<PratoPrincipal> pratoPrincipalList = pratoPrincipalRepository.findAll();
        assertThat(pratoPrincipalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePratoPrincipal() throws Exception {
        // Initialize the database
        pratoPrincipalService.save(pratoPrincipal);

        int databaseSizeBeforeDelete = pratoPrincipalRepository.findAll().size();

        // Delete the pratoPrincipal
        restPratoPrincipalMockMvc.perform(delete("/api/prato-principals/{id}", pratoPrincipal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PratoPrincipal> pratoPrincipalList = pratoPrincipalRepository.findAll();
        assertThat(pratoPrincipalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PratoPrincipal.class);
        PratoPrincipal pratoPrincipal1 = new PratoPrincipal();
        pratoPrincipal1.setId(1L);
        PratoPrincipal pratoPrincipal2 = new PratoPrincipal();
        pratoPrincipal2.setId(pratoPrincipal1.getId());
        assertThat(pratoPrincipal1).isEqualTo(pratoPrincipal2);
        pratoPrincipal2.setId(2L);
        assertThat(pratoPrincipal1).isNotEqualTo(pratoPrincipal2);
        pratoPrincipal1.setId(null);
        assertThat(pratoPrincipal1).isNotEqualTo(pratoPrincipal2);
    }
}
