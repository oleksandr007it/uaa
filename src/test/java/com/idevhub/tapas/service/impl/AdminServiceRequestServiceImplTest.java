package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.Kopfg;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import com.idevhub.tapas.repository.StatehoodSubjectRepository;
import com.idevhub.tapas.repository.StatehoodSubjectRepresentRepository;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.service.UserService;
import com.idevhub.tapas.service.dto.*;
import com.idevhub.tapas.service.errors.UserAuthenticationNeedException;
import com.idevhub.tapas.service.mapper.AddressMapper;
import com.idevhub.tapas.service.mapper.AdminServiceRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


class AdminServiceRequestServiceImplTest {


    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private AdminServiceRequestMapper mockAdminServiceRequestMapper;
    @Mock
    private StatehoodSubjectRepresentRepository mockSubjectRepresentRepository;
    @Mock
    private StatehoodSubjectRepository mockSubjectRepository;
    @Mock
    private UserService mockUserService;
    @Mock
    private AddressMapper mockAddressMapper;

    private AdminServiceRequestServiceImpl adminServiceRequestServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        adminServiceRequestServiceImplUnderTest = spy(new AdminServiceRequestServiceImpl(mockUserRepository, mockAdminServiceRequestMapper, mockSubjectRepresentRepository, mockSubjectRepository, mockUserService, mockAddressMapper));
    }

    @Test
    void testGetFullRespDTO() {
        // Setup
        final AdminServiceRequestFullRespDTO expectedResult = new AdminServiceRequestFullRespDTO();
        expectedResult.setRNOKPP("RNOKPP");
        expectedResult.setPhone("phone");
        expectedResult.setEmail("email");
        expectedResult.setEmailApproved(false);
        expectedResult.setPassport("passport");
        expectedResult.setPassportGivenBy("passportGivenBy");
        expectedResult.setPassportGivenAt(Instant.ofEpochSecond(0L));
        expectedResult.setRegistrationAddress("registrationAddress");
        expectedResult.setPosition("position");
        expectedResult.setDeclarantId(0L);

        // Configure UserRepository.findOneById(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");

        final Optional<User> optionalUser = Optional.of(user);
        when(mockUserRepository.findOneById(anyLong())).thenReturn(optionalUser);

        // Configure StatehoodSubjectRepository.findById(...).
        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(Instant.ofEpochSecond(0L));
        statehoodSubject1.setUpdatedAt(Instant.ofEpochSecond(0L));
        final Optional<StatehoodSubject> statehoodSubject = Optional.of(statehoodSubject1);
        when(mockSubjectRepository.findById(anyLong())).thenReturn(statehoodSubject);

        // Configure AdminServiceRequestMapper.toDto(...).
        final AdminServiceRequestFullRespDTO adminServiceRequestFullRespDTO = new AdminServiceRequestFullRespDTO();
        adminServiceRequestFullRespDTO.setRNOKPP("RNOKPP");
        adminServiceRequestFullRespDTO.setPhone("phone");
        adminServiceRequestFullRespDTO.setEmail("email");
        adminServiceRequestFullRespDTO.setEmailApproved(false);
        adminServiceRequestFullRespDTO.setPassport("passport");
        adminServiceRequestFullRespDTO.setPassportGivenBy("passportGivenBy");
        adminServiceRequestFullRespDTO.setPassportGivenAt(Instant.ofEpochSecond(0L));
        adminServiceRequestFullRespDTO.setRegistrationAddress("registrationAddress");
        adminServiceRequestFullRespDTO.setPosition("position");
        adminServiceRequestFullRespDTO.setDeclarantId(0L);
        when(mockAdminServiceRequestMapper.toDto(any(User.class), any(StatehoodSubject.class))).thenReturn(adminServiceRequestFullRespDTO);

        // Run the test
        final AdminServiceRequestFullRespDTO result = adminServiceRequestServiceImplUnderTest.getFullRespDTO(0L, 0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetCurrentUserFullRespDTO() {
        // Setup
        final AdminServiceRequestFullRespDTO expectedResult = new AdminServiceRequestFullRespDTO();
        expectedResult.setRNOKPP("RNOKPP");
        expectedResult.setPhone("phone");
        expectedResult.setEmail("email");
        expectedResult.setEmailApproved(false);
        expectedResult.setPassport("passport");
        expectedResult.setPassportGivenBy("passportGivenBy");
        expectedResult.setPassportGivenAt(Instant.ofEpochSecond(0L));
        expectedResult.setRegistrationAddress("registrationAddress");
        expectedResult.setPosition("position");
        expectedResult.setDeclarantId(0L);

        // Configure UserService.getCurrentUser(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");


        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Configure StatehoodSubjectRepository.findById(...).
        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(Instant.ofEpochSecond(0L));
        statehoodSubject1.setUpdatedAt(Instant.ofEpochSecond(0L));
        final Optional<StatehoodSubject> statehoodSubject = Optional.of(statehoodSubject1);
        when(mockSubjectRepository.findById(anyLong())).thenReturn(statehoodSubject);

        // Configure AdminServiceRequestMapper.toDto(...).
        final AdminServiceRequestFullRespDTO adminServiceRequestFullRespDTO = new AdminServiceRequestFullRespDTO();
        adminServiceRequestFullRespDTO.setRNOKPP("RNOKPP");
        adminServiceRequestFullRespDTO.setPhone("phone");
        adminServiceRequestFullRespDTO.setEmail("email");
        adminServiceRequestFullRespDTO.setEmailApproved(false);
        adminServiceRequestFullRespDTO.setPassport("passport");
        adminServiceRequestFullRespDTO.setPassportGivenBy("passportGivenBy");
        adminServiceRequestFullRespDTO.setPassportGivenAt(Instant.ofEpochSecond(0L));
        adminServiceRequestFullRespDTO.setRegistrationAddress("registrationAddress");
        adminServiceRequestFullRespDTO.setPosition("position");
        adminServiceRequestFullRespDTO.setDeclarantId(0L);
        when(mockAdminServiceRequestMapper.toDto(any(User.class), any(StatehoodSubject.class))).thenReturn(adminServiceRequestFullRespDTO);

        // Run the test
        final AdminServiceRequestFullRespDTO result = adminServiceRequestServiceImplUnderTest.getCurrentUserFullRespDTO(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetASRBrokerageCreateData() {
        // Setup
        Kopfg kopfg = new Kopfg();
        kopfg.setCode(123);


        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setSubjectCode("123");
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(Instant.ofEpochSecond(0L));
        statehoodSubject1.setUpdatedAt(Instant.ofEpochSecond(0L));
        statehoodSubject1.setKopfg(kopfg);
        statehoodSubject1.setLegalAddress(new Address());

        User declarant = new User();
        declarant.setFullName("fulll name");
        declarant.setFirstName("first name");
        declarant.setMiddleName("middle name");
        declarant.setLastName("last name");
        declarant.setLogin("login");

        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject1);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setUpdatedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setDeletedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setDeclarant(declarant);
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);

        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent = Optional.of(statehoodSubjectRepresent1);


        when(mockSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(anyLong())).thenReturn(statehoodSubjectRepresent);

        doReturn(0L).when(adminServiceRequestServiceImplUnderTest).getCurrentDeclarantIdOrThrowEx();

        final AddressDTO addressDTO = new AddressDTO();
        addressDTO.setKoatuuCode("8000000000");
        addressDTO.setFullAddress("full address");

        when(mockAddressMapper.toDto(any(Address.class))).thenReturn(addressDTO);

        // Run the test
        final AdminServiceRequestBrokerageCreateDTO result = adminServiceRequestServiceImplUnderTest.getASRBrokerageCreateData();

        // Verify the results
        assertThat(result.getEdrpou()).isEqualTo("123");
    }

    @Test
    void testGetASRWarehouseCreateData() {
        // Setup
        final AdminServiceRequestWarehouseDTO expectedResult =
            new AdminServiceRequestWarehouseDTO(
                "edrpou",
                "subjectFullName",
                "subjectShortName",
                "headFullName",
                "registrationAddress",
                0L,
                "380631234567",
                "abc@gmail.com");

        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(...).

        Kopfg kopfg = new Kopfg();
        kopfg.setCode(123);


        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setSubjectCode("123");
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(Instant.ofEpochSecond(0L));
        statehoodSubject1.setUpdatedAt(Instant.ofEpochSecond(0L));
        statehoodSubject1.setKopfg(kopfg);
        statehoodSubject1.setLegalAddress(new Address());

        User declarant = new User();
        declarant.setFullName("fulll name");
        declarant.setFirstName("first name");
        declarant.setMiddleName("middle name");
        declarant.setLastName("last name");
        declarant.setLogin("login");

        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject1);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setUpdatedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setDeletedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setDeclarant(declarant);
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);

        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent = Optional.of(statehoodSubjectRepresent1);

        doReturn(0L).when(adminServiceRequestServiceImplUnderTest).getCurrentDeclarantIdOrThrowEx();


        final AddressDTO addressDTO = new AddressDTO();
        addressDTO.setKoatuuCode("8000000000");
        addressDTO.setFullAddress("full address");

        when(mockAddressMapper.toDto(any(Address.class))).thenReturn(addressDTO);

        when(mockSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(anyLong())).thenReturn(statehoodSubjectRepresent);

        // Run the test
        final AdminServiceRequestWarehouseDTO result = adminServiceRequestServiceImplUnderTest.getASRWarehouseCreateData();

        // Verify the results
        assertThat(result.getRegistrationAddress()).isEqualTo("full address");
    }

    @Test
    void testGetASRBrokerageUpdateData() {
        // Setup
        final Set<Long> subjectsIds = new HashSet<>(Arrays.asList(0L));


        Kopfg kopfg = new Kopfg();
        kopfg.setCode(123);


        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setSubjectCode("123");
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(Instant.ofEpochSecond(0L));
        statehoodSubject1.setUpdatedAt(Instant.ofEpochSecond(0L));
        statehoodSubject1.setKopfg(kopfg);
        statehoodSubject1.setLegalAddress(new Address());

        User declarant = new User();
        declarant.setFullName("fulll name");
        declarant.setFirstName("first name");
        declarant.setMiddleName("middle name");
        declarant.setLastName("last name");
        declarant.setLogin("login");

        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject1);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setUpdatedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setDeletedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setDeclarant(declarant);
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);


        doReturn(0L).when(adminServiceRequestServiceImplUnderTest).getCurrentDeclarantIdOrThrowEx();

        final AddressDTO addressDTO = new AddressDTO();
        addressDTO.setKoatuuCode("8000000000");
        addressDTO.setFullAddress("full address");

        when(mockAddressMapper.toDto(any(Address.class))).thenReturn(addressDTO);

        final List<StatehoodSubjectRepresent> statehoodSubjectRepresents = Arrays.asList(statehoodSubjectRepresent1);
        when(mockSubjectRepresentRepository.findAllByDeclarant_IdAndStatehoodSubject_IdIn(anyLong(), anyList())).thenReturn(statehoodSubjectRepresents);

        // Run the test
        final List<AdminServiceRequestBrokerageUpdateDTO> result = adminServiceRequestServiceImplUnderTest.getASRBrokerageUpdateData(subjectsIds);

        // Verify the results
        assertThat(result.size()).isGreaterThan(0);
    }

    @Test
    void testGetASRWarehouseUpdateData() {
        // Setup
        final Set<Long> subjectsIds = new HashSet<>(Arrays.asList(0L));


        Kopfg kopfg = new Kopfg();
        kopfg.setCode(123);


        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setSubjectCode("123");
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(Instant.ofEpochSecond(0L));
        statehoodSubject1.setUpdatedAt(Instant.ofEpochSecond(0L));
        statehoodSubject1.setKopfg(kopfg);
        statehoodSubject1.setLegalAddress(new Address());

        User declarant = new User();
        declarant.setFullName("fulll name");
        declarant.setFirstName("first name");
        declarant.setMiddleName("middle name");
        declarant.setLastName("last name");
        declarant.setLogin("login");

        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject1);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setUpdatedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setDeletedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setDeclarant(declarant);
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);

        doReturn(0L).when(adminServiceRequestServiceImplUnderTest).getCurrentDeclarantIdOrThrowEx();

        final List<StatehoodSubjectRepresent> statehoodSubjectRepresents = Arrays.asList(statehoodSubjectRepresent1);

        when(mockSubjectRepresentRepository.findAllByDeclarant_IdAndStatehoodSubject_IdIn(anyLong(), anyList())).thenReturn(statehoodSubjectRepresents);

        final AddressDTO addressDTO = new AddressDTO();
        addressDTO.setKoatuuCode("8000000000");
        addressDTO.setFullAddress("full address");

        when(mockAddressMapper.toDto(any(Address.class))).thenReturn(addressDTO);

        // Run the test
        final List<AdminServiceRequestWarehouseDTO> result = adminServiceRequestServiceImplUnderTest.getASRWarehouseUpdateData(subjectsIds);

        // Verify the results
        assertThat(result.size()).isGreaterThan(0);
    }

    @Test
    void getCurrentDeclarantIdOrThrowEx() {
        doReturn(0L).when(adminServiceRequestServiceImplUnderTest).getCurrentUserIdIfExists();
        Long result = adminServiceRequestServiceImplUnderTest.getCurrentDeclarantIdOrThrowEx();
        assertThat(result).isEqualTo(0L);
        doReturn(null).when(adminServiceRequestServiceImplUnderTest).getCurrentUserIdIfExists();
        assertThatThrownBy(() -> {
            adminServiceRequestServiceImplUnderTest.getCurrentDeclarantIdOrThrowEx();
        }).isInstanceOf(UserAuthenticationNeedException.class);

    }

    @Test
    void testGetDataToApproveRequest() {
        // Setup
        final DataToApproveRequestDTO expectedResult = new DataToApproveRequestDTO("edrpouStr", "rnokppStr");

        Kopfg kopfg = new Kopfg();
        kopfg.setCode(123);


        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setSubjectCode("edrpouStr");
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(Instant.ofEpochSecond(0L));
        statehoodSubject1.setUpdatedAt(Instant.ofEpochSecond(0L));
        statehoodSubject1.setKopfg(kopfg);
        statehoodSubject1.setLegalAddress(new Address());

        User declarant = new User();
        declarant.setFullName("fulll name");
        declarant.setFirstName("first name");
        declarant.setMiddleName("middle name");
        declarant.setLastName("last name");
        declarant.setLogin("login");
        declarant.setRnokpp("rnokppStr");

        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject1);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setUpdatedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setDeletedAt(Instant.ofEpochSecond(0L));
        statehoodSubjectRepresent1.setDeclarant(declarant);
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);

        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent = Optional.of(statehoodSubjectRepresent1);

        doReturn(0L).when(adminServiceRequestServiceImplUnderTest).getCurrentDeclarantIdOrThrowEx();

        when(mockSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(anyLong())).thenReturn(statehoodSubjectRepresent);

        // Run the test
        final DataToApproveRequestDTO result = adminServiceRequestServiceImplUnderTest.getDataToApproveRequest();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
