package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.UaaApp;
import com.idevhub.tapas.domain.EmailConfirmation;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.enumeration.EmailConfirmationStatus;
import com.idevhub.tapas.domain.enumeration.EmailRejectionReason;
import com.idevhub.tapas.repository.EmailConfirmationRepository;
import com.idevhub.tapas.service.EmailConfirmationService;
import com.idevhub.tapas.service.dto.EmailConfirmationDTO;
import com.idevhub.tapas.service.mapper.EmailConfirmationMapper;
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
 * Test class for the EmailConfirmationResource REST controller.
 *
 * @see EmailConfirmationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApp.class)
public class EmailConfirmationResourceIntTest {

    private static final EmailConfirmationStatus DEFAULT_CONFIRMATION_STATUS = EmailConfirmationStatus.ACTIVE;
    private static final EmailConfirmationStatus UPDATED_CONFIRMATION_STATUS = EmailConfirmationStatus.CONFIRMED;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_UNTIL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_UNTIL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_HTML_TEMPLATE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HTML_TEMPLATE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LANG_KEY = "AAAAAAAAAA";
    private static final String UPDATED_LANG_KEY = "BBBBBBBBBB";

    private static final Instant DEFAULT_REJECTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REJECTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EmailRejectionReason DEFAULT_REJECT_DESCRIPTION = EmailRejectionReason.EMAIL_NOT_VALID;
    private static final EmailRejectionReason UPDATED_REJECT_DESCRIPTION = EmailRejectionReason.INTERNAL_ERROR;

    private static final Instant DEFAULT_APPROVED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPROVED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_IP_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_IP_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private EmailConfirmationRepository emailConfirmationRepository;


    @Autowired
    private EmailConfirmationMapper emailConfirmationMapper;


    @Autowired
    private EmailConfirmationService emailConfirmationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmailConfirmationMockMvc;

    private EmailConfirmation emailConfirmation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmailConfirmationResource emailConfirmationResource = new EmailConfirmationResource(emailConfirmationService);
        this.restEmailConfirmationMockMvc = MockMvcBuilders.standaloneSetup(emailConfirmationResource)
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
    public static EmailConfirmation createEntity(EntityManager em) {
        EmailConfirmation emailConfirmation = new EmailConfirmation()
            .confirmationStatus(DEFAULT_CONFIRMATION_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .validUntil(DEFAULT_VALID_UNTIL)
            .email(DEFAULT_EMAIL)
            .htmlTemplateName(DEFAULT_HTML_TEMPLATE_NAME)
            .langKey(DEFAULT_LANG_KEY)
            .rejectedAt(DEFAULT_REJECTED_AT)
            .rejectDescription(DEFAULT_REJECT_DESCRIPTION)
            .approvedAt(DEFAULT_APPROVED_AT)
            .ipAddress(DEFAULT_IP_ADDRESS);
        // Add required entity
        User createdBy = UserResourceIT.createEntityUser(em);
        em.persist(createdBy);
        em.flush();
        emailConfirmation.setCreatedBy(createdBy);
        return emailConfirmation;
    }

    @Before
    public void initTest() {
        emailConfirmation = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailConfirmation() throws Exception {
        int databaseSizeBeforeCreate = emailConfirmationRepository.findAll().size();

        // Create the EmailConfirmation
        EmailConfirmationDTO emailConfirmationDTO = emailConfirmationMapper.toDto(emailConfirmation);
        restEmailConfirmationMockMvc.perform(post("/api/email-confirmations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailConfirmationDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailConfirmation in the database
        List<EmailConfirmation> emailConfirmationList = emailConfirmationRepository.findAll();
        assertThat(emailConfirmationList).hasSize(databaseSizeBeforeCreate + 1);
        EmailConfirmation testEmailConfirmation = emailConfirmationList.get(emailConfirmationList.size() - 1);
        assertThat(testEmailConfirmation.getConfirmationStatus()).isEqualTo(DEFAULT_CONFIRMATION_STATUS);
        assertThat(testEmailConfirmation.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testEmailConfirmation.getValidUntil()).isEqualTo(DEFAULT_VALID_UNTIL);
        assertThat(testEmailConfirmation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmailConfirmation.getHtmlTemplateName()).isEqualTo(DEFAULT_HTML_TEMPLATE_NAME);
        assertThat(testEmailConfirmation.getLangKey()).isEqualTo(DEFAULT_LANG_KEY);
        assertThat(testEmailConfirmation.getRejectedAt()).isEqualTo(DEFAULT_REJECTED_AT);
        assertThat(testEmailConfirmation.getRejectDescription()).isEqualTo(DEFAULT_REJECT_DESCRIPTION);
        assertThat(testEmailConfirmation.getApprovedAt()).isEqualTo(DEFAULT_APPROVED_AT);
        assertThat(testEmailConfirmation.getIpAddress()).isEqualTo(DEFAULT_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void createEmailConfirmationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailConfirmationRepository.findAll().size();

        // Create the EmailConfirmation with an existing ID
        emailConfirmation.setId(1L);
        EmailConfirmationDTO emailConfirmationDTO = emailConfirmationMapper.toDto(emailConfirmation);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailConfirmationMockMvc.perform(post("/api/email-confirmations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailConfirmationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailConfirmation in the database
        List<EmailConfirmation> emailConfirmationList = emailConfirmationRepository.findAll();
        assertThat(emailConfirmationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkConfirmationStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailConfirmationRepository.findAll().size();
        // set the field null
        emailConfirmation.setConfirmationStatus(null);

        // Create the EmailConfirmation, which fails.
        EmailConfirmationDTO emailConfirmationDTO = emailConfirmationMapper.toDto(emailConfirmation);

        restEmailConfirmationMockMvc.perform(post("/api/email-confirmations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailConfirmationDTO)))
            .andExpect(status().isBadRequest());

        List<EmailConfirmation> emailConfirmationList = emailConfirmationRepository.findAll();
        assertThat(emailConfirmationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailConfirmationRepository.findAll().size();
        // set the field null
        emailConfirmation.setCreatedAt(null);

        // Create the EmailConfirmation, which fails.
        EmailConfirmationDTO emailConfirmationDTO = emailConfirmationMapper.toDto(emailConfirmation);

        restEmailConfirmationMockMvc.perform(post("/api/email-confirmations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailConfirmationDTO)))
            .andExpect(status().isBadRequest());

        List<EmailConfirmation> emailConfirmationList = emailConfirmationRepository.findAll();
        assertThat(emailConfirmationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidUntilIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailConfirmationRepository.findAll().size();
        // set the field null
        emailConfirmation.setValidUntil(null);

        // Create the EmailConfirmation, which fails.
        EmailConfirmationDTO emailConfirmationDTO = emailConfirmationMapper.toDto(emailConfirmation);

        restEmailConfirmationMockMvc.perform(post("/api/email-confirmations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailConfirmationDTO)))
            .andExpect(status().isBadRequest());

        List<EmailConfirmation> emailConfirmationList = emailConfirmationRepository.findAll();
        assertThat(emailConfirmationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailConfirmationRepository.findAll().size();
        // set the field null
        emailConfirmation.setEmail(null);

        // Create the EmailConfirmation, which fails.
        EmailConfirmationDTO emailConfirmationDTO = emailConfirmationMapper.toDto(emailConfirmation);

        restEmailConfirmationMockMvc.perform(post("/api/email-confirmations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailConfirmationDTO)))
            .andExpect(status().isBadRequest());

        List<EmailConfirmation> emailConfirmationList = emailConfirmationRepository.findAll();
        assertThat(emailConfirmationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHtmlTemplateNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailConfirmationRepository.findAll().size();
        // set the field null
        emailConfirmation.setHtmlTemplateName(null);

        // Create the EmailConfirmation, which fails.
        EmailConfirmationDTO emailConfirmationDTO = emailConfirmationMapper.toDto(emailConfirmation);

        restEmailConfirmationMockMvc.perform(post("/api/email-confirmations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailConfirmationDTO)))
            .andExpect(status().isBadRequest());

        List<EmailConfirmation> emailConfirmationList = emailConfirmationRepository.findAll();
        assertThat(emailConfirmationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLangKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailConfirmationRepository.findAll().size();
        // set the field null
        emailConfirmation.setLangKey(null);

        // Create the EmailConfirmation, which fails.
        EmailConfirmationDTO emailConfirmationDTO = emailConfirmationMapper.toDto(emailConfirmation);

        restEmailConfirmationMockMvc.perform(post("/api/email-confirmations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailConfirmationDTO)))
            .andExpect(status().isBadRequest());

        List<EmailConfirmation> emailConfirmationList = emailConfirmationRepository.findAll();
        assertThat(emailConfirmationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailConfirmations() throws Exception {
        // Initialize the database
        emailConfirmationRepository.saveAndFlush(emailConfirmation);

        // Get all the emailConfirmationList
        restEmailConfirmationMockMvc.perform(get("/api/email-confirmations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailConfirmation.getId().intValue())))
            .andExpect(jsonPath("$.[*].confirmationStatus").value(hasItem(DEFAULT_CONFIRMATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].validUntil").value(hasItem(DEFAULT_VALID_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].htmlTemplateName").value(hasItem(DEFAULT_HTML_TEMPLATE_NAME.toString())))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY.toString())))
            .andExpect(jsonPath("$.[*].rejectedAt").value(hasItem(DEFAULT_REJECTED_AT.toString())))
            .andExpect(jsonPath("$.[*].rejectDescription").value(hasItem(DEFAULT_REJECT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].approvedAt").value(hasItem(DEFAULT_APPROVED_AT.toString())))
            .andExpect(jsonPath("$.[*].ipAddress").value(hasItem(DEFAULT_IP_ADDRESS.toString())));
    }


    @Test
    @Transactional
    public void getEmailConfirmation() throws Exception {
        // Initialize the database
        emailConfirmationRepository.saveAndFlush(emailConfirmation);

        // Get the emailConfirmation
        restEmailConfirmationMockMvc.perform(get("/api/email-confirmations/{id}", emailConfirmation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailConfirmation.getId().intValue()))
            .andExpect(jsonPath("$.confirmationStatus").value(DEFAULT_CONFIRMATION_STATUS.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.validUntil").value(DEFAULT_VALID_UNTIL.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.htmlTemplateName").value(DEFAULT_HTML_TEMPLATE_NAME.toString()))
            .andExpect(jsonPath("$.langKey").value(DEFAULT_LANG_KEY.toString()))
            .andExpect(jsonPath("$.rejectedAt").value(DEFAULT_REJECTED_AT.toString()))
            .andExpect(jsonPath("$.rejectDescription").value(DEFAULT_REJECT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.approvedAt").value(DEFAULT_APPROVED_AT.toString()))
            .andExpect(jsonPath("$.ipAddress").value(DEFAULT_IP_ADDRESS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmailConfirmation() throws Exception {
        // Get the emailConfirmation
        restEmailConfirmationMockMvc.perform(get("/api/email-confirmations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailConfirmation() throws Exception {
        // Initialize the database
        emailConfirmationRepository.saveAndFlush(emailConfirmation);

        int databaseSizeBeforeUpdate = emailConfirmationRepository.findAll().size();

        // Update the emailConfirmation
        EmailConfirmation updatedEmailConfirmation = emailConfirmationRepository.findById(emailConfirmation.getId()).get();
        // Disconnect from session so that the updates on updatedEmailConfirmation are not directly saved in db
        em.detach(updatedEmailConfirmation);
        updatedEmailConfirmation
            .confirmationStatus(UPDATED_CONFIRMATION_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .validUntil(UPDATED_VALID_UNTIL)
            .email(UPDATED_EMAIL)
            .htmlTemplateName(UPDATED_HTML_TEMPLATE_NAME)
            .langKey(UPDATED_LANG_KEY)
            .rejectedAt(UPDATED_REJECTED_AT)
            .rejectDescription(UPDATED_REJECT_DESCRIPTION)
            .approvedAt(UPDATED_APPROVED_AT)
            .ipAddress(UPDATED_IP_ADDRESS);
        EmailConfirmationDTO emailConfirmationDTO = emailConfirmationMapper.toDto(updatedEmailConfirmation);

        restEmailConfirmationMockMvc.perform(put("/api/email-confirmations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailConfirmationDTO)))
            .andExpect(status().isOk());

        // Validate the EmailConfirmation in the database
        List<EmailConfirmation> emailConfirmationList = emailConfirmationRepository.findAll();
        assertThat(emailConfirmationList).hasSize(databaseSizeBeforeUpdate);
        EmailConfirmation testEmailConfirmation = emailConfirmationList.get(emailConfirmationList.size() - 1);
        assertThat(testEmailConfirmation.getConfirmationStatus()).isEqualTo(UPDATED_CONFIRMATION_STATUS);
        assertThat(testEmailConfirmation.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testEmailConfirmation.getValidUntil()).isEqualTo(UPDATED_VALID_UNTIL);
        assertThat(testEmailConfirmation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmailConfirmation.getHtmlTemplateName()).isEqualTo(UPDATED_HTML_TEMPLATE_NAME);
        assertThat(testEmailConfirmation.getLangKey()).isEqualTo(UPDATED_LANG_KEY);
        assertThat(testEmailConfirmation.getRejectedAt()).isEqualTo(UPDATED_REJECTED_AT);
        assertThat(testEmailConfirmation.getRejectDescription()).isEqualTo(UPDATED_REJECT_DESCRIPTION);
        assertThat(testEmailConfirmation.getApprovedAt()).isEqualTo(UPDATED_APPROVED_AT);
        assertThat(testEmailConfirmation.getIpAddress()).isEqualTo(UPDATED_IP_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailConfirmation() throws Exception {
        int databaseSizeBeforeUpdate = emailConfirmationRepository.findAll().size();

        // Create the EmailConfirmation
        EmailConfirmationDTO emailConfirmationDTO = emailConfirmationMapper.toDto(emailConfirmation);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmailConfirmationMockMvc.perform(put("/api/email-confirmations")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(emailConfirmationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailConfirmation in the database
        List<EmailConfirmation> emailConfirmationList = emailConfirmationRepository.findAll();
        assertThat(emailConfirmationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailConfirmation() throws Exception {
        // Initialize the database
        emailConfirmationRepository.saveAndFlush(emailConfirmation);

        int databaseSizeBeforeDelete = emailConfirmationRepository.findAll().size();

        // Get the emailConfirmation
        restEmailConfirmationMockMvc.perform(delete("/api/email-confirmations/{id}", emailConfirmation.getId())
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmailConfirmation> emailConfirmationList = emailConfirmationRepository.findAll();
        assertThat(emailConfirmationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailConfirmation.class);
        EmailConfirmation emailConfirmation1 = new EmailConfirmation();
        emailConfirmation1.setId(1L);
        EmailConfirmation emailConfirmation2 = new EmailConfirmation();
        emailConfirmation2.setId(emailConfirmation1.getId());
        assertThat(emailConfirmation1).isEqualTo(emailConfirmation2);
        emailConfirmation2.setId(2L);
        assertThat(emailConfirmation1).isNotEqualTo(emailConfirmation2);
        emailConfirmation1.setId(null);
        assertThat(emailConfirmation1).isNotEqualTo(emailConfirmation2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailConfirmationDTO.class);
        EmailConfirmationDTO emailConfirmationDTO1 = new EmailConfirmationDTO();
        emailConfirmationDTO1.setId(1L);
        EmailConfirmationDTO emailConfirmationDTO2 = new EmailConfirmationDTO();
        assertThat(emailConfirmationDTO1).isNotEqualTo(emailConfirmationDTO2);
        emailConfirmationDTO2.setId(emailConfirmationDTO1.getId());
        assertThat(emailConfirmationDTO1).isEqualTo(emailConfirmationDTO2);
        emailConfirmationDTO2.setId(2L);
        assertThat(emailConfirmationDTO1).isNotEqualTo(emailConfirmationDTO2);
        emailConfirmationDTO1.setId(null);
        assertThat(emailConfirmationDTO1).isNotEqualTo(emailConfirmationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emailConfirmationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emailConfirmationMapper.fromId(null)).isNull();
    }
}
