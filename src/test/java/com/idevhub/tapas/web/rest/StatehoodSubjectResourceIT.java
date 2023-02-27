package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.UaaApp;
import com.idevhub.tapas.domain.Kopfg;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.address.Country;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.repository.AddressRepository;
import com.idevhub.tapas.repository.KopfgRepository;
import com.idevhub.tapas.repository.StatehoodSubjectRepository;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.utils.TestSecurityUtils;
import com.idevhub.tapas.service.*;
import com.idevhub.tapas.service.criteria.StatehoodSubjectSpecificationHelper;
import com.idevhub.tapas.service.dto.*;
import com.idevhub.tapas.service.feign.CBackDictionariesClient;
import com.idevhub.tapas.service.feign.IntegrationEDRClient;
import com.idevhub.tapas.service.feign.RemoteCryptographyServiceClient;
import com.idevhub.tapas.service.impl.StatehoodSubjectServiceImpl;
import com.idevhub.tapas.service.mapper.AddressMapper;
import com.idevhub.tapas.service.mapper.StatehoodSubjectMapper;
import com.idevhub.tapas.web.rest.errors.ExceptionTranslator;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.idevhub.dto.SignerInfo;
import ua.idevhub.dto.VerifyByDataDTO;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.idevhub.tapas.util.EntityUtils.privilegeInGroup;
import static com.idevhub.tapas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApp.class)
//@AutoConfigureMockMvc
@WithMockUser(value = "admin", username = "admin", authorities = AuthoritiesConstants.ADMIN)
public class StatehoodSubjectResourceIT {
    private static final String EDRPOU = "12345678";
    private static final String RNOKPP = "1234567890";
    @Autowired
    private StatehoodSubjectRepository statehoodSubjectRepository;
    @Autowired
    private StatehoodSubjectMapper statehoodSubjectMapper;

    @MockBean(name = "com.idevhub.tapas.service.feign.CBackDictionariesClient")
    private CBackDictionariesClient mock_dictionariesClient;


    @Mock
    private EmailConfirmationService emailConfirmationService;
    @Mock
    private MailService mailService;
    @Mock
    private StatehoodSubjectSpecificationHelper specificationHelper;
    @Mock
    private StatehoodSubjectRepresentLookupService statehoodSubjectRepresentLookupService;
    @Autowired
    private AddressRepository addressRepository;
    @Mock
    private RemoteCryptographyServiceClient remoteCryptographyServiceClient;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private IntegrationEDRClient integrationEDRClient;

    private StatehoodSubjectService statehoodSubjectService;

    private StatehoodSubjectServiceImpl spystatehoodSubjectService;

    private MockMvc restStatehoodSubjectMockMvc;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private StatehoodSubjectRepresentService statehoodSubjectRepresentService;

    @Autowired
    private KopfgRepository kopfgRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private PrivilegeForSubjectFindService privilegeGroupsService;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        this.statehoodSubjectService = new StatehoodSubjectServiceImpl(
            statehoodSubjectRepository,
            statehoodSubjectMapper,
            emailConfirmationService,
            statehoodSubjectRepresentService,
            mailService,
            specificationHelper,
            statehoodSubjectRepresentLookupService,
            addressRepository,
            remoteCryptographyServiceClient,
            userRepository,
            integrationEDRClient,
            kopfgRepository,
            addressMapper,
            privilegeGroupsService);

        StatehoodSubjectResource statehoodSubjectResource =
            new StatehoodSubjectResource(this.statehoodSubjectService);
        this.spystatehoodSubjectService = (StatehoodSubjectServiceImpl) spy(this.statehoodSubjectService);
        this.restStatehoodSubjectMockMvc = MockMvcBuilders.standaloneSetup(statehoodSubjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();


    }


    private SubjectMainInfo createSubjectMainInfo() {
        SubjectMainInfo subjectMainInfo = new SubjectMainInfo();
        subjectMainInfo.setCode("1234567890");
        subjectMainInfo.setId(989867);
        subjectMainInfo.setName("GOGO");
        subjectMainInfo.setState(1);
        return subjectMainInfo;
    }

    @Test
    public void testGetActiveSubjectsByCodeOrPassportFromEdr() throws Exception {

        SubjectMainInfo subjectMainInfo = createSubjectMainInfo();

        Optional<SubjectMainInfo> optional = Optional.of(subjectMainInfo);
        Boolean bValue = false;
        when(integrationEDRClient.getActiveSubjectInfoByCode(anyString())).thenReturn(optional);

        // Get the statehoodSubject
        restStatehoodSubjectMockMvc.perform(get("/api/statehood-subjects/search/active/{code}", "1234567890"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.subjectCode").value("1234567890"))
            .andExpect(jsonPath("$.subjectName").value(subjectMainInfo.getName()))
            .andExpect(jsonPath("$.isInternal").value(bValue.booleanValue()))
            .andExpect(jsonPath("$.rawDataBase64").exists());

    }

    @Test
    @Transactional
    public void testGetActiveSubjectsByCodeOrPassportFromRepo() throws Exception {

        SubjectMainInfo subjectMainInfo = createSubjectMainInfo();
        Optional<SubjectMainInfo> optional = Optional.of(subjectMainInfo);
        StatehoodSubject statehoodSubject = createAndSaveStatehoodSubject(UserStatus.BLOCKED, EDRPOU);
        Boolean bValue = true;
        when(integrationEDRClient.getActiveSubjectInfoByCode(anyString())).thenReturn(optional);

        // Get the statehoodSubject
        restStatehoodSubjectMockMvc.perform(get("/api/statehood-subjects/search/active/{code}", EDRPOU))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.subjectCode").value(EDRPOU))
            .andExpect(jsonPath("$.subjectName").value(statehoodSubject.getSubjectName()))
            .andExpect(jsonPath("$.isInternal").value(bValue.booleanValue()))
            .andExpect(jsonPath("$.rawDataBase64").exists());
        Optional<StatehoodSubject> before = statehoodSubjectRepository.findById(statehoodSubject.getId());
        assertThat(before.get().getSubjectStatus()).isEqualTo(UserStatus.ACTIVE);

    }


    @Test
    public void testGetActiveSubjectsByCodeOrPassportExeptions() {
        when(integrationEDRClient.getActiveSubjectInfoByCode(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            statehoodSubjectService.getActiveSubjectsByCodeOrPassport("123451");
        }).isInstanceOf(RuntimeException.class);
    }


    @Test
    public void testGetActiveSubjectsByCodeOrPassportExeptions2() {
        when(integrationEDRClient.getActiveSubjectInfoByCode(anyString())).thenThrow(new RuntimeException("Error"));
        assertThatThrownBy(() -> {
            statehoodSubjectService.getActiveSubjectsByCodeOrPassport("123451");
        }).isInstanceOf(RuntimeException.class);

    }

    private static Address createEntity() {
        Address address = new Address();
        address.setCountryCode("804");
        address.setPostalCode("0291099");

        address.setHouseNumber("432113");
        address.setPavilionNumber("12345");
        address.setApartmentNumber("1");
        return address;
    }

    private StatehoodSubject createAndSaveStatehoodSubject(String userStatus, String edrpou) {
        // Add required entity
        Kopfg kopfg = KopfgResourceIntTest.createEntity(em);
        kopfgRepository.saveAndFlush(kopfg);

        // Add required entity
        Address legalAddress = createEntity();
        addressRepository.saveAndFlush(legalAddress);

        StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setSubjectStatus(userStatus);
        statehoodSubject.setSubjectCode(edrpou);
        statehoodSubject.setEori("");
        statehoodSubject.setActualAddress(legalAddress);
        statehoodSubject.setSubjectShortName("Name");
        statehoodSubject.setKopfg(kopfg);
        statehoodSubject.setEmailApproved(true);
        statehoodSubject.setEmail("email");
        statehoodSubject.setTelNumber("342313");
        statehoodSubject.setActualSameAsLegalAddress(true);
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.FULL_CONFIRMED);
        statehoodSubject.setSubjectName("Name");
        statehoodSubject.setLegalAddress(legalAddress);
        statehoodSubject.setCreatedAt(Instant.now());
        statehoodSubject.setCreatedBy("admin");
        statehoodSubjectRepository.save(statehoodSubject);
        return statehoodSubject;
    }

    private SubjectInfoForAdd createSubjectInfoForAdd(String edrpou) {
        SubjectInfoForAdd subjectInfoForAdd = new SubjectInfoForAdd();
        subjectInfoForAdd.setEmail("email");
        subjectInfoForAdd.setSubjectCode(edrpou);
        subjectInfoForAdd.setTelNumber("teNumber");
        subjectInfoForAdd.setRawDataBase64("sss");
        subjectInfoForAdd.setSignBase64("sign");
        return subjectInfoForAdd;
    }

    private SignerInfo createSignerInfo(String edrpou, String rnokppo) {
        SignerInfo signerInfo = new SignerInfo();
        signerInfo.setOkpo_(edrpou);
        signerInfo.setGrfl_(rnokppo);
        signerInfo.setCommon_name_("Петренко павло Павло:%вич");
        return signerInfo;
    }

    private User createEntityUser(String rnokpp, String edorpou) {
        User user = new User();
        user.setLogin("admin" + RandomStringUtils.randomAlphabetic(5));
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail(RandomStringUtils.randomAlphabetic(5) + "johndoe@localhost");
        user.setFirstName("Петренко");
        user.setLastName("Павло");
        user.setLangKey("Павлович");
        user.setFullName("Петренко Павло Павлович");
        user.setEdrpouCode(edorpou);
        user.setRnokpp(rnokpp);
        userRepository.saveAndFlush(user);
        return user;
    }


    @Test
    @DisplayName("create StatehoodSubject and Represeter from  repository")
    @Transactional
    public void createStatehoodSubjectAndAddRepreseter() throws Exception {
        TestSecurityUtils.setSecurityContext(123L);
        var localGroup = privilegeInGroup(em, "V1_1_2_test", "GR_V1_LEGAL_ENTITY");
        em.persist(localGroup);
        StatehoodSubject expect = createAndSaveStatehoodSubject(UserStatus.ACTIVE, "34567845");
        SubjectMainInfo subjectMainInfo = createSubjectMainInfo();
        Optional<SubjectMainInfo> optional = Optional.of(subjectMainInfo);
        when(integrationEDRClient.getActiveSubjectInfoByCode(anyString())).thenReturn(optional);
        SubjectInfoForAdd subjectInfoForAdd = createSubjectInfoForAdd("34567845");
        SignerInfo signerInfo = createSignerInfo("34567845", RNOKPP);
        when(remoteCryptographyServiceClient.verifyByData(any(VerifyByDataDTO.class))).thenReturn(signerInfo);
        User user = createEntityUser(RNOKPP, "34567845");
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setCode("34567845");
        subjectDTO.setName("Петренко Павло ПавловИЧ");
        subjectDTO.setOlfName("930");
        when(spystatehoodSubjectService.getCurrentUserId()).thenReturn(user.getId());
        when(integrationEDRClient.getSubjectDTODetails(any(BigInteger.class))).thenReturn(Optional.of(subjectDTO));
        Long result = spystatehoodSubjectService.createStatehoodSubjectAndAddRepreseter(subjectInfoForAdd);
        assertEquals(expect.getId(), result);
        em.remove(localGroup);
    }

    private List<Country> stubCountries() {
        Country country = new Country();
        country.setNumberCode("804");
        country.setAlpha2("UA");
        country.setAlpha3("UKR");
        country.setNameEn("Ukraine");
        country.setNameUk("Україна");
        country.setNameUkShort("Україна");
        return Arrays.asList(country);
    }


    @Test
    @DisplayName("Update StatehoodSubject and Represeter from  repository")
    @Transactional
    public void updateStatehoodSubject() throws Exception {
        TestSecurityUtils.setSecurityContext(123L);
        StatehoodSubject expect = createAndSaveStatehoodSubject(UserStatus.ACTIVE, "34567845");

        val addressDTO = new AddressDTO();
        addressDTO.setId(expect.getLegalAddress().getId());


        StatehoodSubjectDTO inputDTO = new StatehoodSubjectDTO();
        inputDTO.setId(expect.getId());
        inputDTO.setSubjectStatus("ACTIVE");
        inputDTO.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        inputDTO.setSubjectCode("");
        inputDTO.setSubjectName("");
        inputDTO.setSubjectShortName("");
        inputDTO.setTelNumber("");
        inputDTO.setEmail("mail@mail.ua");
        inputDTO.setIsEmailApproved(false);
        inputDTO.setEori("ririri");
        inputDTO.setActualSameAsLegalAddress(false);
        inputDTO.setKopfgId(expect.getKopfg().getId());
        inputDTO.setKopfgCode(expect.getKopfg().getCode().toString());
        inputDTO.setActualSameAsLegalAddress(false);
        inputDTO.setLegalAddress(addressDTO);
        inputDTO.setActualAddress(addressDTO);

        when(mock_dictionariesClient.getAllCountries()).thenReturn(stubCountries());
        when(mock_dictionariesClient.getCountryByNumberCode(any())).thenReturn(stubCountries());

        StatehoodSubjectDTO result = spystatehoodSubjectService.update(inputDTO);
        assertEquals(result.getEmail(), "mail@mail.ua");
        assertEquals(result.getEori(), "ririri");

    }

    @Test
    @DisplayName("create StatehoodSubject and Represeter and check Heads List")
    @Transactional
    public void createStatehoodSubjectAndAddRepreseterDifferentName() throws Exception {
        TestSecurityUtils.setSecurityContext(123L);
        var localGroup = privilegeInGroup(em, "V1_1_2_test", "GR_V1_LEGAL_ENTITY");
        em.persist(localGroup);
        StatehoodSubject expect = createAndSaveStatehoodSubject(UserStatus.ACTIVE, "45464748");
        SubjectMainInfo subjectMainInfo = createSubjectMainInfo();
        Optional<SubjectMainInfo> optional = Optional.of(subjectMainInfo);
        when(integrationEDRClient.getActiveSubjectInfoByCode(anyString())).thenReturn(optional);
        SubjectInfoForAdd subjectInfoForAdd = createSubjectInfoForAdd("45464748");
        SignerInfo signerInfo = createSignerInfo("45464748", RNOKPP);
        when(remoteCryptographyServiceClient.verifyByData(any(VerifyByDataDTO.class))).thenReturn(signerInfo);
        List<String> list = new ArrayList<>();
        list.add("=Петренко-Павло Павло'вИЧ");
        list.add("Иваненко  Микола");
        User user = createEntityUser(RNOKPP, "45464748");
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setCode("45464748");
        subjectDTO.setHeadFullName(list);
        subjectDTO.setName("Иваненко  Микола");
        subjectDTO.setOlfName("930");
        when(spystatehoodSubjectService.getCurrentUserId()).thenReturn(user.getId());
        when(integrationEDRClient.getSubjectDTODetails(any(BigInteger.class))).thenReturn(Optional.of(subjectDTO));
        Long result = spystatehoodSubjectService.createStatehoodSubjectAndAddRepreseter(subjectInfoForAdd);
        assertEquals(expect.getId(), result);
        Optional<StatehoodSubjectRepresent> represent = statehoodSubjectRepresentService.findByDeclarantIdAndStatehoodSubjectId(user.getId(), result);
        assertTrue(represent.isPresent());
        assertThat(represent.get().getPrivilegeGroups().size()).isGreaterThan(0);
        em.remove(localGroup);
    }


    @Test
    @DisplayName("create StatehoodSubject and Represeter and check Null Edrpou")
    @Transactional
    public void createStatehoodSubjectAndAddRepreseterEdrpou2() throws Exception {
        TestSecurityUtils.setSecurityContext(123L);
        var localGroup = privilegeInGroup(em, "V1_1_2_test", "GR_V1_LEGAL_ENTITY");
        em.persist(localGroup);
        StatehoodSubject expect = createAndSaveStatehoodSubject(UserStatus.ACTIVE, "45464777");
        SubjectMainInfo subjectMainInfo = createSubjectMainInfo();
        Optional<SubjectMainInfo> optional = Optional.of(subjectMainInfo);
        when(integrationEDRClient.getActiveSubjectInfoByCode(anyString())).thenReturn(optional);
        SubjectInfoForAdd subjectInfoForAdd = createSubjectInfoForAdd("12345678");

        SignerInfo signerInfo = createSignerInfo(null, "12345678");
        when(remoteCryptographyServiceClient.verifyByData(any(VerifyByDataDTO.class))).thenReturn(signerInfo);
        List<String> list = new ArrayList<>();
        list.add("=Петренко-Павло Павло'вИЧ");
        list.add("Иваненко  Микола");
        User user = createEntityUser("12345678", "45464777");
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setCode("45464777");
        subjectDTO.setHeadFullName(list);
        subjectDTO.setName("Иваненко  Микола");
        subjectDTO.setOlfName("930");

        AddressFromEDR addressDTO = new AddressFromEDR();
        addressDTO.setCountry("address.getCountry().getValue()");
        addressDTO.setZip("123131");
        addressDTO.setFullAddress("address.getAddress().getValue()");

        AddressParts addressPartsDTO = new AddressParts();
        addressPartsDTO.setAtu("addressParts.getAtu().getValue()");
        addressPartsDTO.setBuilding("312343");
        addressPartsDTO.setBuildingType("addressParts.getBuildingType().getValue()");
        addressPartsDTO.setHouse("123141");
        addressPartsDTO.setHouseType("addressParts.getHouseType().getValue()");
        addressPartsDTO.setNum("addressParts.getNum().getValue()");
        addressPartsDTO.setNumType("addressParts.getNumType().getValue()");
        addressPartsDTO.setStreet("addressParts.getStreet().getValue()");


        addressDTO.setParts(addressPartsDTO);
        subjectDTO.setAddressFromEDR(addressDTO);

        when(spystatehoodSubjectService.getCurrentUserId()).thenReturn(user.getId());
        when(integrationEDRClient.getSubjectDTODetails(any(BigInteger.class))).thenReturn(Optional.of(subjectDTO));
        Long result = spystatehoodSubjectService.createStatehoodSubjectAndAddRepreseter(subjectInfoForAdd);

        assertNotEquals(expect.getId(), result);
        em.remove(localGroup);
    }

    @Test
    @Transactional
    @DisplayName("create StatehoodSubject and Represeter and check Null Rnokpo  Exeption")
    public void createStatehoodSubjectAndAddRepreseterRnokpoExeption() throws Exception {

        createAndSaveStatehoodSubject(UserStatus.ACTIVE, "88888888");
        SubjectMainInfo subjectMainInfo = createSubjectMainInfo();
        Optional<SubjectMainInfo> optional = Optional.of(subjectMainInfo);
        when(integrationEDRClient.getActiveSubjectInfoByCode(anyString())).thenReturn(optional);
        SubjectInfoForAdd subjectInfoForAdd = createSubjectInfoForAdd("88888888");
        SignerInfo signerInfo = createSignerInfo(null, "1234567899");
        when(remoteCryptographyServiceClient.verifyByData(any(VerifyByDataDTO.class))).thenReturn(signerInfo);
        List<String> list = new ArrayList<>();
        list.add("=Петренко-Павло Павло'вИЧ");
        list.add("Иваненко  Микола");
        User user = createEntityUser("1234567899", "88888888");
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setCode("88888888");
        subjectDTO.setHeadFullName(list);
        subjectDTO.setName("Иваненко  Микола");
        subjectDTO.setOlfName("930");
        when(spystatehoodSubjectService.getCurrentUserId()).thenReturn(user.getId());
        when(integrationEDRClient.getSubjectDTODetails(any(BigInteger.class))).thenReturn(Optional.of(subjectDTO));

        assertThatThrownBy(() -> {
            spystatehoodSubjectService.createStatehoodSubjectAndAddRepreseter(subjectInfoForAdd);
        }).isInstanceOf(RuntimeException.class);

    }

    @Test
    @Transactional
    @DisplayName("create StatehoodSubject and Represeter and check wrong Edrpou Exeption")
    public void createStatehoodSubjectAndAddRepreseterEdrpouExeption() throws Exception {

        createAndSaveStatehoodSubject(UserStatus.ACTIVE, "88888887");
        SubjectMainInfo subjectMainInfo = createSubjectMainInfo();
        Optional<SubjectMainInfo> optional = Optional.of(subjectMainInfo);
        when(integrationEDRClient.getActiveSubjectInfoByCode(anyString())).thenReturn(optional);
        SubjectInfoForAdd subjectInfoForAdd = createSubjectInfoForAdd("88888887");
        SignerInfo signerInfo = createSignerInfo("333333", "12345678");
        when(remoteCryptographyServiceClient.verifyByData(any(VerifyByDataDTO.class))).thenReturn(signerInfo);
        List<String> list = new ArrayList<>();
        list.add("=Петренко-Павло Павло'вИЧ");
        list.add("Иваненко  Микола");
        User user = createEntityUser("12345678", "88888887");
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setCode("88888887");
        subjectDTO.setHeadFullName(list);
        subjectDTO.setName("Иваненко  Микола");
        subjectDTO.setOlfName("930");
        when(spystatehoodSubjectService.getCurrentUserId()).thenReturn(user.getId());
        when(integrationEDRClient.getSubjectDTODetails(any(BigInteger.class))).thenReturn(Optional.of(subjectDTO));

        assertThatThrownBy(() -> {
            spystatehoodSubjectService.createStatehoodSubjectAndAddRepreseter(subjectInfoForAdd);
        }).isInstanceOf(RuntimeException.class);

    }


    @Test
    @Transactional
    @DisplayName("create StatehoodSubject and Represeter from  EDR")
    public void createStatehoodSubjectAndAddRepreseterFromEdr() throws Exception {
        TestSecurityUtils.setSecurityContext(123L);
        var localGroup = privilegeInGroup(em, "V1_1_2_test", "GR_V1_LEGAL_ENTITY");
        em.persist(localGroup);
        SubjectMainInfo subjectMainInfo = createSubjectMainInfo();
        Optional<SubjectMainInfo> optional = Optional.of(subjectMainInfo);
        when(integrationEDRClient.getActiveSubjectInfoByCode(anyString())).thenReturn(optional);
        SubjectInfoForAdd subjectInfoForAdd = createSubjectInfoForAdd("89088888");
        SignerInfo signerInfo = createSignerInfo("89088888", RNOKPP);
        when(remoteCryptographyServiceClient.verifyByData(any(VerifyByDataDTO.class))).thenReturn(signerInfo);
        User user = createEntityUser(RNOKPP, "89088888");
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setCode("89088888");
        subjectDTO.setName("Петренко Павло ПавловИЧ");
        subjectDTO.setOlfName("930");
        AddressFromEDR addressFromEDR = new AddressFromEDR();
        addressFromEDR.setZip("02092");
        AddressParts addressParts = new AddressParts();
        addressParts.setHouse("223");
        addressParts.setBuilding("GHKL");
        addressFromEDR.setParts(addressParts);
        addressFromEDR.setFullAddress("улица  Пушкина  дом Калатушкина 19");
        subjectDTO.setAddressFromEDR(addressFromEDR);

        when(spystatehoodSubjectService.getCurrentUserId()).thenReturn(user.getId());
        when(integrationEDRClient.getSubjectDTODetails(any(BigInteger.class))).thenReturn(Optional.of(subjectDTO));
        Long id = spystatehoodSubjectService.createStatehoodSubjectAndAddRepreseter(subjectInfoForAdd);
        StatehoodSubject rezult = statehoodSubjectRepository.findById(id).get();
        assertEquals(rezult.getEmail(), subjectInfoForAdd.getEmail());
        em.remove(localGroup);

    }
}

