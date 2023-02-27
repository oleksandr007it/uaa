package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.UaaApp;
import com.idevhub.tapas.domain.Kopfg;
import com.idevhub.tapas.repository.KopfgRepository;
import com.idevhub.tapas.service.KopfgService;
import com.idevhub.tapas.service.dto.KopfgDTO;
import com.idevhub.tapas.service.mapper.KopfgMapper;
import com.idevhub.tapas.web.rest.errors.ExceptionTranslator;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.idevhub.tapas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the KopfgResource REST controller.
 *
 * @see KopfgResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApp.class)
public class KopfgResourceIntTest {

    private static final Integer DEFAULT_CODE = 3;
    private static final Integer UPDATED_CODE = 4;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_UNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_UNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PREVIOUS_CODES = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUS_CODES = "BBBBBBBBBB";

    @Autowired
    private KopfgRepository kopfgRepository;


    @Autowired
    private KopfgMapper kopfgMapper;

    @Autowired
    private KopfgService kopfgService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKopfgMockMvc;

    private Kopfg kopfg;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KopfgResource kopfgResource = new KopfgResource(kopfgService);
        this.restKopfgMockMvc = MockMvcBuilders.standaloneSetup(kopfgResource)
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
    public static Kopfg createEntity(EntityManager em) {
        Kopfg kopfg = new Kopfg()
            .setCode(DEFAULT_CODE)
            .setName(DEFAULT_NAME)
            .setValidUntil(DEFAULT_VALID_UNTIL)
            .setPreviousCodes(DEFAULT_PREVIOUS_CODES);
        return kopfg;
    }

    @Before
    public void initTest() {
        kopfg = createEntity(em);
    }

    @Test
    @Transactional
    public void createKopfg() throws Exception {
        int databaseSizeBeforeCreate = kopfgRepository.findAll().size();

        // Create the Kopfg
        KopfgDTO kopfgDTO = kopfgMapper.toDto(kopfg);
        restKopfgMockMvc.perform(post("/api/kopfgs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kopfgDTO)))
            .andExpect(status().isCreated());

        // Validate the Kopfg in the database
        List<Kopfg> kopfgList = kopfgRepository.findAll();
        assertThat(kopfgList).hasSize(databaseSizeBeforeCreate + 1);
        Kopfg testKopfg = kopfgList.get(kopfgList.size() - 1);
        assertThat(testKopfg.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testKopfg.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testKopfg.getValidUntil()).isEqualTo(DEFAULT_VALID_UNTIL);
        assertThat(testKopfg.getPreviousCodes()).isEqualTo(DEFAULT_PREVIOUS_CODES);
    }

    @Test
    @Transactional
    public void createKopfgWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kopfgRepository.findAll().size();

        // Create the Kopfg with an existing ID
        kopfg.setId(1L);
        KopfgDTO kopfgDTO = kopfgMapper.toDto(kopfg);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKopfgMockMvc.perform(post("/api/kopfgs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kopfgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kopfg in the database
        List<Kopfg> kopfgList = kopfgRepository.findAll();
        assertThat(kopfgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kopfgRepository.findAll().size();
        // set the field null
        kopfg.setCode(null);

        // Create the Kopfg, which fails.
        KopfgDTO kopfgDTO = kopfgMapper.toDto(kopfg);

        restKopfgMockMvc.perform(post("/api/kopfgs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kopfgDTO)))
            .andExpect(status().isBadRequest());

        List<Kopfg> kopfgList = kopfgRepository.findAll();
        assertThat(kopfgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = kopfgRepository.findAll().size();
        // set the field null
        kopfg.setName(null);

        // Create the Kopfg, which fails.
        KopfgDTO kopfgDTO = kopfgMapper.toDto(kopfg);

        restKopfgMockMvc.perform(post("/api/kopfgs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kopfgDTO)))
            .andExpect(status().isBadRequest());

        List<Kopfg> kopfgList = kopfgRepository.findAll();
        assertThat(kopfgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKopfgs() throws Exception {
        // Initialize the database
        kopfgRepository.saveAndFlush(kopfg);

        // Get all the kopfgList
        restKopfgMockMvc.perform(get("/api/kopfgs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kopfg.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].previousCodes").value(hasItem(DEFAULT_PREVIOUS_CODES.toString())));
    }


    @Test
    @Transactional
    public void getKopfg() throws Exception {
        // Initialize the database
        kopfgRepository.saveAndFlush(kopfg);

        // Get the kopfg
        restKopfgMockMvc.perform(get("/api/kopfgs/{id}", kopfg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kopfg.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.validUntil").value(DEFAULT_VALID_UNTIL.toString()))
            .andExpect(jsonPath("$.previousCodes").value(DEFAULT_PREVIOUS_CODES.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingKopfg() throws Exception {
        // Get the kopfg
        restKopfgMockMvc.perform(get("/api/kopfgs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKopfg() throws Exception {
        // Initialize the database
        kopfgRepository.saveAndFlush(kopfg);

        int databaseSizeBeforeUpdate = kopfgRepository.findAll().size();

        // Update the Kopfg
        Kopfg updatedKopfg = kopfgRepository.findById(kopfg.getId()).get();
        // Disconnect from session so that the updates on updatedKopfg are not directly saved in db
        em.detach(updatedKopfg);
        updatedKopfg
            .setCode(UPDATED_CODE)
            .setName(UPDATED_NAME)
            .setValidUntil(UPDATED_VALID_UNTIL)
            .setPreviousCodes(UPDATED_PREVIOUS_CODES);
        KopfgDTO kopfgDTO = kopfgMapper.toDto(updatedKopfg);

        restKopfgMockMvc.perform(put("/api/kopfgs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kopfgDTO)))
            .andExpect(status().isOk());

        // Validate the Kopfg in the database
        List<Kopfg> kopfgList = kopfgRepository.findAll();
        assertThat(kopfgList).hasSize(databaseSizeBeforeUpdate);
        Kopfg testKopfg = kopfgList.get(kopfgList.size() - 1);
        assertThat(testKopfg.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testKopfg.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testKopfg.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testKopfg.getPreviousCodes()).isEqualTo(UPDATED_PREVIOUS_CODES);
    }

    @Test
    @Transactional
    public void updateNonExistingKopfg() throws Exception {
        int databaseSizeBeforeUpdate = kopfgRepository.findAll().size();

        // Create the Kopfg
        KopfgDTO kopfgDTO = kopfgMapper.toDto(kopfg);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKopfgMockMvc.perform(put("/api/kopfgs")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kopfgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Kopfg in the database
        List<Kopfg> kopfgList = kopfgRepository.findAll();
        assertThat(kopfgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKopfg() throws Exception {
        // Initialize the database
        kopfgRepository.saveAndFlush(kopfg);

        int databaseSizeBeforeDelete = kopfgRepository.findAll().size();

        // Get the Kopfg
        restKopfgMockMvc.perform(delete("/api/kopfgs/{id}", kopfg.getId())
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Kopfg> kopfgList = kopfgRepository.findAll();
        assertThat(kopfgList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kopfg.class);
        Kopfg kopfg1 = new Kopfg();
        kopfg1.setId(1L);
        Kopfg kopfg2 = new Kopfg();
        kopfg2.setId(kopfg1.getId());
        assertThat(kopfg1).isEqualTo(kopfg2);
        kopfg2.setId(2L);
        assertThat(kopfg1).isNotEqualTo(kopfg2);
        kopfg1.setId(null);
        assertThat(kopfg1).isNotEqualTo(kopfg2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KopfgDTO.class);
        KopfgDTO kopfgDTO1 = new KopfgDTO();
        kopfgDTO1.setId(1L);
        KopfgDTO kopfgDTO2 = new KopfgDTO();
        assertThat(kopfgDTO1).isNotEqualTo(kopfgDTO2);
        kopfgDTO2.setId(kopfgDTO1.getId());
        assertThat(kopfgDTO1).isEqualTo(kopfgDTO2);
        kopfgDTO2.setId(2L);
        assertThat(kopfgDTO1).isNotEqualTo(kopfgDTO2);
        kopfgDTO1.setId(null);
        assertThat(kopfgDTO1).isNotEqualTo(kopfgDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(kopfgMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(kopfgMapper.fromId(null)).isNull();
    }
}
