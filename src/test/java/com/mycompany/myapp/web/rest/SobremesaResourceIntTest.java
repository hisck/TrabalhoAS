package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.UniticketApp;

import com.mycompany.myapp.domain.Sobremesa;
import com.mycompany.myapp.repository.SobremesaRepository;
import com.mycompany.myapp.service.SobremesaService;
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
 * Test class for the SobremesaResource REST controller.
 *
 * @see SobremesaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UniticketApp.class)
public class SobremesaResourceIntTest {

    private static final String DEFAULT_NOME_DA_SOBREMESA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DA_SOBREMESA = "BBBBBBBBBB";

    @Autowired
    private SobremesaRepository sobremesaRepository;

    @Autowired
    private SobremesaService sobremesaService;

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

    private MockMvc restSobremesaMockMvc;

    private Sobremesa sobremesa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SobremesaResource sobremesaResource = new SobremesaResource(sobremesaService);
        this.restSobremesaMockMvc = MockMvcBuilders.standaloneSetup(sobremesaResource)
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
    public static Sobremesa createEntity(EntityManager em) {
        Sobremesa sobremesa = new Sobremesa()
            .nomeDaSobremesa(DEFAULT_NOME_DA_SOBREMESA);
        return sobremesa;
    }

    @Before
    public void initTest() {
        sobremesa = createEntity(em);
    }

    @Test
    @Transactional
    public void createSobremesa() throws Exception {
        int databaseSizeBeforeCreate = sobremesaRepository.findAll().size();

        // Create the Sobremesa
        restSobremesaMockMvc.perform(post("/api/sobremesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sobremesa)))
            .andExpect(status().isCreated());

        // Validate the Sobremesa in the database
        List<Sobremesa> sobremesaList = sobremesaRepository.findAll();
        assertThat(sobremesaList).hasSize(databaseSizeBeforeCreate + 1);
        Sobremesa testSobremesa = sobremesaList.get(sobremesaList.size() - 1);
        assertThat(testSobremesa.getNomeDaSobremesa()).isEqualTo(DEFAULT_NOME_DA_SOBREMESA);
    }

    @Test
    @Transactional
    public void createSobremesaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sobremesaRepository.findAll().size();

        // Create the Sobremesa with an existing ID
        sobremesa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSobremesaMockMvc.perform(post("/api/sobremesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sobremesa)))
            .andExpect(status().isBadRequest());

        // Validate the Sobremesa in the database
        List<Sobremesa> sobremesaList = sobremesaRepository.findAll();
        assertThat(sobremesaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeDaSobremesaIsRequired() throws Exception {
        int databaseSizeBeforeTest = sobremesaRepository.findAll().size();
        // set the field null
        sobremesa.setNomeDaSobremesa(null);

        // Create the Sobremesa, which fails.

        restSobremesaMockMvc.perform(post("/api/sobremesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sobremesa)))
            .andExpect(status().isBadRequest());

        List<Sobremesa> sobremesaList = sobremesaRepository.findAll();
        assertThat(sobremesaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSobremesas() throws Exception {
        // Initialize the database
        sobremesaRepository.saveAndFlush(sobremesa);

        // Get all the sobremesaList
        restSobremesaMockMvc.perform(get("/api/sobremesas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sobremesa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDaSobremesa").value(hasItem(DEFAULT_NOME_DA_SOBREMESA.toString())));
    }
    
    @Test
    @Transactional
    public void getSobremesa() throws Exception {
        // Initialize the database
        sobremesaRepository.saveAndFlush(sobremesa);

        // Get the sobremesa
        restSobremesaMockMvc.perform(get("/api/sobremesas/{id}", sobremesa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sobremesa.getId().intValue()))
            .andExpect(jsonPath("$.nomeDaSobremesa").value(DEFAULT_NOME_DA_SOBREMESA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSobremesa() throws Exception {
        // Get the sobremesa
        restSobremesaMockMvc.perform(get("/api/sobremesas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSobremesa() throws Exception {
        // Initialize the database
        sobremesaService.save(sobremesa);

        int databaseSizeBeforeUpdate = sobremesaRepository.findAll().size();

        // Update the sobremesa
        Sobremesa updatedSobremesa = sobremesaRepository.findById(sobremesa.getId()).get();
        // Disconnect from session so that the updates on updatedSobremesa are not directly saved in db
        em.detach(updatedSobremesa);
        updatedSobremesa
            .nomeDaSobremesa(UPDATED_NOME_DA_SOBREMESA);

        restSobremesaMockMvc.perform(put("/api/sobremesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSobremesa)))
            .andExpect(status().isOk());

        // Validate the Sobremesa in the database
        List<Sobremesa> sobremesaList = sobremesaRepository.findAll();
        assertThat(sobremesaList).hasSize(databaseSizeBeforeUpdate);
        Sobremesa testSobremesa = sobremesaList.get(sobremesaList.size() - 1);
        assertThat(testSobremesa.getNomeDaSobremesa()).isEqualTo(UPDATED_NOME_DA_SOBREMESA);
    }

    @Test
    @Transactional
    public void updateNonExistingSobremesa() throws Exception {
        int databaseSizeBeforeUpdate = sobremesaRepository.findAll().size();

        // Create the Sobremesa

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSobremesaMockMvc.perform(put("/api/sobremesas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sobremesa)))
            .andExpect(status().isBadRequest());

        // Validate the Sobremesa in the database
        List<Sobremesa> sobremesaList = sobremesaRepository.findAll();
        assertThat(sobremesaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSobremesa() throws Exception {
        // Initialize the database
        sobremesaService.save(sobremesa);

        int databaseSizeBeforeDelete = sobremesaRepository.findAll().size();

        // Delete the sobremesa
        restSobremesaMockMvc.perform(delete("/api/sobremesas/{id}", sobremesa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sobremesa> sobremesaList = sobremesaRepository.findAll();
        assertThat(sobremesaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sobremesa.class);
        Sobremesa sobremesa1 = new Sobremesa();
        sobremesa1.setId(1L);
        Sobremesa sobremesa2 = new Sobremesa();
        sobremesa2.setId(sobremesa1.getId());
        assertThat(sobremesa1).isEqualTo(sobremesa2);
        sobremesa2.setId(2L);
        assertThat(sobremesa1).isNotEqualTo(sobremesa2);
        sobremesa1.setId(null);
        assertThat(sobremesa1).isNotEqualTo(sobremesa2);
    }
}
