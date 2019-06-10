package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.UniticketApp;

import com.mycompany.myapp.domain.Acompanhamento;
import com.mycompany.myapp.repository.AcompanhamentoRepository;
import com.mycompany.myapp.service.AcompanhamentoService;
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
 * Test class for the AcompanhamentoResource REST controller.
 *
 * @see AcompanhamentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UniticketApp.class)
public class AcompanhamentoResourceIntTest {

    private static final String DEFAULT_NOME_DO_ACOMPANHAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_DO_ACOMPANHAMENTO = "BBBBBBBBBB";

    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;

    @Autowired
    private AcompanhamentoService acompanhamentoService;

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

    private MockMvc restAcompanhamentoMockMvc;

    private Acompanhamento acompanhamento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AcompanhamentoResource acompanhamentoResource = new AcompanhamentoResource(acompanhamentoService);
        this.restAcompanhamentoMockMvc = MockMvcBuilders.standaloneSetup(acompanhamentoResource)
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
    public static Acompanhamento createEntity(EntityManager em) {
        Acompanhamento acompanhamento = new Acompanhamento()
            .nomeDoAcompanhamento(DEFAULT_NOME_DO_ACOMPANHAMENTO);
        return acompanhamento;
    }

    @Before
    public void initTest() {
        acompanhamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcompanhamento() throws Exception {
        int databaseSizeBeforeCreate = acompanhamentoRepository.findAll().size();

        // Create the Acompanhamento
        restAcompanhamentoMockMvc.perform(post("/api/acompanhamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acompanhamento)))
            .andExpect(status().isCreated());

        // Validate the Acompanhamento in the database
        List<Acompanhamento> acompanhamentoList = acompanhamentoRepository.findAll();
        assertThat(acompanhamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Acompanhamento testAcompanhamento = acompanhamentoList.get(acompanhamentoList.size() - 1);
        assertThat(testAcompanhamento.getNomeDoAcompanhamento()).isEqualTo(DEFAULT_NOME_DO_ACOMPANHAMENTO);
    }

    @Test
    @Transactional
    public void createAcompanhamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acompanhamentoRepository.findAll().size();

        // Create the Acompanhamento with an existing ID
        acompanhamento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcompanhamentoMockMvc.perform(post("/api/acompanhamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acompanhamento)))
            .andExpect(status().isBadRequest());

        // Validate the Acompanhamento in the database
        List<Acompanhamento> acompanhamentoList = acompanhamentoRepository.findAll();
        assertThat(acompanhamentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeDoAcompanhamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = acompanhamentoRepository.findAll().size();
        // set the field null
        acompanhamento.setNomeDoAcompanhamento(null);

        // Create the Acompanhamento, which fails.

        restAcompanhamentoMockMvc.perform(post("/api/acompanhamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acompanhamento)))
            .andExpect(status().isBadRequest());

        List<Acompanhamento> acompanhamentoList = acompanhamentoRepository.findAll();
        assertThat(acompanhamentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcompanhamentos() throws Exception {
        // Initialize the database
        acompanhamentoRepository.saveAndFlush(acompanhamento);

        // Get all the acompanhamentoList
        restAcompanhamentoMockMvc.perform(get("/api/acompanhamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acompanhamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeDoAcompanhamento").value(hasItem(DEFAULT_NOME_DO_ACOMPANHAMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getAcompanhamento() throws Exception {
        // Initialize the database
        acompanhamentoRepository.saveAndFlush(acompanhamento);

        // Get the acompanhamento
        restAcompanhamentoMockMvc.perform(get("/api/acompanhamentos/{id}", acompanhamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acompanhamento.getId().intValue()))
            .andExpect(jsonPath("$.nomeDoAcompanhamento").value(DEFAULT_NOME_DO_ACOMPANHAMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAcompanhamento() throws Exception {
        // Get the acompanhamento
        restAcompanhamentoMockMvc.perform(get("/api/acompanhamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcompanhamento() throws Exception {
        // Initialize the database
        acompanhamentoService.save(acompanhamento);

        int databaseSizeBeforeUpdate = acompanhamentoRepository.findAll().size();

        // Update the acompanhamento
        Acompanhamento updatedAcompanhamento = acompanhamentoRepository.findById(acompanhamento.getId()).get();
        // Disconnect from session so that the updates on updatedAcompanhamento are not directly saved in db
        em.detach(updatedAcompanhamento);
        updatedAcompanhamento
            .nomeDoAcompanhamento(UPDATED_NOME_DO_ACOMPANHAMENTO);

        restAcompanhamentoMockMvc.perform(put("/api/acompanhamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAcompanhamento)))
            .andExpect(status().isOk());

        // Validate the Acompanhamento in the database
        List<Acompanhamento> acompanhamentoList = acompanhamentoRepository.findAll();
        assertThat(acompanhamentoList).hasSize(databaseSizeBeforeUpdate);
        Acompanhamento testAcompanhamento = acompanhamentoList.get(acompanhamentoList.size() - 1);
        assertThat(testAcompanhamento.getNomeDoAcompanhamento()).isEqualTo(UPDATED_NOME_DO_ACOMPANHAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingAcompanhamento() throws Exception {
        int databaseSizeBeforeUpdate = acompanhamentoRepository.findAll().size();

        // Create the Acompanhamento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcompanhamentoMockMvc.perform(put("/api/acompanhamentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acompanhamento)))
            .andExpect(status().isBadRequest());

        // Validate the Acompanhamento in the database
        List<Acompanhamento> acompanhamentoList = acompanhamentoRepository.findAll();
        assertThat(acompanhamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAcompanhamento() throws Exception {
        // Initialize the database
        acompanhamentoService.save(acompanhamento);

        int databaseSizeBeforeDelete = acompanhamentoRepository.findAll().size();

        // Delete the acompanhamento
        restAcompanhamentoMockMvc.perform(delete("/api/acompanhamentos/{id}", acompanhamento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Acompanhamento> acompanhamentoList = acompanhamentoRepository.findAll();
        assertThat(acompanhamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acompanhamento.class);
        Acompanhamento acompanhamento1 = new Acompanhamento();
        acompanhamento1.setId(1L);
        Acompanhamento acompanhamento2 = new Acompanhamento();
        acompanhamento2.setId(acompanhamento1.getId());
        assertThat(acompanhamento1).isEqualTo(acompanhamento2);
        acompanhamento2.setId(2L);
        assertThat(acompanhamento1).isNotEqualTo(acompanhamento2);
        acompanhamento1.setId(null);
        assertThat(acompanhamento1).isNotEqualTo(acompanhamento2);
    }
}
