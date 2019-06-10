package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.UniticketApp;

import com.mycompany.myapp.domain.Cardapio;
import com.mycompany.myapp.repository.CardapioRepository;
import com.mycompany.myapp.service.CardapioService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CardapioResource REST controller.
 *
 * @see CardapioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UniticketApp.class)
public class CardapioResourceIntTest {

    private static final String DEFAULT_DIA = "AAAAAAAAAA";
    private static final String UPDATED_DIA = "BBBBBBBBBB";

    @Autowired
    private CardapioRepository cardapioRepository;

    @Mock
    private CardapioRepository cardapioRepositoryMock;

    @Mock
    private CardapioService cardapioServiceMock;

    @Autowired
    private CardapioService cardapioService;

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

    private MockMvc restCardapioMockMvc;

    private Cardapio cardapio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CardapioResource cardapioResource = new CardapioResource(cardapioService);
        this.restCardapioMockMvc = MockMvcBuilders.standaloneSetup(cardapioResource)
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
    public static Cardapio createEntity(EntityManager em) {
        Cardapio cardapio = new Cardapio()
            .dia(DEFAULT_DIA);
        return cardapio;
    }

    @Before
    public void initTest() {
        cardapio = createEntity(em);
    }

    @Test
    @Transactional
    public void createCardapio() throws Exception {
        int databaseSizeBeforeCreate = cardapioRepository.findAll().size();

        // Create the Cardapio
        restCardapioMockMvc.perform(post("/api/cardapios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardapio)))
            .andExpect(status().isCreated());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeCreate + 1);
        Cardapio testCardapio = cardapioList.get(cardapioList.size() - 1);
        assertThat(testCardapio.getDia()).isEqualTo(DEFAULT_DIA);
    }

    @Test
    @Transactional
    public void createCardapioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cardapioRepository.findAll().size();

        // Create the Cardapio with an existing ID
        cardapio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardapioMockMvc.perform(post("/api/cardapios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardapio)))
            .andExpect(status().isBadRequest());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDiaIsRequired() throws Exception {
        int databaseSizeBeforeTest = cardapioRepository.findAll().size();
        // set the field null
        cardapio.setDia(null);

        // Create the Cardapio, which fails.

        restCardapioMockMvc.perform(post("/api/cardapios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardapio)))
            .andExpect(status().isBadRequest());

        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCardapios() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get all the cardapioList
        restCardapioMockMvc.perform(get("/api/cardapios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardapio.getId().intValue())))
            .andExpect(jsonPath("$.[*].dia").value(hasItem(DEFAULT_DIA.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCardapiosWithEagerRelationshipsIsEnabled() throws Exception {
        CardapioResource cardapioResource = new CardapioResource(cardapioServiceMock);
        when(cardapioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCardapioMockMvc = MockMvcBuilders.standaloneSetup(cardapioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCardapioMockMvc.perform(get("/api/cardapios?eagerload=true"))
        .andExpect(status().isOk());

        verify(cardapioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCardapiosWithEagerRelationshipsIsNotEnabled() throws Exception {
        CardapioResource cardapioResource = new CardapioResource(cardapioServiceMock);
            when(cardapioServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCardapioMockMvc = MockMvcBuilders.standaloneSetup(cardapioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCardapioMockMvc.perform(get("/api/cardapios?eagerload=true"))
        .andExpect(status().isOk());

            verify(cardapioServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCardapio() throws Exception {
        // Initialize the database
        cardapioRepository.saveAndFlush(cardapio);

        // Get the cardapio
        restCardapioMockMvc.perform(get("/api/cardapios/{id}", cardapio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cardapio.getId().intValue()))
            .andExpect(jsonPath("$.dia").value(DEFAULT_DIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCardapio() throws Exception {
        // Get the cardapio
        restCardapioMockMvc.perform(get("/api/cardapios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCardapio() throws Exception {
        // Initialize the database
        cardapioService.save(cardapio);

        int databaseSizeBeforeUpdate = cardapioRepository.findAll().size();

        // Update the cardapio
        Cardapio updatedCardapio = cardapioRepository.findById(cardapio.getId()).get();
        // Disconnect from session so that the updates on updatedCardapio are not directly saved in db
        em.detach(updatedCardapio);
        updatedCardapio
            .dia(UPDATED_DIA);

        restCardapioMockMvc.perform(put("/api/cardapios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCardapio)))
            .andExpect(status().isOk());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeUpdate);
        Cardapio testCardapio = cardapioList.get(cardapioList.size() - 1);
        assertThat(testCardapio.getDia()).isEqualTo(UPDATED_DIA);
    }

    @Test
    @Transactional
    public void updateNonExistingCardapio() throws Exception {
        int databaseSizeBeforeUpdate = cardapioRepository.findAll().size();

        // Create the Cardapio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardapioMockMvc.perform(put("/api/cardapios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cardapio)))
            .andExpect(status().isBadRequest());

        // Validate the Cardapio in the database
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCardapio() throws Exception {
        // Initialize the database
        cardapioService.save(cardapio);

        int databaseSizeBeforeDelete = cardapioRepository.findAll().size();

        // Delete the cardapio
        restCardapioMockMvc.perform(delete("/api/cardapios/{id}", cardapio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cardapio> cardapioList = cardapioRepository.findAll();
        assertThat(cardapioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cardapio.class);
        Cardapio cardapio1 = new Cardapio();
        cardapio1.setId(1L);
        Cardapio cardapio2 = new Cardapio();
        cardapio2.setId(cardapio1.getId());
        assertThat(cardapio1).isEqualTo(cardapio2);
        cardapio2.setId(2L);
        assertThat(cardapio1).isNotEqualTo(cardapio2);
        cardapio1.setId(null);
        assertThat(cardapio1).isNotEqualTo(cardapio2);
    }
}
