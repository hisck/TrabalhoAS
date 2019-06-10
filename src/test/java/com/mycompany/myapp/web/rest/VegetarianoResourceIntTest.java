package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.UniticketApp;

import com.mycompany.myapp.domain.Vegetariano;
import com.mycompany.myapp.repository.VegetarianoRepository;
import com.mycompany.myapp.service.VegetarianoService;
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
 * Test class for the VegetarianoResource REST controller.
 *
 * @see VegetarianoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UniticketApp.class)
public class VegetarianoResourceIntTest {

    private static final String DEFAULT_NOME_DO_PRATO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DO_PRATO = "BBBBBBBBBB";

    @Autowired
    private VegetarianoRepository vegetarianoRepository;

    @Autowired
    private VegetarianoService vegetarianoService;

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

    private MockMvc restVegetarianoMockMvc;

    private Vegetariano vegetariano;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VegetarianoResource vegetarianoResource = new VegetarianoResource(vegetarianoService);
        this.restVegetarianoMockMvc = MockMvcBuilders.standaloneSetup(vegetarianoResource)
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
    public static Vegetariano createEntity(EntityManager em) {
        Vegetariano vegetariano = new Vegetariano()
            .nomeDoPrato(DEFAULT_NOME_DO_PRATO);
        return vegetariano;
    }

    @Before
    public void initTest() {
        vegetariano = createEntity(em);
    }

    @Test
    @Transactional
    public void createVegetariano() throws Exception {
        int databaseSizeBeforeCreate = vegetarianoRepository.findAll().size();

        // Create the Vegetariano
        restVegetarianoMockMvc.perform(post("/api/vegetarianos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vegetariano)))
            .andExpect(status().isCreated());

        // Validate the Vegetariano in the database
        List<Vegetariano> vegetarianoList = vegetarianoRepository.findAll();
        assertThat(vegetarianoList).hasSize(databaseSizeBeforeCreate + 1);
        Vegetariano testVegetariano = vegetarianoList.get(vegetarianoList.size() - 1);
        assertThat(testVegetariano.getNomeDoPrato()).isEqualTo(DEFAULT_NOME_DO_PRATO);
    }

    @Test
    @Transactional
    public void createVegetarianoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vegetarianoRepository.findAll().size();

        // Create the Vegetariano with an existing ID
        vegetariano.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVegetarianoMockMvc.perform(post("/api/vegetarianos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vegetariano)))
            .andExpect(status().isBadRequest());

        // Validate the Vegetariano in the database
        List<Vegetariano> vegetarianoList = vegetarianoRepository.findAll();
        assertThat(vegetarianoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeDoPratoIsRequired() throws Exception {
        int databaseSizeBeforeTest = vegetarianoRepository.findAll().size();
        // set the field null
        vegetariano.setNomeDoPrato(null);

        // Create the Vegetariano, which fails.

        restVegetarianoMockMvc.perform(post("/api/vegetarianos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vegetariano)))
            .andExpect(status().isBadRequest());

        List<Vegetariano> vegetarianoList = vegetarianoRepository.findAll();
        assertThat(vegetarianoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVegetarianos() throws Exception {
        // Initialize the database
        vegetarianoRepository.saveAndFlush(vegetariano);

        // Get all the vegetarianoList
        restVegetarianoMockMvc.perform(get("/api/vegetarianos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vegetariano.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDoPrato").value(hasItem(DEFAULT_NOME_DO_PRATO.toString())));
    }
    
    @Test
    @Transactional
    public void getVegetariano() throws Exception {
        // Initialize the database
        vegetarianoRepository.saveAndFlush(vegetariano);

        // Get the vegetariano
        restVegetarianoMockMvc.perform(get("/api/vegetarianos/{id}", vegetariano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vegetariano.getId().intValue()))
            .andExpect(jsonPath("$.nomeDoPrato").value(DEFAULT_NOME_DO_PRATO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVegetariano() throws Exception {
        // Get the vegetariano
        restVegetarianoMockMvc.perform(get("/api/vegetarianos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVegetariano() throws Exception {
        // Initialize the database
        vegetarianoService.save(vegetariano);

        int databaseSizeBeforeUpdate = vegetarianoRepository.findAll().size();

        // Update the vegetariano
        Vegetariano updatedVegetariano = vegetarianoRepository.findById(vegetariano.getId()).get();
        // Disconnect from session so that the updates on updatedVegetariano are not directly saved in db
        em.detach(updatedVegetariano);
        updatedVegetariano
            .nomeDoPrato(UPDATED_NOME_DO_PRATO);

        restVegetarianoMockMvc.perform(put("/api/vegetarianos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVegetariano)))
            .andExpect(status().isOk());

        // Validate the Vegetariano in the database
        List<Vegetariano> vegetarianoList = vegetarianoRepository.findAll();
        assertThat(vegetarianoList).hasSize(databaseSizeBeforeUpdate);
        Vegetariano testVegetariano = vegetarianoList.get(vegetarianoList.size() - 1);
        assertThat(testVegetariano.getNomeDoPrato()).isEqualTo(UPDATED_NOME_DO_PRATO);
    }

    @Test
    @Transactional
    public void updateNonExistingVegetariano() throws Exception {
        int databaseSizeBeforeUpdate = vegetarianoRepository.findAll().size();

        // Create the Vegetariano

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVegetarianoMockMvc.perform(put("/api/vegetarianos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vegetariano)))
            .andExpect(status().isBadRequest());

        // Validate the Vegetariano in the database
        List<Vegetariano> vegetarianoList = vegetarianoRepository.findAll();
        assertThat(vegetarianoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVegetariano() throws Exception {
        // Initialize the database
        vegetarianoService.save(vegetariano);

        int databaseSizeBeforeDelete = vegetarianoRepository.findAll().size();

        // Delete the vegetariano
        restVegetarianoMockMvc.perform(delete("/api/vegetarianos/{id}", vegetariano.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vegetariano> vegetarianoList = vegetarianoRepository.findAll();
        assertThat(vegetarianoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vegetariano.class);
        Vegetariano vegetariano1 = new Vegetariano();
        vegetariano1.setId(1L);
        Vegetariano vegetariano2 = new Vegetariano();
        vegetariano2.setId(vegetariano1.getId());
        assertThat(vegetariano1).isEqualTo(vegetariano2);
        vegetariano2.setId(2L);
        assertThat(vegetariano1).isNotEqualTo(vegetariano2);
        vegetariano1.setId(null);
        assertThat(vegetariano1).isNotEqualTo(vegetariano2);
    }
}
