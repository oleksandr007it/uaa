package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.UaaApp;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.Kopfg;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.repository.StatehoodSubjectRepository;
import com.idevhub.tapas.service.StatehoodSubjectService;
import com.idevhub.tapas.service.dto.StatehoodSubjectDTO;
import com.idevhub.tapas.service.mapper.StatehoodSubjectMapper;
import com.idevhub.tapas.web.rest.errors.ExceptionTranslator;
import org.apache.commons.lang3.RandomStringUtils;
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
 * Test class for the StatehoodSubjectResource REST controller.
 *
 * @see StatehoodSubjectResource
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApp.class)
public class StatehoodSubjectResourceIntTest {

    private static final String DEFAULT_SUBJECT_STATUS = UserStatus.ACTIVE;
    private static final String UPDATED_SUBJECT_STATUS = UserStatus.BLOCKED;

    private static final AccountDetailsStatus DEFAULT_ACCOUNT_DETAILS_STATUS = AccountDetailsStatus.FULL_NOT_CONFIRMED;
    private static final AccountDetailsStatus UPDATED_ACCOUNT_DETAILS_STATUS = AccountDetailsStatus.FULL_CONFIRMED;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SUBJECT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HEAD_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HEAD_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TEL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA@gmail.com";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB@gmail.com";

    private static final Boolean DEFAULT_IS_EMAIL_APPROVED = false;
    private static final Boolean UPDATED_IS_EMAIL_APPROVED = true;

    private static final String DEFAULT_EORI = "AAAAAAAAAA";
    private static final String UPDATED_EORI = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTUAL_SAME_AS_LEGAL_ADDRESS = false;
    private static final Boolean UPDATED_IS_ACTUAL_SAME_AS_LEGAL_ADDRESS = true;

    private static final String DEFAULT_LOGIN = "johndoerr";

    private static final String DEFAULT_FIRSTNAME = "john";

    private static final String DEFAULT_LASTNAME = "doe";

    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";

    private static final String DEFAULT_LANGKEY = "dummy";

    @Autowired
    private StatehoodSubjectRepository statehoodSubjectRepository;


    @Autowired
    private StatehoodSubjectMapper statehoodSubjectMapper;


    @Autowired
    private StatehoodSubjectService statehoodSubjectService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStatehoodSubjectMockMvc;

    private StatehoodSubject statehoodSubject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatehoodSubjectResource statehoodSubjectResource =
            new StatehoodSubjectResource(statehoodSubjectService);

        this.restStatehoodSubjectMockMvc = MockMvcBuilders.standaloneSetup(statehoodSubjectResource)
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
    public static StatehoodSubject createEntity(EntityManager em) {
        StatehoodSubject statehoodSubject = new StatehoodSubject()
            .setSubjectStatus(DEFAULT_SUBJECT_STATUS)
            .setAccountDetailsStatus(DEFAULT_ACCOUNT_DETAILS_STATUS)
            .setCreatedAt(DEFAULT_CREATED_AT)
            .setUpdatedAt(DEFAULT_UPDATED_AT)
            .setDeletedAt(DEFAULT_DELETED_AT)
            .setSubjectCode(DEFAULT_SUBJECT_CODE)
            .setSubjectName(DEFAULT_SUBJECT_NAME)
            .setSubjectShortName(DEFAULT_SUBJECT_SHORT_NAME)
            .setHeadFullName(DEFAULT_HEAD_FULL_NAME)
            .setTelNumber(DEFAULT_TEL_NUMBER)
            .setEmail(DEFAULT_EMAIL)
            .setEmailApproved(DEFAULT_IS_EMAIL_APPROVED)
            .setEori(DEFAULT_EORI)
            .setActualSameAsLegalAddress(DEFAULT_IS_ACTUAL_SAME_AS_LEGAL_ADDRESS);
        // Add required entity
        User createdBy = createStubbedUser();
        em.persist(createdBy);
        em.flush();
        statehoodSubject.setCreatedBy(createdBy.getLogin());
        // Add required entity
        Kopfg kopfg = KopfgResourceIntTest.createEntity(em);
        em.persist(kopfg);
        em.flush();
        statehoodSubject.setKopfg(kopfg);
        // Add required entity
        Address legalAddress = AddressResourceIntTest.createEntity(em);
        em.persist(legalAddress);
        em.flush();
        statehoodSubject.setLegalAddress(legalAddress);
        // Add required entity
        Address actualAddress = AddressResourceIntTest.createEntity(em);
        em.persist(actualAddress);
        em.flush();
        statehoodSubject.setActualAddress(actualAddress);
        return statehoodSubject;
    }

    private static User createStubbedUser() {
        User user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail(DEFAULT_EMAIL);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setLangKey(DEFAULT_LANGKEY);

        return user;
    }

    @Before
    public void initTest() {
        statehoodSubject = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatehoodSubject() throws Exception {
        int databaseSizeBeforeCreate = statehoodSubjectRepository.findAll().size();

        // Create the StatehoodSubject
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(statehoodSubject);
        restStatehoodSubjectMockMvc.perform(post("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isCreated());

        // Validate the StatehoodSubject in the database
        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeCreate + 1);
        StatehoodSubject testStatehoodSubject = statehoodSubjectList.get(statehoodSubjectList.size() - 1);
        assertThat(testStatehoodSubject.getSubjectStatus()).isEqualTo(DEFAULT_SUBJECT_STATUS);
        assertThat(testStatehoodSubject.getAccountDetailsStatus()).isEqualTo(DEFAULT_ACCOUNT_DETAILS_STATUS);
        assertThat(testStatehoodSubject.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testStatehoodSubject.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testStatehoodSubject.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
        assertThat(testStatehoodSubject.getSubjectCode()).isEqualTo(DEFAULT_SUBJECT_CODE);
        assertThat(testStatehoodSubject.getSubjectName()).isEqualTo(DEFAULT_SUBJECT_NAME);
        assertThat(testStatehoodSubject.getSubjectShortName()).isEqualTo(DEFAULT_SUBJECT_SHORT_NAME);
        assertThat(testStatehoodSubject.getHeadFullName()).isEqualTo(DEFAULT_HEAD_FULL_NAME);
        assertThat(testStatehoodSubject.getTelNumber()).isEqualTo(DEFAULT_TEL_NUMBER);
        assertThat(testStatehoodSubject.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testStatehoodSubject.isEmailApproved()).isEqualTo(DEFAULT_IS_EMAIL_APPROVED);
        assertThat(testStatehoodSubject.getEori()).isEqualTo(DEFAULT_EORI);
        assertThat(testStatehoodSubject.isActualSameAsLegalAddress()).isEqualTo(DEFAULT_IS_ACTUAL_SAME_AS_LEGAL_ADDRESS);
    }

    @Test
    @Transactional
    public void createStatehoodSubjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statehoodSubjectRepository.findAll().size();

        // Create the StatehoodSubject with an existing ID
        statehoodSubject.setId(1L);
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(statehoodSubject);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatehoodSubjectMockMvc.perform(post("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StatehoodSubject in the database
        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSubjectStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = statehoodSubjectRepository.findAll().size();
        // set the field null
        statehoodSubject.setSubjectStatus(null);

        // Create the StatehoodSubject, which fails.
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(statehoodSubject);

        restStatehoodSubjectMockMvc.perform(post("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isBadRequest());

        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountDetailsStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = statehoodSubjectRepository.findAll().size();
        // set the field null
        statehoodSubject.setAccountDetailsStatus(null);

        // Create the StatehoodSubject, which fails.
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(statehoodSubject);

        restStatehoodSubjectMockMvc.perform(post("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isBadRequest());

        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = statehoodSubjectRepository.findAll().size();
        // set the field null
        statehoodSubject.setCreatedAt(null);

        // Create the StatehoodSubject, which fails.
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(statehoodSubject);

        restStatehoodSubjectMockMvc.perform(post("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isBadRequest());

        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubjectCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = statehoodSubjectRepository.findAll().size();
        // set the field null
        statehoodSubject.setSubjectCode(null);

        // Create the StatehoodSubject, which fails.
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(statehoodSubject);

        restStatehoodSubjectMockMvc.perform(post("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isBadRequest());

        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubjectNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = statehoodSubjectRepository.findAll().size();
        // set the field null
        statehoodSubject.setSubjectName(null);

        // Create the StatehoodSubject, which fails.
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(statehoodSubject);

        restStatehoodSubjectMockMvc.perform(post("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isBadRequest());

        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubjectShortNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = statehoodSubjectRepository.findAll().size();
        // set the field null
        statehoodSubject.setSubjectShortName(null);

        // Create the StatehoodSubject, which fails.
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(statehoodSubject);

        restStatehoodSubjectMockMvc.perform(post("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isBadRequest());

        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = statehoodSubjectRepository.findAll().size();
        // set the field null
        statehoodSubject.setTelNumber(null);

        // Create the StatehoodSubject, which fails.
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(statehoodSubject);

        restStatehoodSubjectMockMvc.perform(post("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isBadRequest());

        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = statehoodSubjectRepository.findAll().size();
        // set the field null
        statehoodSubject.setEmail(null);

        // Create the StatehoodSubject, which fails.
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(statehoodSubject);

        restStatehoodSubjectMockMvc.perform(post("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isBadRequest());

        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEoriIsRequired() throws Exception {
        int databaseSizeBeforeTest = statehoodSubjectRepository.findAll().size();
        // set the field null
        statehoodSubject.setEori(null);

        // Create the StatehoodSubject, which fails.
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(statehoodSubject);

        restStatehoodSubjectMockMvc.perform(post("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isBadRequest());

        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStatehoodSubjects() throws Exception {
        // Initialize the database
        statehoodSubjectRepository.saveAndFlush(statehoodSubject);

        // Get all the statehoodSubjectList
        restStatehoodSubjectMockMvc.perform(get("/api/statehood-subjects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statehoodSubject.getId().intValue())))
            .andExpect(jsonPath("$.[*].subjectStatus").value(hasItem(DEFAULT_SUBJECT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].accountDetailsStatus").value(hasItem(DEFAULT_ACCOUNT_DETAILS_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())))
            .andExpect(jsonPath("$.[*].subjectCode").value(hasItem(DEFAULT_SUBJECT_CODE.toString())))
            .andExpect(jsonPath("$.[*].subjectName").value(hasItem(DEFAULT_SUBJECT_NAME.toString())))
            .andExpect(jsonPath("$.[*].subjectShortName").value(hasItem(DEFAULT_SUBJECT_SHORT_NAME.toString())))
            .andExpect(jsonPath("$.[*].headFullName").value(hasItem(DEFAULT_HEAD_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].telNumber").value(hasItem(DEFAULT_TEL_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].isEmailApproved").value(hasItem(DEFAULT_IS_EMAIL_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].eori").value(hasItem(DEFAULT_EORI.toString())))
            .andExpect(jsonPath("$.[*].isActualSameAsLegalAddress").value(hasItem(DEFAULT_IS_ACTUAL_SAME_AS_LEGAL_ADDRESS.booleanValue())));
    }


    @Test
    @Transactional
    public void getStatehoodSubject() throws Exception {
        // Initialize the database
        statehoodSubjectRepository.saveAndFlush(statehoodSubject);

        // Get the statehoodSubject
        restStatehoodSubjectMockMvc.perform(get("/api/statehood-subjects/{id}", statehoodSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(statehoodSubject.getId().intValue()))
            .andExpect(jsonPath("$.subjectStatus").value(DEFAULT_SUBJECT_STATUS.toString()))
            .andExpect(jsonPath("$.accountDetailsStatus").value(DEFAULT_ACCOUNT_DETAILS_STATUS.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()))
            .andExpect(jsonPath("$.subjectCode").value(DEFAULT_SUBJECT_CODE.toString()))
            .andExpect(jsonPath("$.subjectName").value(DEFAULT_SUBJECT_NAME.toString()))
            .andExpect(jsonPath("$.subjectShortName").value(DEFAULT_SUBJECT_SHORT_NAME.toString()))
            .andExpect(jsonPath("$.headFullName").value(DEFAULT_HEAD_FULL_NAME.toString()))
            .andExpect(jsonPath("$.telNumber").value(DEFAULT_TEL_NUMBER.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.isEmailApproved").value(DEFAULT_IS_EMAIL_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.eori").value(DEFAULT_EORI.toString()))
            .andExpect(jsonPath("$.isActualSameAsLegalAddress").value(DEFAULT_IS_ACTUAL_SAME_AS_LEGAL_ADDRESS.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingStatehoodSubject() throws Exception {
        // Get the statehoodSubject
        restStatehoodSubjectMockMvc.perform(get("/api/statehood-subjects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatehoodSubject() throws Exception {
        // Initialize the database
        statehoodSubjectRepository.saveAndFlush(statehoodSubject);

        int databaseSizeBeforeUpdate = statehoodSubjectRepository.findAll().size();

        // Update the statehoodSubject
        StatehoodSubject updatedStatehoodSubject = statehoodSubjectRepository.findById(statehoodSubject.getId()).get();
        // Disconnect from session so that the updates on updatedStatehoodSubject are not directly saved in db
        em.detach(updatedStatehoodSubject);
        updatedStatehoodSubject
            .setSubjectStatus(UPDATED_SUBJECT_STATUS)
            .setAccountDetailsStatus(UPDATED_ACCOUNT_DETAILS_STATUS)
            .setCreatedAt(UPDATED_CREATED_AT)
            .setUpdatedAt(UPDATED_UPDATED_AT)
            .setDeletedAt(UPDATED_DELETED_AT)
            .setSubjectCode(UPDATED_SUBJECT_CODE)
            .setSubjectName(UPDATED_SUBJECT_NAME)
            .setSubjectShortName(UPDATED_SUBJECT_SHORT_NAME)
            .setHeadFullName(UPDATED_HEAD_FULL_NAME)
            .setTelNumber(UPDATED_TEL_NUMBER)
            .setEmail(UPDATED_EMAIL)
            .setEmailApproved(UPDATED_IS_EMAIL_APPROVED)
            .setEori(UPDATED_EORI)
            .setActualSameAsLegalAddress(UPDATED_IS_ACTUAL_SAME_AS_LEGAL_ADDRESS);
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(updatedStatehoodSubject);

        restStatehoodSubjectMockMvc.perform(put("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isOk());

        // Validate the StatehoodSubject in the database
        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeUpdate);
        StatehoodSubject testStatehoodSubject = statehoodSubjectList.get(statehoodSubjectList.size() - 1);
        assertThat(testStatehoodSubject.getSubjectStatus()).isEqualTo(UPDATED_SUBJECT_STATUS);
        assertThat(testStatehoodSubject.getAccountDetailsStatus()).isEqualTo(UPDATED_ACCOUNT_DETAILS_STATUS);
        assertThat(testStatehoodSubject.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testStatehoodSubject.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testStatehoodSubject.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
        assertThat(testStatehoodSubject.getSubjectCode()).isEqualTo(UPDATED_SUBJECT_CODE);
        assertThat(testStatehoodSubject.getSubjectName()).isEqualTo(UPDATED_SUBJECT_NAME);
        assertThat(testStatehoodSubject.getSubjectShortName()).isEqualTo(UPDATED_SUBJECT_SHORT_NAME);
        assertThat(testStatehoodSubject.getHeadFullName()).isEqualTo(UPDATED_HEAD_FULL_NAME);
        assertThat(testStatehoodSubject.getTelNumber()).isEqualTo(UPDATED_TEL_NUMBER);
        assertThat(testStatehoodSubject.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testStatehoodSubject.isEmailApproved()).isEqualTo(UPDATED_IS_EMAIL_APPROVED);
        assertThat(testStatehoodSubject.getEori()).isEqualTo(UPDATED_EORI);
        assertThat(testStatehoodSubject.isActualSameAsLegalAddress()).isEqualTo(UPDATED_IS_ACTUAL_SAME_AS_LEGAL_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingStatehoodSubject() throws Exception {
        int databaseSizeBeforeUpdate = statehoodSubjectRepository.findAll().size();

        // Create the StatehoodSubject
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectMapper.toDto(statehoodSubject);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStatehoodSubjectMockMvc.perform(put("/api/statehood-subjects")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(statehoodSubjectDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StatehoodSubject in the database
        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatehoodSubject() throws Exception {
        // Initialize the database
        statehoodSubjectRepository.saveAndFlush(statehoodSubject);

        int databaseSizeBeforeDelete = statehoodSubjectRepository.findAll().size();

        // Get the statehoodSubject
        restStatehoodSubjectMockMvc.perform(delete("/api/statehood-subjects/{id}", statehoodSubject.getId())
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StatehoodSubject> statehoodSubjectList = statehoodSubjectRepository.findAll();
        assertThat(statehoodSubjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatehoodSubject.class);
        StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(1L);
        StatehoodSubject statehoodSubject2 = new StatehoodSubject();
        statehoodSubject2.setId(statehoodSubject1.getId());
        assertThat(statehoodSubject1).isEqualTo(statehoodSubject2);
        statehoodSubject2.setId(2L);
        assertThat(statehoodSubject1).isNotEqualTo(statehoodSubject2);
        statehoodSubject1.setId(null);
        assertThat(statehoodSubject1).isNotEqualTo(statehoodSubject2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatehoodSubjectDTO.class);
        StatehoodSubjectDTO statehoodSubjectDTO1 = new StatehoodSubjectDTO();
        statehoodSubjectDTO1.setId(1L);
        StatehoodSubjectDTO statehoodSubjectDTO2 = new StatehoodSubjectDTO();
        assertThat(statehoodSubjectDTO1).isNotEqualTo(statehoodSubjectDTO2);
        statehoodSubjectDTO2.setId(statehoodSubjectDTO1.getId());
        assertThat(statehoodSubjectDTO1).isEqualTo(statehoodSubjectDTO2);
        statehoodSubjectDTO2.setId(2L);
        assertThat(statehoodSubjectDTO1).isNotEqualTo(statehoodSubjectDTO2);
        statehoodSubjectDTO1.setId(null);
        assertThat(statehoodSubjectDTO1).isNotEqualTo(statehoodSubjectDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(statehoodSubjectMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(statehoodSubjectMapper.fromId(null)).isNull();
    }
}
