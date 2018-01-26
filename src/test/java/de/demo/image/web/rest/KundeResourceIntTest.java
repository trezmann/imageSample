package de.demo.image.web.rest;

import de.demo.image.ImageSampleApp;

import de.demo.image.domain.Kunde;
import de.demo.image.repository.KundeRepository;
import de.demo.image.service.KundeService;
import de.demo.image.service.dto.KundeDTO;
import de.demo.image.service.mapper.KundeMapper;
import de.demo.image.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static de.demo.image.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KundeResource REST controller.
 *
 * @see KundeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImageSampleApp.class)
public class KundeResourceIntTest {

    private static final String DEFAULT_STAMMNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_STAMMNUMMER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_S_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_S_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_E_DATUM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_E_DATUM = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STEUERNUMMER = "AAAAAAAAAA";
    private static final String UPDATED_STEUERNUMMER = "BBBBBBBBBB";

    @Autowired
    private KundeRepository kundeRepository;

    @Autowired
    private KundeMapper kundeMapper;

    @Autowired
    private KundeService kundeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKundeMockMvc;

    private Kunde kunde;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KundeResource kundeResource = new KundeResource(kundeService);
        this.restKundeMockMvc = MockMvcBuilders.standaloneSetup(kundeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kunde createEntity(EntityManager em) {
        Kunde kunde = new Kunde()
            .stammnummer(DEFAULT_STAMMNUMMER)
            .sDatum(DEFAULT_S_DATUM)
            .eDatum(DEFAULT_E_DATUM)
            .steuernummer(DEFAULT_STEUERNUMMER);
        return kunde;
    }

    @Before
    public void initTest() {
        kunde = createEntity(em);
    }

    @Test
    @Transactional
    public void createKunde() throws Exception {
        int databaseSizeBeforeCreate = kundeRepository.findAll().size();

        // Create the Kunde
        KundeDTO kundeDTO = kundeMapper.toDto(kunde);
        restKundeMockMvc.perform(post("/api/kundes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kundeDTO)))
            .andExpect(status().isCreated());

        // Validate the Kunde in the database
        List<Kunde> kundeList = kundeRepository.findAll();
        assertThat(kundeList).hasSize(databaseSizeBeforeCreate + 1);
        Kunde testKunde = kundeList.get(kundeList.size() - 1);
        assertThat(testKunde.getStammnummer()).isEqualTo(DEFAULT_STAMMNUMMER);
        assertThat(testKunde.getsDatum()).isEqualTo(DEFAULT_S_DATUM);
        assertThat(testKunde.geteDatum()).isEqualTo(DEFAULT_E_DATUM);
        assertThat(testKunde.getSteuernummer()).isEqualTo(DEFAULT_STEUERNUMMER);
    }

    @Test
    @Transactional
    public void createKundeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kundeRepository.findAll().size();

        // Create the Kunde with an existing ID
        kunde.setId(1L);
        KundeDTO kundeDTO = kundeMapper.toDto(kunde);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKundeMockMvc.perform(post("/api/kundes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kundeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kunde in the database
        List<Kunde> kundeList = kundeRepository.findAll();
        assertThat(kundeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllKundes() throws Exception {
        // Initialize the database
        kundeRepository.saveAndFlush(kunde);

        // Get all the kundeList
        restKundeMockMvc.perform(get("/api/kundes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kunde.getId().intValue())))
            .andExpect(jsonPath("$.[*].stammnummer").value(hasItem(DEFAULT_STAMMNUMMER.toString())))
            .andExpect(jsonPath("$.[*].sDatum").value(hasItem(DEFAULT_S_DATUM.toString())))
            .andExpect(jsonPath("$.[*].eDatum").value(hasItem(DEFAULT_E_DATUM.toString())))
            .andExpect(jsonPath("$.[*].steuernummer").value(hasItem(DEFAULT_STEUERNUMMER.toString())));
    }

    @Test
    @Transactional
    public void getKunde() throws Exception {
        // Initialize the database
        kundeRepository.saveAndFlush(kunde);

        // Get the kunde
        restKundeMockMvc.perform(get("/api/kundes/{id}", kunde.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kunde.getId().intValue()))
            .andExpect(jsonPath("$.stammnummer").value(DEFAULT_STAMMNUMMER.toString()))
            .andExpect(jsonPath("$.sDatum").value(DEFAULT_S_DATUM.toString()))
            .andExpect(jsonPath("$.eDatum").value(DEFAULT_E_DATUM.toString()))
            .andExpect(jsonPath("$.steuernummer").value(DEFAULT_STEUERNUMMER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKunde() throws Exception {
        // Get the kunde
        restKundeMockMvc.perform(get("/api/kundes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKunde() throws Exception {
        // Initialize the database
        kundeRepository.saveAndFlush(kunde);
        int databaseSizeBeforeUpdate = kundeRepository.findAll().size();

        // Update the kunde
        Kunde updatedKunde = kundeRepository.findOne(kunde.getId());
        // Disconnect from session so that the updates on updatedKunde are not directly saved in db
        em.detach(updatedKunde);
        updatedKunde
            .stammnummer(UPDATED_STAMMNUMMER)
            .sDatum(UPDATED_S_DATUM)
            .eDatum(UPDATED_E_DATUM)
            .steuernummer(UPDATED_STEUERNUMMER);
        KundeDTO kundeDTO = kundeMapper.toDto(updatedKunde);

        restKundeMockMvc.perform(put("/api/kundes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kundeDTO)))
            .andExpect(status().isOk());

        // Validate the Kunde in the database
        List<Kunde> kundeList = kundeRepository.findAll();
        assertThat(kundeList).hasSize(databaseSizeBeforeUpdate);
        Kunde testKunde = kundeList.get(kundeList.size() - 1);
        assertThat(testKunde.getStammnummer()).isEqualTo(UPDATED_STAMMNUMMER);
        assertThat(testKunde.getsDatum()).isEqualTo(UPDATED_S_DATUM);
        assertThat(testKunde.geteDatum()).isEqualTo(UPDATED_E_DATUM);
        assertThat(testKunde.getSteuernummer()).isEqualTo(UPDATED_STEUERNUMMER);
    }

    @Test
    @Transactional
    public void updateNonExistingKunde() throws Exception {
        int databaseSizeBeforeUpdate = kundeRepository.findAll().size();

        // Create the Kunde
        KundeDTO kundeDTO = kundeMapper.toDto(kunde);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKundeMockMvc.perform(put("/api/kundes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kundeDTO)))
            .andExpect(status().isCreated());

        // Validate the Kunde in the database
        List<Kunde> kundeList = kundeRepository.findAll();
        assertThat(kundeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKunde() throws Exception {
        // Initialize the database
        kundeRepository.saveAndFlush(kunde);
        int databaseSizeBeforeDelete = kundeRepository.findAll().size();

        // Get the kunde
        restKundeMockMvc.perform(delete("/api/kundes/{id}", kunde.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Kunde> kundeList = kundeRepository.findAll();
        assertThat(kundeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kunde.class);
        Kunde kunde1 = new Kunde();
        kunde1.setId(1L);
        Kunde kunde2 = new Kunde();
        kunde2.setId(kunde1.getId());
        assertThat(kunde1).isEqualTo(kunde2);
        kunde2.setId(2L);
        assertThat(kunde1).isNotEqualTo(kunde2);
        kunde1.setId(null);
        assertThat(kunde1).isNotEqualTo(kunde2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KundeDTO.class);
        KundeDTO kundeDTO1 = new KundeDTO();
        kundeDTO1.setId(1L);
        KundeDTO kundeDTO2 = new KundeDTO();
        assertThat(kundeDTO1).isNotEqualTo(kundeDTO2);
        kundeDTO2.setId(kundeDTO1.getId());
        assertThat(kundeDTO1).isEqualTo(kundeDTO2);
        kundeDTO2.setId(2L);
        assertThat(kundeDTO1).isNotEqualTo(kundeDTO2);
        kundeDTO1.setId(null);
        assertThat(kundeDTO1).isNotEqualTo(kundeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(kundeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(kundeMapper.fromId(null)).isNull();
    }
}
