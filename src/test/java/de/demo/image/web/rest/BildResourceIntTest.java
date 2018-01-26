package de.demo.image.web.rest;

import de.demo.image.ImageSampleApp;

import de.demo.image.domain.Bild;
import de.demo.image.repository.BildRepository;
import de.demo.image.service.BildService;
import de.demo.image.service.dto.BildDTO;
import de.demo.image.service.mapper.BildMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static de.demo.image.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BildResource REST controller.
 *
 * @see BildResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImageSampleApp.class)
public class BildResourceIntTest {

    private static final byte[] DEFAULT_BILD_DATEI = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BILD_DATEI = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_BILD_DATEI_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BILD_DATEI_CONTENT_TYPE = "image/png";

    @Autowired
    private BildRepository bildRepository;

    @Autowired
    private BildMapper bildMapper;

    @Autowired
    private BildService bildService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBildMockMvc;

    private Bild bild;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BildResource bildResource = new BildResource(bildService);
        this.restBildMockMvc = MockMvcBuilders.standaloneSetup(bildResource)
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
    public static Bild createEntity(EntityManager em) {
        Bild bild = new Bild()
            .bildDatei(DEFAULT_BILD_DATEI)
            .bildDateiContentType(DEFAULT_BILD_DATEI_CONTENT_TYPE);
        return bild;
    }

    @Before
    public void initTest() {
        bild = createEntity(em);
    }

    @Test
    @Transactional
    public void createBild() throws Exception {
        int databaseSizeBeforeCreate = bildRepository.findAll().size();

        // Create the Bild
        BildDTO bildDTO = bildMapper.toDto(bild);
        restBildMockMvc.perform(post("/api/bilds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bildDTO)))
            .andExpect(status().isCreated());

        // Validate the Bild in the database
        List<Bild> bildList = bildRepository.findAll();
        assertThat(bildList).hasSize(databaseSizeBeforeCreate + 1);
        Bild testBild = bildList.get(bildList.size() - 1);
        assertThat(testBild.getBildDatei()).isEqualTo(DEFAULT_BILD_DATEI);
        assertThat(testBild.getBildDateiContentType()).isEqualTo(DEFAULT_BILD_DATEI_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createBildWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bildRepository.findAll().size();

        // Create the Bild with an existing ID
        bild.setId(1L);
        BildDTO bildDTO = bildMapper.toDto(bild);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBildMockMvc.perform(post("/api/bilds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bildDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bild in the database
        List<Bild> bildList = bildRepository.findAll();
        assertThat(bildList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBilds() throws Exception {
        // Initialize the database
        bildRepository.saveAndFlush(bild);

        // Get all the bildList
        restBildMockMvc.perform(get("/api/bilds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bild.getId().intValue())))
            .andExpect(jsonPath("$.[*].bildDateiContentType").value(hasItem(DEFAULT_BILD_DATEI_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bildDatei").value(hasItem(Base64Utils.encodeToString(DEFAULT_BILD_DATEI))));
    }

    @Test
    @Transactional
    public void getBild() throws Exception {
        // Initialize the database
        bildRepository.saveAndFlush(bild);

        // Get the bild
        restBildMockMvc.perform(get("/api/bilds/{id}", bild.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bild.getId().intValue()))
            .andExpect(jsonPath("$.bildDateiContentType").value(DEFAULT_BILD_DATEI_CONTENT_TYPE))
            .andExpect(jsonPath("$.bildDatei").value(Base64Utils.encodeToString(DEFAULT_BILD_DATEI)));
    }

    @Test
    @Transactional
    public void getNonExistingBild() throws Exception {
        // Get the bild
        restBildMockMvc.perform(get("/api/bilds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBild() throws Exception {
        // Initialize the database
        bildRepository.saveAndFlush(bild);
        int databaseSizeBeforeUpdate = bildRepository.findAll().size();

        // Update the bild
        Bild updatedBild = bildRepository.findOne(bild.getId());
        // Disconnect from session so that the updates on updatedBild are not directly saved in db
        em.detach(updatedBild);
        updatedBild
            .bildDatei(UPDATED_BILD_DATEI)
            .bildDateiContentType(UPDATED_BILD_DATEI_CONTENT_TYPE);
        BildDTO bildDTO = bildMapper.toDto(updatedBild);

        restBildMockMvc.perform(put("/api/bilds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bildDTO)))
            .andExpect(status().isOk());

        // Validate the Bild in the database
        List<Bild> bildList = bildRepository.findAll();
        assertThat(bildList).hasSize(databaseSizeBeforeUpdate);
        Bild testBild = bildList.get(bildList.size() - 1);
        assertThat(testBild.getBildDatei()).isEqualTo(UPDATED_BILD_DATEI);
        assertThat(testBild.getBildDateiContentType()).isEqualTo(UPDATED_BILD_DATEI_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingBild() throws Exception {
        int databaseSizeBeforeUpdate = bildRepository.findAll().size();

        // Create the Bild
        BildDTO bildDTO = bildMapper.toDto(bild);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBildMockMvc.perform(put("/api/bilds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bildDTO)))
            .andExpect(status().isCreated());

        // Validate the Bild in the database
        List<Bild> bildList = bildRepository.findAll();
        assertThat(bildList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBild() throws Exception {
        // Initialize the database
        bildRepository.saveAndFlush(bild);
        int databaseSizeBeforeDelete = bildRepository.findAll().size();

        // Get the bild
        restBildMockMvc.perform(delete("/api/bilds/{id}", bild.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bild> bildList = bildRepository.findAll();
        assertThat(bildList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bild.class);
        Bild bild1 = new Bild();
        bild1.setId(1L);
        Bild bild2 = new Bild();
        bild2.setId(bild1.getId());
        assertThat(bild1).isEqualTo(bild2);
        bild2.setId(2L);
        assertThat(bild1).isNotEqualTo(bild2);
        bild1.setId(null);
        assertThat(bild1).isNotEqualTo(bild2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BildDTO.class);
        BildDTO bildDTO1 = new BildDTO();
        bildDTO1.setId(1L);
        BildDTO bildDTO2 = new BildDTO();
        assertThat(bildDTO1).isNotEqualTo(bildDTO2);
        bildDTO2.setId(bildDTO1.getId());
        assertThat(bildDTO1).isEqualTo(bildDTO2);
        bildDTO2.setId(2L);
        assertThat(bildDTO1).isNotEqualTo(bildDTO2);
        bildDTO1.setId(null);
        assertThat(bildDTO1).isNotEqualTo(bildDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bildMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bildMapper.fromId(null)).isNull();
    }
}
