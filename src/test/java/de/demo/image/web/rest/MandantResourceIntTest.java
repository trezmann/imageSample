package de.demo.image.web.rest;

import de.demo.image.ImageSampleApp;

import de.demo.image.domain.Mandant;
import de.demo.image.repository.MandantRepository;
import de.demo.image.service.MandantService;
import de.demo.image.service.dto.MandantDTO;
import de.demo.image.service.mapper.MandantMapper;
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
import java.util.List;

import static de.demo.image.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MandantResource REST controller.
 *
 * @see MandantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImageSampleApp.class)
public class MandantResourceIntTest {

    private static final String DEFAULT_KURZBEZ = "AAAAAAAAAA";
    private static final String UPDATED_KURZBEZ = "BBBBBBBBBB";

    private static final String DEFAULT_BEZEICHNUNG = "AAAAAAAAAA";
    private static final String UPDATED_BEZEICHNUNG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AKTIV = false;
    private static final Boolean UPDATED_AKTIV = true;

    @Autowired
    private MandantRepository mandantRepository;

    @Autowired
    private MandantMapper mandantMapper;

    @Autowired
    private MandantService mandantService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMandantMockMvc;

    private Mandant mandant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MandantResource mandantResource = new MandantResource(mandantService);
        this.restMandantMockMvc = MockMvcBuilders.standaloneSetup(mandantResource)
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
    public static Mandant createEntity(EntityManager em) {
        Mandant mandant = new Mandant()
            .kurzbez(DEFAULT_KURZBEZ)
            .bezeichnung(DEFAULT_BEZEICHNUNG)
            .aktiv(DEFAULT_AKTIV);
        return mandant;
    }

    @Before
    public void initTest() {
        mandant = createEntity(em);
    }

    @Test
    @Transactional
    public void createMandant() throws Exception {
        int databaseSizeBeforeCreate = mandantRepository.findAll().size();

        // Create the Mandant
        MandantDTO mandantDTO = mandantMapper.toDto(mandant);
        restMandantMockMvc.perform(post("/api/mandants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mandantDTO)))
            .andExpect(status().isCreated());

        // Validate the Mandant in the database
        List<Mandant> mandantList = mandantRepository.findAll();
        assertThat(mandantList).hasSize(databaseSizeBeforeCreate + 1);
        Mandant testMandant = mandantList.get(mandantList.size() - 1);
        assertThat(testMandant.getKurzbez()).isEqualTo(DEFAULT_KURZBEZ);
        assertThat(testMandant.getBezeichnung()).isEqualTo(DEFAULT_BEZEICHNUNG);
        assertThat(testMandant.isAktiv()).isEqualTo(DEFAULT_AKTIV);
    }

    @Test
    @Transactional
    public void createMandantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mandantRepository.findAll().size();

        // Create the Mandant with an existing ID
        mandant.setId(1L);
        MandantDTO mandantDTO = mandantMapper.toDto(mandant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMandantMockMvc.perform(post("/api/mandants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mandantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mandant in the database
        List<Mandant> mandantList = mandantRepository.findAll();
        assertThat(mandantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMandants() throws Exception {
        // Initialize the database
        mandantRepository.saveAndFlush(mandant);

        // Get all the mandantList
        restMandantMockMvc.perform(get("/api/mandants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mandant.getId().intValue())))
            .andExpect(jsonPath("$.[*].kurzbez").value(hasItem(DEFAULT_KURZBEZ.toString())))
            .andExpect(jsonPath("$.[*].bezeichnung").value(hasItem(DEFAULT_BEZEICHNUNG.toString())))
            .andExpect(jsonPath("$.[*].aktiv").value(hasItem(DEFAULT_AKTIV.booleanValue())));
    }

    @Test
    @Transactional
    public void getMandant() throws Exception {
        // Initialize the database
        mandantRepository.saveAndFlush(mandant);

        // Get the mandant
        restMandantMockMvc.perform(get("/api/mandants/{id}", mandant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mandant.getId().intValue()))
            .andExpect(jsonPath("$.kurzbez").value(DEFAULT_KURZBEZ.toString()))
            .andExpect(jsonPath("$.bezeichnung").value(DEFAULT_BEZEICHNUNG.toString()))
            .andExpect(jsonPath("$.aktiv").value(DEFAULT_AKTIV.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMandant() throws Exception {
        // Get the mandant
        restMandantMockMvc.perform(get("/api/mandants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMandant() throws Exception {
        // Initialize the database
        mandantRepository.saveAndFlush(mandant);
        int databaseSizeBeforeUpdate = mandantRepository.findAll().size();

        // Update the mandant
        Mandant updatedMandant = mandantRepository.findOne(mandant.getId());
        // Disconnect from session so that the updates on updatedMandant are not directly saved in db
        em.detach(updatedMandant);
        updatedMandant
            .kurzbez(UPDATED_KURZBEZ)
            .bezeichnung(UPDATED_BEZEICHNUNG)
            .aktiv(UPDATED_AKTIV);
        MandantDTO mandantDTO = mandantMapper.toDto(updatedMandant);

        restMandantMockMvc.perform(put("/api/mandants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mandantDTO)))
            .andExpect(status().isOk());

        // Validate the Mandant in the database
        List<Mandant> mandantList = mandantRepository.findAll();
        assertThat(mandantList).hasSize(databaseSizeBeforeUpdate);
        Mandant testMandant = mandantList.get(mandantList.size() - 1);
        assertThat(testMandant.getKurzbez()).isEqualTo(UPDATED_KURZBEZ);
        assertThat(testMandant.getBezeichnung()).isEqualTo(UPDATED_BEZEICHNUNG);
        assertThat(testMandant.isAktiv()).isEqualTo(UPDATED_AKTIV);
    }

    @Test
    @Transactional
    public void updateNonExistingMandant() throws Exception {
        int databaseSizeBeforeUpdate = mandantRepository.findAll().size();

        // Create the Mandant
        MandantDTO mandantDTO = mandantMapper.toDto(mandant);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMandantMockMvc.perform(put("/api/mandants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mandantDTO)))
            .andExpect(status().isCreated());

        // Validate the Mandant in the database
        List<Mandant> mandantList = mandantRepository.findAll();
        assertThat(mandantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMandant() throws Exception {
        // Initialize the database
        mandantRepository.saveAndFlush(mandant);
        int databaseSizeBeforeDelete = mandantRepository.findAll().size();

        // Get the mandant
        restMandantMockMvc.perform(delete("/api/mandants/{id}", mandant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Mandant> mandantList = mandantRepository.findAll();
        assertThat(mandantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mandant.class);
        Mandant mandant1 = new Mandant();
        mandant1.setId(1L);
        Mandant mandant2 = new Mandant();
        mandant2.setId(mandant1.getId());
        assertThat(mandant1).isEqualTo(mandant2);
        mandant2.setId(2L);
        assertThat(mandant1).isNotEqualTo(mandant2);
        mandant1.setId(null);
        assertThat(mandant1).isNotEqualTo(mandant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MandantDTO.class);
        MandantDTO mandantDTO1 = new MandantDTO();
        mandantDTO1.setId(1L);
        MandantDTO mandantDTO2 = new MandantDTO();
        assertThat(mandantDTO1).isNotEqualTo(mandantDTO2);
        mandantDTO2.setId(mandantDTO1.getId());
        assertThat(mandantDTO1).isEqualTo(mandantDTO2);
        mandantDTO2.setId(2L);
        assertThat(mandantDTO1).isNotEqualTo(mandantDTO2);
        mandantDTO1.setId(null);
        assertThat(mandantDTO1).isNotEqualTo(mandantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(mandantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(mandantMapper.fromId(null)).isNull();
    }
}
