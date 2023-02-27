package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.CEADepartment;
import com.idevhub.tapas.domain.CEAEmployee;
import com.idevhub.tapas.domain.CentralExecutiveAuthority;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.constants.UserType;
import com.idevhub.tapas.domain.enumeration.CEADepartmentStatus;
import com.idevhub.tapas.domain.enumeration.CentralExecutiveAuthorityStatus;
import com.idevhub.tapas.domain.enumeration.PositionType;
import com.idevhub.tapas.domain.enumeration.PrivilegeCode;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.WithMockCustomUser;
import com.idevhub.tapas.service.CentralExecutiveAuthorityService;
import com.idevhub.tapas.service.UserService;
import com.idevhub.tapas.service.criteria.CEAEmployeeCriteria;
import com.idevhub.tapas.service.criteria.CEAEmployeeSpecificationHelper;
import com.idevhub.tapas.service.dto.CEAEmployeeContextDTO;
import com.idevhub.tapas.service.dto.CEAEmployeeDTO;
import com.idevhub.tapas.service.dto.CeaEmployeeMainInfoDTO;
import com.idevhub.tapas.service.mapper.UserMapper;
import io.github.jhipster.service.filter.StringFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
@SpringBootTest
class CEAEmployeeServiceImplTest {

    @Autowired
    private CEAEmployeeSpecificationHelper mockSpecificationHelper;
    @Mock
    private UserRepository mockRepository;
    @Mock
    private UserService mockUserService;
    @Mock
    private CentralExecutiveAuthorityService mockCeaService;
    @Mock
    private UserMapper mockMapper;

    private CEAEmployeeServiceImpl ceaEmployeeServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        openMocks(this);
        ceaEmployeeServiceImplUnderTest = new CEAEmployeeServiceImpl(mockSpecificationHelper, mockRepository, mockUserService, mockCeaService, mockMapper);
    }

    @Test
    @WithMockCustomUser(userRole = AuthoritiesConstants.CEA_EMPLOYEE)
    void testSaveEmployee() {
        // Setup
        final CEAEmployeeDTO toSave = new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "code", 0L, "departmentFullName", "position", PositionType.HEAD);
        final CEAEmployeeDTO expectedResult = new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD);

        // Configure UserService.getCurrentUser(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setEdrpouCode("code");
        user.setUserType(UserType.CEA_EMPLOYEE);
      //  user.setCentralExecutiveAuthority()
        user.setMiddleName("middleName");
        user.setFullName("fullName");
        user.setStatus(UserStatus.ACTIVE);
        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Configure UserMapper.toCeaEmployeeContextDto(...).
        final CEAEmployeeContextDTO ceaEmployeeContextDTO = new CEAEmployeeContextDTO();
        ceaEmployeeContextDTO.setUserId(0L);
        ceaEmployeeContextDTO.setFirstName("firstName");
        ceaEmployeeContextDTO.setLastName("lastName");
        ceaEmployeeContextDTO.setMiddleName("middleName");
        ceaEmployeeContextDTO.setFullName("fullName");
        ceaEmployeeContextDTO.setCeaCode("code");
        ceaEmployeeContextDTO.setCeaDepartmentId(0L);
        ceaEmployeeContextDTO.setPosition("position");
        ceaEmployeeContextDTO.setPositionType(PositionType.HEAD);
        ceaEmployeeContextDTO.setPrivilegeCodes(Set.of(PrivilegeCode.V1_1_1));
        when(mockMapper.toCeaEmployeeContextDto(any(User.class))).thenReturn(ceaEmployeeContextDTO);

        // Configure CentralExecutiveAuthorityService.getByCode(...).
        final CentralExecutiveAuthority centralExecutiveAuthority = new CentralExecutiveAuthority();
        centralExecutiveAuthority.setId(0L);
        centralExecutiveAuthority.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority.centralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthority.deletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthority.setCode("code");

        centralExecutiveAuthority.setFullNameUkr("fullNameUkr");
        centralExecutiveAuthority.fullNameUkr("fullNameUkr");
        centralExecutiveAuthority.setFullNameEng("fullNameEng");
        when(mockCeaService.getByCode(anyString())).thenReturn(centralExecutiveAuthority);

        // Configure CentralExecutiveAuthorityService.getDepartment(...).
        final CEADepartment ceaDepartment = new CEADepartment();
        ceaDepartment.setId(0L);
        ceaDepartment.setCeaDepartmentStatus(CEADepartmentStatus.ACTIVE);
        ceaDepartment.ceaDepartmentStatus(CEADepartmentStatus.ACTIVE);
        ceaDepartment.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        ceaDepartment.deletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        ceaDepartment.setDeletedBy("deletedBy");
        ceaDepartment.deletedBy("deletedBy");
        ceaDepartment.setFullNameUkr("fullNameUkr");
        ceaDepartment.fullNameUkr("fullNameUkr");
        ceaDepartment.setFullNameEng("fullNameEng");
        when(mockCeaService.getDepartment(anyString(), anyLong())).thenReturn(ceaDepartment);

        // Configure UserMapper.toCeaEmployeeDto(...).
        final CEAEmployeeDTO ceaEmployeeDTO = new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD);
        when(mockMapper.toCeaEmployeeDto(any(User.class))).thenReturn(ceaEmployeeDTO);

        // Configure UserService.saveCeaEmployeeAccount(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setPassword("password");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        user1.setUserType(UserType.CEA_EMPLOYEE);
        user1.setMiddleName("middleName");
        user1.setFullName("fullName");
        user1.setStatus(UserStatus.ACTIVE);
        when(mockUserService.saveCeaEmployeeAccount(any(CEAEmployee.class))).thenReturn(user1);

        // Run the test
        final CEAEmployeeDTO result = ceaEmployeeServiceImplUnderTest.saveEmployee(toSave);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    @WithMockCustomUser(userRole = AuthoritiesConstants.CEA_EMPLOYEE)
    void testDeleteEmployee() {
        // Setup

        // Configure UserService.getCurrentUser(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setUserType("userType");
        user.setMiddleName("middleName");
        user.setFullName("fullName");
        user.setStatus("status");
        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Configure UserMapper.toCeaEmployeeContextDto(...).
        final CEAEmployeeContextDTO ceaEmployeeContextDTO = new CEAEmployeeContextDTO();
        ceaEmployeeContextDTO.setUserId(0L);
        ceaEmployeeContextDTO.setFirstName("firstName");
        ceaEmployeeContextDTO.setLastName("lastName");
        ceaEmployeeContextDTO.setMiddleName("middleName");
        ceaEmployeeContextDTO.setFullName("fullName");
        ceaEmployeeContextDTO.setCeaCode("ceaCode");
        ceaEmployeeContextDTO.setCeaDepartmentId(0L);
        ceaEmployeeContextDTO.setPosition("position");
        ceaEmployeeContextDTO.setPositionType(PositionType.HEAD);
        ceaEmployeeContextDTO.setPrivilegeCodes(Set.of(PrivilegeCode.V1_1_1));
        when(mockMapper.toCeaEmployeeContextDto(any(User.class))).thenReturn(ceaEmployeeContextDTO);

        // Run the test
        ceaEmployeeServiceImplUnderTest.deleteEmployee(0L);

        // Verify the results
        verify(mockUserService).softDeleteCeaEmployeeAccount(0L, "ceaCode");
    }



    @Test
    @WithMockCustomUser(userRole = AuthoritiesConstants.CEA_EMPLOYEE)
    void testFindAll() {
        // Setup
        final CEAEmployeeCriteria criteria = new CEAEmployeeCriteria(new StringFilter(), new StringFilter(), new StringFilter(), new StringFilter(), new StringFilter(), new CEAEmployeeCriteria.PositionTypeFilter());

        // Configure UserService.getCurrentUser(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setUserType("userType");
        user.setMiddleName("middleName");
        user.setFullName("fullName");
        user.setStatus("status");
        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Configure UserMapper.toCeaEmployeeContextDto(...).
        final CEAEmployeeContextDTO ceaEmployeeContextDTO = new CEAEmployeeContextDTO();
        ceaEmployeeContextDTO.setUserId(0L);
        ceaEmployeeContextDTO.setFirstName("firstName");
        ceaEmployeeContextDTO.setLastName("lastName");
        ceaEmployeeContextDTO.setMiddleName("middleName");
        ceaEmployeeContextDTO.setFullName("fullName");
        ceaEmployeeContextDTO.setCeaCode("ceaCode");
        ceaEmployeeContextDTO.setCeaDepartmentId(0L);
        ceaEmployeeContextDTO.setPosition("position");
        ceaEmployeeContextDTO.setPositionType(PositionType.HEAD);
        ceaEmployeeContextDTO.setPrivilegeCodes(Set.of(PrivilegeCode.V1_1_1));
        when(mockMapper.toCeaEmployeeContextDto(any(User.class))).thenReturn(ceaEmployeeContextDTO);


        // Configure UserRepository.findAll(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setPassword("password");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        user1.setUserType("userType");
        user1.setMiddleName("middleName");
        user1.setFullName("fullName");
        user1.setStatus("status");
        final Page<User> users = new PageImpl<>(List.of(user1));
        when(mockRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(users);

        // Configure UserMapper.toCeaEmployeeDto(...).
        final CEAEmployeeDTO ceaEmployeeDTO = new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD);
        when(mockMapper.toCeaEmployeeDto(any(User.class))).thenReturn(ceaEmployeeDTO);

        // Run the test
        final Page<CEAEmployeeDTO> result = ceaEmployeeServiceImplUnderTest.findAll(PageRequest.of(0, 1), criteria);

        // Verify the results
    }



    @Test
    @WithMockCustomUser(userRole = AuthoritiesConstants.CEA_EMPLOYEE)
    void testGetById() {
        // Setup
        final CEAEmployeeDTO expectedResult = new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD);

        // Configure UserService.getCurrentUser(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setUserType("userType");
        user.setMiddleName("middleName");
        user.setFullName("fullName");
        user.setStatus("status");
        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Configure UserMapper.toCeaEmployeeContextDto(...).
        final CEAEmployeeContextDTO ceaEmployeeContextDTO = new CEAEmployeeContextDTO();
        ceaEmployeeContextDTO.setUserId(0L);
        ceaEmployeeContextDTO.setFirstName("firstName");
        ceaEmployeeContextDTO.setLastName("lastName");
        ceaEmployeeContextDTO.setMiddleName("middleName");
        ceaEmployeeContextDTO.setFullName("fullName");
        ceaEmployeeContextDTO.setCeaCode("ceaCode");
        ceaEmployeeContextDTO.setCeaDepartmentId(0L);
        ceaEmployeeContextDTO.setPosition("position");
        ceaEmployeeContextDTO.setPositionType(PositionType.HEAD);
        ceaEmployeeContextDTO.setPrivilegeCodes(Set.of(PrivilegeCode.V1_1_1));
        when(mockMapper.toCeaEmployeeContextDto(any(User.class))).thenReturn(ceaEmployeeContextDTO);

        // Configure UserRepository.findCeaEmployee(...).
        final User user2 = new User();
        user2.setId(0L);
        user2.setLogin("login");
        user2.setPassword("password");
        user2.setFirstName("firstName");
        user2.setLastName("lastName");
        user2.setEmail("email");
        user2.setUserType("userType");
        user2.setMiddleName("middleName");
        user2.setFullName("fullName");
        user2.setStatus("status");
        final Optional<User> user1 = Optional.of(user2);
        when(mockRepository.findCeaEmployee(0L, "ceaCode")).thenReturn(user1);

        // Configure UserMapper.toCeaEmployeeDto(...).
        final CEAEmployeeDTO ceaEmployeeDTO = new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD);
        when(mockMapper.toCeaEmployeeDto(any(User.class))).thenReturn(ceaEmployeeDTO);

        // Run the test
        final CEAEmployeeDTO result = ceaEmployeeServiceImplUnderTest.getById(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }



    @Test
    @WithMockCustomUser(userRole = AuthoritiesConstants.CEA_EMPLOYEE)
    void testGetDepartmentHead() {
        // Setup
        final CEAEmployeeContextDTO expectedResult = new CEAEmployeeContextDTO();
        expectedResult.setUserId(0L);
        expectedResult.setFirstName("firstName");
        expectedResult.setLastName("lastName");
        expectedResult.setMiddleName("middleName");
        expectedResult.setFullName("fullName");
        expectedResult.setCeaCode("ceaCode");
        expectedResult.setCeaDepartmentId(0L);
        expectedResult.setPosition("position");
        expectedResult.setPositionType(PositionType.HEAD);
        expectedResult.setPrivilegeCodes(Set.of(PrivilegeCode.V1_1_1));

        // Configure UserService.getCurrentUser(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setUserType("userType");
        user.setMiddleName("middleName");
        user.setFullName("fullName");
        user.setStatus("status");
        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Configure UserMapper.toCeaEmployeeContextDto(...).
        final CEAEmployeeContextDTO ceaEmployeeContextDTO = new CEAEmployeeContextDTO();
        ceaEmployeeContextDTO.setUserId(0L);
        ceaEmployeeContextDTO.setFirstName("firstName");
        ceaEmployeeContextDTO.setLastName("lastName");
        ceaEmployeeContextDTO.setMiddleName("middleName");
        ceaEmployeeContextDTO.setFullName("fullName");
        ceaEmployeeContextDTO.setCeaCode("ceaCode");
        ceaEmployeeContextDTO.setCeaDepartmentId(0L);
        ceaEmployeeContextDTO.setPosition("position");
        ceaEmployeeContextDTO.setPositionType(PositionType.HEAD);
        ceaEmployeeContextDTO.setPrivilegeCodes(Set.of(PrivilegeCode.V1_1_1));
        when(mockMapper.toCeaEmployeeContextDto(any(User.class))).thenReturn(ceaEmployeeContextDTO);

        // Configure UserRepository.findAllCeaEmployeeByDepartmentId(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setPassword("password");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        user1.setUserType(UserType.CEA_EMPLOYEE);
        user1.setPositionType(PositionType.HEAD);
        user1.setMiddleName("middleName");
        user1.setFullName("fullName");
        user1.setStatus("status");
        final List<User> users = List.of(user1);
        when(mockRepository.findAllCeaEmployeeByDepartmentId(anyLong(), anyString())).thenReturn(users);

        // Run the test
        final CEAEmployeeContextDTO result = ceaEmployeeServiceImplUnderTest.getDepartmentHead(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    @WithMockCustomUser(userRole = AuthoritiesConstants.CEA_EMPLOYEE)
    void testGetCurrentContext() {
        // Setup
        final CEAEmployeeContextDTO expectedResult = new CEAEmployeeContextDTO();
        expectedResult.setUserId(0L);
        expectedResult.setFirstName("firstName");
        expectedResult.setLastName("lastName");
        expectedResult.setMiddleName("middleName");
        expectedResult.setFullName("fullName");
        expectedResult.setCeaCode("ceaCode");
        expectedResult.setCeaDepartmentId(0L);
        expectedResult.setPosition("position");
        expectedResult.setPositionType(PositionType.HEAD);
        expectedResult.setPrivilegeCodes(Set.of(PrivilegeCode.V1_1_1));

        // Configure UserService.getCurrentUser(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setUserType("userType");
        user.setMiddleName("middleName");
        user.setFullName("fullName");
        user.setStatus("status");
        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Configure UserMapper.toCeaEmployeeContextDto(...).
        final CEAEmployeeContextDTO ceaEmployeeContextDTO = new CEAEmployeeContextDTO();
        ceaEmployeeContextDTO.setUserId(0L);
        ceaEmployeeContextDTO.setFirstName("firstName");
        ceaEmployeeContextDTO.setLastName("lastName");
        ceaEmployeeContextDTO.setMiddleName("middleName");
        ceaEmployeeContextDTO.setFullName("fullName");
        ceaEmployeeContextDTO.setCeaCode("ceaCode");
        ceaEmployeeContextDTO.setCeaDepartmentId(0L);
        ceaEmployeeContextDTO.setPosition("position");
        ceaEmployeeContextDTO.setPositionType(PositionType.HEAD);
        ceaEmployeeContextDTO.setPrivilegeCodes(Set.of(PrivilegeCode.V1_1_1));
        when(mockMapper.toCeaEmployeeContextDto(any(User.class))).thenReturn(ceaEmployeeContextDTO);

        // Run the test
        final CEAEmployeeContextDTO result = ceaEmployeeServiceImplUnderTest.getCurrentContext();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    @WithMockCustomUser(userRole = AuthoritiesConstants.CEA_EMPLOYEE)
    void testGetCeaUserMainInfo() {
        // Setup
        final CeaEmployeeMainInfoDTO expectedResult = new CeaEmployeeMainInfoDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "orgUnit", "position");

        // Configure UserService.getCurrentUser(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setUserType("userType");
        user.setMiddleName("middleName");
        user.setFullName("fullName");
        user.setStatus("status");
        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Configure UserMapper.toCeaEmployeeMainInfoDto(...).
        final CeaEmployeeMainInfoDTO ceaEmployeeMainInfoDTO = new CeaEmployeeMainInfoDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "orgUnit", "position");
        when(mockMapper.toCeaEmployeeMainInfoDto(any(User.class))).thenReturn(ceaEmployeeMainInfoDTO);

        // Run the test
        final CeaEmployeeMainInfoDTO result = ceaEmployeeServiceImplUnderTest.getCeaUserMainInfo();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    @WithMockCustomUser(userRole = AuthoritiesConstants.CEA_EMPLOYEE)
    void testGetCeaEmployeeContext() {
        // Setup
        final CEAEmployeeContextDTO expectedResult = new CEAEmployeeContextDTO();
        expectedResult.setUserId(0L);
        expectedResult.setFirstName("firstName");
        expectedResult.setLastName("lastName");
        expectedResult.setMiddleName("middleName");
        expectedResult.setFullName("fullName");
        expectedResult.setCeaCode("ceaCode");
        expectedResult.setCeaDepartmentId(0L);
        expectedResult.setPosition("position");
        expectedResult.setPositionType(PositionType.HEAD);
        expectedResult.setPrivilegeCodes(Set.of(PrivilegeCode.V1_1_1));

        // Configure UserRepository.findCeaEmployee(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setPassword("password");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        user1.setUserType("userType");
        user1.setMiddleName("middleName");
        user1.setFullName("fullName");
        user1.setStatus("status");
        final Optional<User> user = Optional.of(user1);
        when(mockRepository.findCeaEmployee(0L, "ceaCode")).thenReturn(user);

        // Configure UserMapper.toCeaEmployeeContextDto(...).
        final CEAEmployeeContextDTO ceaEmployeeContextDTO = new CEAEmployeeContextDTO();
        ceaEmployeeContextDTO.setUserId(0L);
        ceaEmployeeContextDTO.setFirstName("firstName");
        ceaEmployeeContextDTO.setLastName("lastName");
        ceaEmployeeContextDTO.setMiddleName("middleName");
        ceaEmployeeContextDTO.setFullName("fullName");
        ceaEmployeeContextDTO.setCeaCode("ceaCode");
        ceaEmployeeContextDTO.setCeaDepartmentId(0L);
        ceaEmployeeContextDTO.setPosition("position");
        ceaEmployeeContextDTO.setPositionType(PositionType.HEAD);
        ceaEmployeeContextDTO.setPrivilegeCodes(Set.of(PrivilegeCode.V1_1_1));
        when(mockMapper.toCeaEmployeeContextDto(any(User.class))).thenReturn(ceaEmployeeContextDTO);

        // Run the test
        final CEAEmployeeContextDTO result = ceaEmployeeServiceImplUnderTest.getCeaEmployeeContext(0L, "ceaCode");

        // Verify the results
        assertEquals(expectedResult, result);
    }


    @Test
    @WithMockCustomUser(userRole = AuthoritiesConstants.CEA_EMPLOYEE)
    void testGetAllActiveCeaEmployeeHasAuth() {
        // Setup
        final CEAEmployeeContextDTO ceaEmployeeContextDTO = new CEAEmployeeContextDTO();
        ceaEmployeeContextDTO.setUserId(0L);
        ceaEmployeeContextDTO.setFirstName("firstName");
        ceaEmployeeContextDTO.setLastName("lastName");
        ceaEmployeeContextDTO.setMiddleName("middleName");
        ceaEmployeeContextDTO.setFullName("fullName");
        ceaEmployeeContextDTO.setCeaCode("ceaCode");
        ceaEmployeeContextDTO.setCeaDepartmentId(0L);
        ceaEmployeeContextDTO.setPosition("position");
        ceaEmployeeContextDTO.setPositionType(PositionType.HEAD);
        ceaEmployeeContextDTO.setPrivilegeCodes(Set.of(PrivilegeCode.V1_1_1));
        final List<CEAEmployeeContextDTO> expectedResult = List.of(ceaEmployeeContextDTO);

        // Configure UserService.getCurrentUser(...).
        final User user = new User();
        user.setId(0L);
        user.setLogin("login");
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setUserType("userType");
        user.setMiddleName("middleName");
        user.setFullName("fullName");
        user.setStatus("status");
        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Configure UserMapper.toCeaEmployeeContextDto(...).
        final CEAEmployeeContextDTO ceaEmployeeContextDTO1 = new CEAEmployeeContextDTO();
        ceaEmployeeContextDTO1.setUserId(0L);
        ceaEmployeeContextDTO1.setFirstName("firstName");
        ceaEmployeeContextDTO1.setLastName("lastName");
        ceaEmployeeContextDTO1.setMiddleName("middleName");
        ceaEmployeeContextDTO1.setFullName("fullName");
        ceaEmployeeContextDTO1.setCeaCode("ceaCode");
        ceaEmployeeContextDTO1.setCeaDepartmentId(0L);
        ceaEmployeeContextDTO1.setPosition("position");
        ceaEmployeeContextDTO1.setPositionType(PositionType.HEAD);
        ceaEmployeeContextDTO1.setPrivilegeCodes(Set.of(PrivilegeCode.V1_1_1));
        when(mockMapper.toCeaEmployeeContextDto(any(User.class))).thenReturn(ceaEmployeeContextDTO1);


        // Configure UserRepository.findAll(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setLogin("login");
        user1.setPassword("password");
        user1.setFirstName("firstName");
        user1.setLastName("lastName");
        user1.setEmail("email");
        user1.setUserType("userType");
        user1.setMiddleName("middleName");
        user1.setFullName("fullName");
        user1.setStatus("status");
        final List<User> users = List.of(user1);
        when(mockRepository.findAll(any(Specification.class))).thenReturn(users);

        // Run the test
        final List<CEAEmployeeContextDTO> result = ceaEmployeeServiceImplUnderTest.getAllActiveCeaEmployeeHasAuth("fullNameLike", PrivilegeCode.V1_1_1);

        // Verify the results
        assertEquals(expectedResult, result);
    }


}
