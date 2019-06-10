package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.UniticketApp;

import com.mycompany.myapp.domain.Salada;
import com.mycompany.myapp.repository.SaladaRepository;
import com.mycompany.myapp.service.SaladaService;
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
 * Test class for the SaladaResource REST controller.
 *
 * @see SaladaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UniticketApp.class)
public class SaladaResourceIntTest {

    private static final String DEFAULT_NOME_DA_SALADA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DA_SALADA = "BBBBBBBBBB";

    @Autowired
    private SaladaRepository saladaRepository;

    @Autowired
    private SaladaService saladaService;

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

    private MockMvc restSaladaMockMvc;

    private Salada salada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SaladaResource saladaResource = new SaladaResource(saladaService);
        this.restSaladaMockMvc = MockMvcBuilders.standaloneSetup(saladaResource)
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
    public static Salada createEntity(EntityManager em) {
        Salada salada = new Salada()
            .nomeDaSalada(DEFAULT_NOME_DA_SALADA);
        return salada;
    }

    @Before
    public void initTest() {
        salada = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalada() throws Exception {
        int databaseSizeBeforeCreate = saladaRepository.findAll().size();

        // Create the Salada
        restSaladaMockMvc.perform(post("/api/saladas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salada)))
            .andExpect(status().isCreated());

        // Validate the Salada in the database
        List<Salada> saladaList = saladaRepository.findAll();
        assertThat(saladaList).hasSize(databaseSizeBeforeCreate + 1);
        Salada testSalada = saladaList.get(saladaList.size() - 1);
        assertThat(testSalada.getNomeDaSalada()).isEqualTo(DEFAULT_NOME_DA_SALADA);
    }

    @Test
    @Transactional
    public void createSaladaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saladaRepository.findAll().size();

        // Create the Salada with an existing ID
        salada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaladaMockMvc.perform(post("/api/saladas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salada)))
            .andExpect(status().isBadRequest());

        // Validate the Salada in the database
        List<Salada> saladaList = saladaRepository.findAll();
        assertThat(saladaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeDaSaladaIsRequired() throws Exception {
        int databaseSizeBeforeTest = saladaRepository.findAll().size();
        // set the field null
        salada.setNomeDaSalada(null);

        // Create the Salada, which fails.

        restSaladaMockMvc.perform(post("/api/saladas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salada)))
            .andExpect(status().isBadRequest());

        List<Salada> saladaList = saladaRepository.findAll();
        assertThat(saladaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSaladas() throws Exception {
        // Initialize the database
        saladaRepository.saveAndFlush(salada);

        // Get all the saladaList
        restSaladaMockMvc.perform(get("/api/saladas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salada.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDaSalada").value(hasItem(DEFAULT_NOME_DA_SALADA.toString())));
    }
    
    @Test
    @Transactional
    public void getSalada() throws Exception {
        // Initialize the database
        saladaRepository.saveAndFlush(salada);

        // Get the salada
        restSaladaMockMvc.perform(get("/api/saladas/{id}", salada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salada.getId().intValue()))
            .andExpect(jsonPath("$.nomeDaSalada").value(DEFAULT_NOME_DA_SALADA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSalada() throws Exception {
        // Get the salada
        restSaladaMockMvc.perform(get("/api/saladas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalada() throws Exception {
        // Initialize the database
        saladaService.save(salada);

        int databaseSizeBeforeUpdate = saladaRepository.findAll().size();

        // Update the salada
        Salada updatedSalada = saladaRepository.findById(salada.getId()).get();
        // Disconnect from session so that the updates on updatedSalada are not directly saved in db
        em.detach(updatedSalada);
        updatedSalada
            .nomeDaSalada(UPDATED_NOME_DA_SALADA);

        restSaladaMockMvc.perform(put("/api/saladas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalada)))
            .andExpect(status().isOk());

        // Validate the Salada in the database
        List<Salada> saladaList = saladaRepository.findAll();
        assertThat(saladaList).hasSize(databaseSizeBeforeUpdate);
        Salada testSalada = saladaList.get(saladaList.size() - 1);
        assertThat(testSalada.getNomeDaSalada()).isEqualTo(UPDATED_NOME_DA_SALADA);
    }

    @Test
    @Transactional
    public void updateNonExistingSalada() throws Exception {
        int databaseSizeBeforeUpdate = saladaRepository.findAll().size();

        // Create the Salada

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaladaMockMvc.perform(put("/api/saladas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salada)))
            .andExpect(status().isBadRequest());

        // Validate the Salada in the database
        List<Salada> saladaList = saladaRepository.findAll();
        assertThat(saladaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalada() throws Exception {
        // Initialize the database
        saladaService.save(salada);

        int databaseSizeBeforeDelete = saladaRepository.findAll().size();

        // Delete the salada
        restSaladaMockMvc.perform(delete("/api/saladas/{id}", salada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Salada> saladaList = saladaRepository.findAll();
        assertThat(saladaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Salada.class);
        Salada salada1 = new Salada();
        salada1.setId(1L);
        Salada salada2 = new Salada();
        salada2.setId(salada1.getId());
        assertThat(salada1).isEqualTo(salada2);
        salada2.setId(2L);
        assertThat(salada1).isNotEqualTo(salada2);
        salada1.setId(null);
        assertThat(salada1).isNotEqualTo(salada2);
    }
}
