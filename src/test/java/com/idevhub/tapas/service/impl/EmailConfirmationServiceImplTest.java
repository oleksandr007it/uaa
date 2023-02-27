package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.config.UaaProperties;
import com.idevhub.tapas.domain.*;
import com.idevhub.tapas.domain.address.*;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.domain.enumeration.EmailConfirmationStatus;
import com.idevhub.tapas.domain.enumeration.EmailRejectionReason;
import com.idevhub.tapas.repository.EmailConfirmationRepository;
import com.idevhub.tapas.repository.StatehoodSubjectRepository;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.service.dto.EmailConfirmationDTO;
import com.idevhub.tapas.service.mapper.EmailConfirmationMapper;
import io.github.jhipster.security.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class EmailConfirmationServiceImplTest {
    private static final String DEFAULT_FIRSTNAME = "Адміністратор";
    private static final String DEFAULT_LASTNAME = "Міністренко";
    private static final String STUBBED_MIDDLE_NAME = "Дміністрович";
    private static final String STUBBED_RNOKPP = "3791012344";
    private static final String DEFAULT_LOGIN = "johndoe";
    private static final String DEFAULT_EMAIL = "abc@gmail.com";
    private static final String DEFAULT_LANGKEY = "en";

    private static final Integer KOPFG_DEFAULT_CODE = 3;
    private static final String KOPFG_DEFAULT_NAME = "AAAAAAAAAA";
    private static final Instant KOPFG_DEFAULT_VALID_UNTIL = Instant.ofEpochMilli(0L);
    private static final String KOPFG_DEFAULT_PREVIOUS_CODES = "AAAAAAAAAA";

    @Mock
    private EmailConfirmationRepository mockEmailConfirmationRepository;
    @Mock
    private EmailConfirmationMapper mockEmailConfirmationMapper;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private StatehoodSubjectRepository mockStatehoodSubjectRepository;
    @Mock
    private CacheManager mockCacheManager;
    @Mock
    private UaaProperties mockUaaProperties;

    private EmailConfirmationServiceImpl emailConfirmationServiceImplUnderTest;

    private User user = createStubbedUser();
    private StatehoodSubject subject = createStatehoodSubject();

    @BeforeEach
    void setUp() {
        initMocks(this);
        emailConfirmationServiceImplUnderTest = new EmailConfirmationServiceImpl(mockEmailConfirmationRepository, mockEmailConfirmationMapper, mockUserRepository, mockStatehoodSubjectRepository, mockCacheManager, mockUaaProperties);
    }

    private EmailConfirmationDTO getEmailConfirmationDTO() {
        EmailConfirmationDTO dto = new EmailConfirmationDTO();

        dto.setId(0L);
        dto.setConfirmationStatus(EmailConfirmationStatus.ACTIVE);
        dto.setCreatedAt(Instant.ofEpochSecond(0L));
        dto.setValidUntil(Instant.now().plus(10, ChronoUnit.DAYS));
        dto.setEmail(DEFAULT_EMAIL);
        dto.setHtmlTemplateName("htmlTemplateName");
        dto.setLangKey("en");
        dto.setRejectedAt(Instant.ofEpochSecond(0L));
        dto.setRejectDescription(EmailRejectionReason.EMAIL_NOT_VALID);
        dto.setApprovedAt(Instant.ofEpochSecond(0L));
        dto.setIpAddress("192.168.31.1");
        dto.setDeclarantId(0L);

        return dto;
    }

    private EmailConfirmation getEmailConfirmation() {
        EmailConfirmation stub = new EmailConfirmation();

        stub.setId(0L);
        stub.setConfirmationStatus(EmailConfirmationStatus.ACTIVE);
        stub.setCreatedAt(Instant.ofEpochSecond(0L));
        stub.setValidUntil(Instant.now().plus(10, ChronoUnit.DAYS));
        stub.setEmail(DEFAULT_EMAIL);
        stub.setHtmlTemplateName("htmlTemplateName");
        stub.setLangKey("en");
        stub.setRejectedAt(Instant.ofEpochSecond(0L));
        stub.setRejectDescription(EmailRejectionReason.EMAIL_NOT_VALID);
        stub.setApprovedAt(Instant.ofEpochSecond(0L));
        stub.setIpAddress("192.168.31.1");
        stub.setDeclarant(user);

        return stub;
    }

    public User createStubbedUser() {
        User user = new User();

        user.setId(0L);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.random(60));
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setMiddleName(STUBBED_MIDDLE_NAME);
        user.setRnokpp(STUBBED_RNOKPP);
        user.setEmail(DEFAULT_EMAIL);
        user.setActivated(true);
        user.setLangKey(DEFAULT_LANGKEY);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);

        Set<Authority> authorities = new HashSet<>();
        Authority role = new Authority();
        role.setName("ROLE_ADMIN");
        authorities.add(role);

        user.setAuthorities(authorities);
        user.setLegalAddress(createStubbedAddress());

        return user;
    }

    private Address createStubbedAddress() {
        Address legalAddress = new Address();

        legalAddress.setCountryCode("804");
        legalAddress.setPostalCode("12345");
        legalAddress.setHouseNumber("house.12");
        legalAddress.setPavilionNumber("block");
        legalAddress.setApartmentNumber("3");
        legalAddress.setAtsObjectId(0L);

        return legalAddress;
    }

    private NaisAtsObject getNaisAtsObject(int i) {
        NaisAtsObject adminTerritorialObject = new NaisAtsObject();

        adminTerritorialObject.setId(0L);
        adminTerritorialObject.setKoatuuCode("123456789" + i);
        adminTerritorialObject.setName("name");
        adminTerritorialObject.setType(getNaisAtsObjectType(i));
        adminTerritorialObject.setStatus(getNaisAtsObjectStatus(i));

        return adminTerritorialObject;
    }

    private NaisAtsObjectStatus getNaisAtsObjectStatus(int i) {
        NaisAtsObjectStatus naisAtsObjectStatus = new NaisAtsObjectStatus();

        naisAtsObjectStatus.setCode(0L);
        naisAtsObjectStatus.setActive(false);
        naisAtsObjectStatus.setDescription("description");

        return naisAtsObjectStatus;
    }

    private NaisAtsObjectType getNaisAtsObjectType(int i) {

        NaisAtsObjectType type = new NaisAtsObjectType();
        type.setLevel(1L);
        type.setCode(1L);
        type.setShortName("shortName" + i);
        type.setFullName("fullName" + i);

        return type;
    }

    public StatehoodSubject createStatehoodSubject() {
        StatehoodSubject statehoodSubject = new StatehoodSubject();

        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.FULL_CONFIRMED);
        statehoodSubject.setSubjectCode("MV/123456");
        statehoodSubject.setSubjectName("name1");
        statehoodSubject.setSubjectShortName("shortName1");
        statehoodSubject.setTelNumber("380631234567");
        statehoodSubject.setEmail(DEFAULT_EMAIL);
        statehoodSubject.setEori("123987234987");
        statehoodSubject.setActualSameAsLegalAddress(true);
        statehoodSubject.setKopfg(createKopfg());
        statehoodSubject.setLegalAddress(createStubbedAddress());
        statehoodSubject.setActualAddress(createStubbedAddress());
        statehoodSubject.setCreatedAt(Instant.now());
        statehoodSubject.setCreatedBy("admin");
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setEmailApproved(true);

        return statehoodSubject;
    }

    public Kopfg createKopfg() {
        Kopfg kopfg = new Kopfg()
            .setCode(KOPFG_DEFAULT_CODE)
            .setName(KOPFG_DEFAULT_NAME)
            .setValidUntil(KOPFG_DEFAULT_VALID_UNTIL)
            .setPreviousCodes(KOPFG_DEFAULT_PREVIOUS_CODES);

        kopfg.setId(0L);

        return kopfg;
    }

    @Test
    void testSave() {
        // Setup
        final EmailConfirmationDTO emailConfirmationDTO = getEmailConfirmationDTO();
        final EmailConfirmationDTO expectedResult = getEmailConfirmationDTO();
        final EmailConfirmation emailConfirmation = getEmailConfirmation();

        when(mockEmailConfirmationMapper.toEntity(any(EmailConfirmationDTO.class))).thenReturn(emailConfirmation);
        when(mockEmailConfirmationRepository.save(any(EmailConfirmation.class))).thenReturn(emailConfirmation);
        when(mockEmailConfirmationMapper.toDto(any(EmailConfirmation.class))).thenReturn(expectedResult);

        // Run the test
        final EmailConfirmationDTO result = emailConfirmationServiceImplUnderTest.save(emailConfirmationDTO);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testSave1() {
        // Setup

        final EmailConfirmation expectedResult = getEmailConfirmation();
        final UaaProperties.Mail mail = new UaaProperties.Mail();
        mail.setConfirmationUrl("confirmationUrl");
        mail.setEmailValidMinutes(0);

        when(mockEmailConfirmationMapper.toEntity(any(User.class))).thenReturn(expectedResult);
        when(mockUaaProperties.getMail()).thenReturn(mail);
        when(mockEmailConfirmationRepository.save(any(EmailConfirmation.class))).thenReturn(expectedResult);

        // Run the test
        final EmailConfirmation result =
            emailConfirmationServiceImplUnderTest.save(user, "htmlTemplateName", "to", subject);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFindAllActive() {
        // Setup
        final EmailConfirmationDTO confirmationDTO = getEmailConfirmationDTO();
        final List<EmailConfirmationDTO> expectedResult = Arrays.asList(confirmationDTO);
        final EmailConfirmation emailConfirmation = getEmailConfirmation();
        final List<EmailConfirmation> emailConfirmations = Arrays.asList(emailConfirmation);

        when(mockEmailConfirmationRepository.findAllByConfirmationStatusIn(any(List.class))).thenReturn(emailConfirmations);
        when(mockEmailConfirmationMapper.toDto(any(EmailConfirmation.class))).thenReturn(confirmationDTO);

        // Run the test
        final List<EmailConfirmationDTO> result = emailConfirmationServiceImplUnderTest.findAllActive();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFindOne() {
        // Setup
        final EmailConfirmationDTO confirmationDTO = getEmailConfirmationDTO();
        final Optional<EmailConfirmationDTO> expectedResult = Optional.of(confirmationDTO);
        final EmailConfirmation emailConfirmation = getEmailConfirmation();
        final Optional<EmailConfirmation> emailConfirmationOpt = Optional.of(emailConfirmation);

        when(mockEmailConfirmationRepository.findById(anyLong())).thenReturn(emailConfirmationOpt);
        when(mockEmailConfirmationMapper.toDto(any(EmailConfirmation.class))).thenReturn(confirmationDTO);

        // Run the test
        final Optional<EmailConfirmationDTO> result = emailConfirmationServiceImplUnderTest.findOne(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testDelete() {
        // Setup

        // Run the test
        emailConfirmationServiceImplUnderTest.delete(0L);

        // Verify the results
        verify(mockEmailConfirmationRepository).deleteById(0L);
    }

    @Test
    void testConfirmEmail() {
        // Setup
        final EmailConfirmationDTO expectedResult = getEmailConfirmationDTO();
        final EmailConfirmation emailConfirmation = getEmailConfirmation();
        final Optional<EmailConfirmation> emailConfirmationOpt = Optional.of(emailConfirmation);

        when(mockEmailConfirmationRepository.findOneById(anyLong())).thenReturn(emailConfirmationOpt);
        when(mockEmailConfirmationRepository.save(any(EmailConfirmation.class))).thenReturn(emailConfirmation);
        when(mockStatehoodSubjectRepository.save(new StatehoodSubject())).thenReturn(subject);
        when(mockUserRepository.save(any(User.class))).thenReturn(user);

        CacheManager cacheManager = new NoOpCacheManager();
        Cache cache = cacheManager.getCache("abc");
        when(mockCacheManager.getCache(any())).thenReturn(cache);
//        when(mockCacheManager.getCache(RepoConst.USERS_BY_ID_CACHE)).thenReturn(user.getId());

        when(mockEmailConfirmationMapper.toDto(any(EmailConfirmation.class))).thenReturn(expectedResult);

        // Run the test
        final EmailConfirmationDTO result = emailConfirmationServiceImplUnderTest.confirmEmail(0L, DEFAULT_EMAIL, "ipAddress");

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
