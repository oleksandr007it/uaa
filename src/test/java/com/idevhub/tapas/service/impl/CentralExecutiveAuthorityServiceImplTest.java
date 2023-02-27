package com.idevhub.tapas.service.impl;

import com.hazelcast.util.UuidUtil;
import com.idevhub.tapas.domain.*;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.CEADepartment;
import com.idevhub.tapas.domain.CentralExecutiveAuthority;
import com.idevhub.tapas.domain.enumeration.CEADepartmentStatus;
import com.idevhub.tapas.domain.enumeration.CentralExecutiveAuthorityStatus;
import com.idevhub.tapas.repository.CEADepartmentRepository;
import com.idevhub.tapas.repository.CentralExecutiveAuthorityRepository;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.WithMockCustomUser;
import com.idevhub.tapas.service.dto.CEADepartmentDTO;
import com.idevhub.tapas.service.dto.CentralExecutiveAuthorityDTO;
import com.idevhub.tapas.service.mapper.CEADepartmentMapper;
import com.idevhub.tapas.service.mapper.CentralExecutiveAuthorityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
@WithMockCustomUser(userRole = AuthoritiesConstants.CEA_EMPLOYEE)
class CentralExecutiveAuthorityServiceImplTest {

    @Mock
    private CentralExecutiveAuthorityRepository mockCentralExecutiveAuthorityRepository;
    @Mock
    private CEADepartmentRepository mockDepartmentRepository;
    @Mock
    private CentralExecutiveAuthorityMapper mockCentralExecutiveAuthorityMapper;
    @Mock
    private CEADepartmentMapper mockCeaDepartmentMapper;

    private CentralExecutiveAuthorityServiceImpl centralExecutiveAuthorityServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        openMocks(this);
        centralExecutiveAuthorityServiceImplUnderTest = new CentralExecutiveAuthorityServiceImpl(mockCentralExecutiveAuthorityRepository, mockDepartmentRepository, mockCentralExecutiveAuthorityMapper, mockCeaDepartmentMapper);
    }

    @Test
    void testSave() {
        // Setup
        final CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO = new CentralExecutiveAuthorityDTO();
        centralExecutiveAuthorityDTO.setId(0L);
        centralExecutiveAuthorityDTO.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthorityDTO.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO.setLastModifiedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO.setCode("code");
        centralExecutiveAuthorityDTO.setFullNameUkr("fullNameUkr");
        centralExecutiveAuthorityDTO.setFullNameEng("fullNameEng");
        centralExecutiveAuthorityDTO.setTelNumber("telNumber");
        centralExecutiveAuthorityDTO.setEmail("email");

        final CentralExecutiveAuthorityDTO expectedResult = new CentralExecutiveAuthorityDTO();
        expectedResult.setId(0L);
        expectedResult.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        expectedResult.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setLastModifiedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setCode("code");
        expectedResult.setFullNameUkr("fullNameUkr");
        expectedResult.setFullNameEng("fullNameEng");
        expectedResult.setTelNumber("telNumber");
        expectedResult.setEmail("email");

        // Configure CentralExecutiveAuthorityMapper.toEntity(...).
        final CentralExecutiveAuthority centralExecutiveAuthority = new CentralExecutiveAuthority();
        centralExecutiveAuthority.setId(0L);
        centralExecutiveAuthority.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority.centralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthority.deletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthority.setCode("code");
        centralExecutiveAuthority.code("code");
        centralExecutiveAuthority.setFullNameUkr("fullNameUkr");
        centralExecutiveAuthority.fullNameUkr("fullNameUkr");
        centralExecutiveAuthority.setFullNameEng("fullNameEng");
        when(mockCentralExecutiveAuthorityMapper.toEntity(any(CentralExecutiveAuthorityDTO.class))).thenReturn(centralExecutiveAuthority);

        // Configure CentralExecutiveAuthorityRepository.save(...).
        final CentralExecutiveAuthority centralExecutiveAuthority1 = new CentralExecutiveAuthority();
        centralExecutiveAuthority1.setId(0L);
        centralExecutiveAuthority1.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority1.centralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthority1.deletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthority1.setCode("code");
        centralExecutiveAuthority1.code("code");
        centralExecutiveAuthority1.setFullNameUkr("fullNameUkr");
        centralExecutiveAuthority1.fullNameUkr("fullNameUkr");
        centralExecutiveAuthority1.setFullNameEng("fullNameEng");
        when(mockCentralExecutiveAuthorityRepository.save(any(CentralExecutiveAuthority.class))).thenReturn(centralExecutiveAuthority1);

        // Configure CentralExecutiveAuthorityMapper.toDto(...).
        final CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO1 = new CentralExecutiveAuthorityDTO();
        centralExecutiveAuthorityDTO1.setId(0L);
        centralExecutiveAuthorityDTO1.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthorityDTO1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO1.setLastModifiedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO1.setCode("code");
        centralExecutiveAuthorityDTO1.setFullNameUkr("fullNameUkr");
        centralExecutiveAuthorityDTO1.setFullNameEng("fullNameEng");
        centralExecutiveAuthorityDTO1.setTelNumber("telNumber");
        centralExecutiveAuthorityDTO1.setEmail("email");
        when(mockCentralExecutiveAuthorityMapper.toDto(any(CentralExecutiveAuthority.class))).thenReturn(centralExecutiveAuthorityDTO1);

        // Run the test
        final CentralExecutiveAuthorityDTO result = centralExecutiveAuthorityServiceImplUnderTest.save(centralExecutiveAuthorityDTO);

        // Verify the results
        assertEquals(expectedResult, result);
    }


    @Test
    void testFindAll() {
        // Setup
        final CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO = new CentralExecutiveAuthorityDTO();
        centralExecutiveAuthorityDTO.setId(0L);
        centralExecutiveAuthorityDTO.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthorityDTO.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO.setLastModifiedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO.setCode("code");
        centralExecutiveAuthorityDTO.setFullNameUkr("fullNameUkr");
        centralExecutiveAuthorityDTO.setFullNameEng("fullNameEng");
        centralExecutiveAuthorityDTO.setTelNumber("telNumber");
        centralExecutiveAuthorityDTO.setEmail("email");
        final List<CentralExecutiveAuthorityDTO> expectedResult = List.of(centralExecutiveAuthorityDTO);

        // Configure CentralExecutiveAuthorityRepository.findAll(...).
        final CentralExecutiveAuthority centralExecutiveAuthority = new CentralExecutiveAuthority();
        centralExecutiveAuthority.setId(0L);
        centralExecutiveAuthority.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority.centralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthority.deletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthority.setCode("code");
        centralExecutiveAuthority.code("code");
        centralExecutiveAuthority.setFullNameUkr("fullNameUkr");
        centralExecutiveAuthority.fullNameUkr("fullNameUkr");
        centralExecutiveAuthority.setFullNameEng("fullNameEng");
        final List<CentralExecutiveAuthority> centralExecutiveAuthorities = List.of(centralExecutiveAuthority);
        when(mockCentralExecutiveAuthorityRepository.findAll()).thenReturn(centralExecutiveAuthorities);

        // Configure CentralExecutiveAuthorityMapper.toDto(...).
        final CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO1 = new CentralExecutiveAuthorityDTO();
        centralExecutiveAuthorityDTO1.setId(0L);
        centralExecutiveAuthorityDTO1.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthorityDTO1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO1.setLastModifiedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO1.setCode("code");
        centralExecutiveAuthorityDTO1.setFullNameUkr("fullNameUkr");
        centralExecutiveAuthorityDTO1.setFullNameEng("fullNameEng");
        centralExecutiveAuthorityDTO1.setTelNumber("telNumber");
        centralExecutiveAuthorityDTO1.setEmail("email");
        when(mockCentralExecutiveAuthorityMapper.toDto(any(CentralExecutiveAuthority.class))).thenReturn(centralExecutiveAuthorityDTO1);

        // Run the test
        final List<CentralExecutiveAuthorityDTO> result = centralExecutiveAuthorityServiceImplUnderTest.findAll();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testFindOne() {
        // Setup
        final CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO = new CentralExecutiveAuthorityDTO();
        centralExecutiveAuthorityDTO.setId(0L);
        centralExecutiveAuthorityDTO.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthorityDTO.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO.setLastModifiedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO.setCode("code");
        centralExecutiveAuthorityDTO.setFullNameUkr("fullNameUkr");
        centralExecutiveAuthorityDTO.setFullNameEng("fullNameEng");
        centralExecutiveAuthorityDTO.setTelNumber("telNumber");
        centralExecutiveAuthorityDTO.setEmail("email");
        final Optional<CentralExecutiveAuthorityDTO> expectedResult = Optional.of(centralExecutiveAuthorityDTO);

        // Configure CentralExecutiveAuthorityRepository.findById(...).
        final CentralExecutiveAuthority centralExecutiveAuthority1 = new CentralExecutiveAuthority();
        centralExecutiveAuthority1.setId(0L);
        centralExecutiveAuthority1.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority1.centralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthority1.deletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthority1.setCode("code");
        centralExecutiveAuthority1.code("code");
        centralExecutiveAuthority1.setFullNameUkr("fullNameUkr");
        centralExecutiveAuthority1.fullNameUkr("fullNameUkr");
        centralExecutiveAuthority1.setFullNameEng("fullNameEng");
        final Optional<CentralExecutiveAuthority> centralExecutiveAuthority = Optional.of(centralExecutiveAuthority1);
        when(mockCentralExecutiveAuthorityRepository.findById(anyLong())).thenReturn(centralExecutiveAuthority);

        // Configure CentralExecutiveAuthorityMapper.toDto(...).
        final CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO1 = new CentralExecutiveAuthorityDTO();
        centralExecutiveAuthorityDTO1.setId(0L);
        centralExecutiveAuthorityDTO1.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthorityDTO1.setCreatedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO1.setLastModifiedDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthorityDTO1.setCode("code");
        centralExecutiveAuthorityDTO1.setFullNameUkr("fullNameUkr");
        centralExecutiveAuthorityDTO1.setFullNameEng("fullNameEng");
        centralExecutiveAuthorityDTO1.setTelNumber("telNumber");
        centralExecutiveAuthorityDTO1.setEmail("email");
        when(mockCentralExecutiveAuthorityMapper.toDto(any(CentralExecutiveAuthority.class))).thenReturn(centralExecutiveAuthorityDTO1);

        // Run the test
        final Optional<CentralExecutiveAuthorityDTO> result = centralExecutiveAuthorityServiceImplUnderTest.findOne(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }


    @Test
    void testDelete() {
        // Setup

        // Run the test
        centralExecutiveAuthorityServiceImplUnderTest.delete(0L);

        // Verify the results
        verify(mockCentralExecutiveAuthorityRepository).deleteById(0L);
    }

    @Test
    void testGetByCode() {
        // Setup
        final CentralExecutiveAuthority expectedResult = new CentralExecutiveAuthority();
        expectedResult.setId(0L);
        expectedResult.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        expectedResult.centralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        expectedResult.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.deletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setCode("code");
        expectedResult.code("code");
        expectedResult.setFullNameUkr("fullNameUkr");
        expectedResult.fullNameUkr("fullNameUkr");
        expectedResult.setFullNameEng("fullNameEng");

        // Configure CentralExecutiveAuthorityRepository.findByCode(...).
        final CentralExecutiveAuthority centralExecutiveAuthority1 = new CentralExecutiveAuthority();
        centralExecutiveAuthority1.setId(0L);
        centralExecutiveAuthority1.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority1.centralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthority1.deletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        centralExecutiveAuthority1.setCode("code");
        centralExecutiveAuthority1.code("code");
        centralExecutiveAuthority1.setFullNameUkr("fullNameUkr");
        centralExecutiveAuthority1.fullNameUkr("fullNameUkr");
        centralExecutiveAuthority1.setFullNameEng("fullNameEng");
        final Optional<CentralExecutiveAuthority> centralExecutiveAuthority = Optional.of(centralExecutiveAuthority1);
        when(mockCentralExecutiveAuthorityRepository.findByCode(anyString())).thenReturn(centralExecutiveAuthority);

        // Run the test
        final CentralExecutiveAuthority result = centralExecutiveAuthorityServiceImplUnderTest.getByCode("edrpou");

        // Verify the results
        assertEquals(expectedResult, result);
    }


    @Test
    void testGetAllActiveDepartments() {

        // Setup
        String stubAddressId = UuidUtil.newSecureUuidString().replaceAll("-", "");

        CentralExecutiveAuthority centralExecutiveAuthority = new CentralExecutiveAuthority();
        centralExecutiveAuthority.setId(0L);
        centralExecutiveAuthority.setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus.ACTIVE);
        centralExecutiveAuthority.setDeletedAt(Instant.now());
        centralExecutiveAuthority.setCode("");
        centralExecutiveAuthority.setFullNameUkr("");
        centralExecutiveAuthority.setFullNameEng("");
        centralExecutiveAuthority.setTelNumber("");
        centralExecutiveAuthority.setEmail("");
        centralExecutiveAuthority.setDeletedBy("");
        centralExecutiveAuthority.setAddress(new Address());
        centralExecutiveAuthority.setCreatedBy("");
        centralExecutiveAuthority.setCreatedDate(Instant.now());
        centralExecutiveAuthority.setLastModifiedBy("");
        centralExecutiveAuthority.setLastModifiedDate(Instant.now());

        final CEADepartmentDTO ceaDepartmentDTO = new CEADepartmentDTO();
        ceaDepartmentDTO.setId(0L);
        ceaDepartmentDTO.setCeaDepartmentStatus(CEADepartmentStatus.ACTIVE);
        ceaDepartmentDTO.setFullNameUkr("fullNameUkr");
        ceaDepartmentDTO.setFullNameEng("fullNameEng");
        ceaDepartmentDTO.setDescription("description");
        ceaDepartmentDTO.setTelNumber("telNumber");
        ceaDepartmentDTO.setEmail("email");
        ceaDepartmentDTO.setCentralExecutiveAuthorityId(0L);
        ceaDepartmentDTO.setCentralExecutiveAuthorityCode("centralExecutiveAuthorityCode");
        ceaDepartmentDTO.setAddressId(stubAddressId);
        final List<CEADepartmentDTO> expectedResult = List.of(ceaDepartmentDTO);

        // Configure CEADepartmentRepository.findAllCeaDepartments(...).
        final CEADepartment ceaDepartment = new CEADepartment();
        ceaDepartment.setId(0L);
        ceaDepartment.setCeaDepartmentStatus(CEADepartmentStatus.ACTIVE);
        ceaDepartment.setCentralExecutiveAuthority(centralExecutiveAuthority);
        ceaDepartment.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        ceaDepartment.deletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        ceaDepartment.setDeletedBy("deletedBy");
        ceaDepartment.deletedBy("deletedBy");
        ceaDepartment.setFullNameUkr("fullNameUkr");
        ceaDepartment.fullNameUkr("fullNameUkr");
        ceaDepartment.setFullNameEng("fullNameEng");
        final List<CEADepartment> ceaDepartments = List.of(ceaDepartment);
        when(mockDepartmentRepository.findAllCeaDepartments(anyString())).thenReturn(ceaDepartments);

        // Configure CEADepartmentMapper.toDto(...).
        final CEADepartmentDTO ceaDepartmentDTO1 = new CEADepartmentDTO();
        ceaDepartmentDTO1.setId(0L);
        ceaDepartmentDTO1.setCeaDepartmentStatus(CEADepartmentStatus.ACTIVE);
        ceaDepartmentDTO1.setFullNameUkr("fullNameUkr");
        ceaDepartmentDTO1.setFullNameEng("fullNameEng");
        ceaDepartmentDTO1.setDescription("description");
        ceaDepartmentDTO1.setTelNumber("telNumber");
        ceaDepartmentDTO1.setEmail("email");
        ceaDepartmentDTO1.setCentralExecutiveAuthorityId(0L);
        ceaDepartmentDTO1.setCentralExecutiveAuthorityCode("centralExecutiveAuthorityCode");
        ceaDepartmentDTO1.setAddressId(stubAddressId);
        when(mockCeaDepartmentMapper.toDto(any(CEADepartment.class))).thenReturn(ceaDepartmentDTO1);

        // Run the test
        final List<CEADepartmentDTO> result = centralExecutiveAuthorityServiceImplUnderTest.getAllActiveDepartments("edrpou");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetDepartment() {
        // Setup
        final CEADepartment expectedResult = new CEADepartment();
        expectedResult.setId(0L);
        expectedResult.setCeaDepartmentStatus(CEADepartmentStatus.ACTIVE);
        expectedResult.ceaDepartmentStatus(CEADepartmentStatus.ACTIVE);
        expectedResult.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.deletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setDeletedBy("deletedBy");
        expectedResult.deletedBy("deletedBy");
        expectedResult.setFullNameUkr("fullNameUkr");
        expectedResult.fullNameUkr("fullNameUkr");
        expectedResult.setFullNameEng("fullNameEng");

        // Configure CEADepartmentRepository.findCeaDepartmentById(...).
        final CEADepartment ceaDepartment1 = new CEADepartment();
        ceaDepartment1.setId(0L);
        ceaDepartment1.setCeaDepartmentStatus(CEADepartmentStatus.ACTIVE);
        ceaDepartment1.ceaDepartmentStatus(CEADepartmentStatus.ACTIVE);
        ceaDepartment1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        ceaDepartment1.deletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        ceaDepartment1.setDeletedBy("deletedBy");
        ceaDepartment1.deletedBy("deletedBy");
        ceaDepartment1.setFullNameUkr("fullNameUkr");
        ceaDepartment1.fullNameUkr("fullNameUkr");
        ceaDepartment1.setFullNameEng("fullNameEng");
        final Optional<CEADepartment> ceaDepartment = Optional.of(ceaDepartment1);
        when(mockDepartmentRepository.findCeaDepartmentById(anyString(), anyLong())).thenReturn(ceaDepartment);

        // Run the test
        final CEADepartment result = centralExecutiveAuthorityServiceImplUnderTest.getDepartment("edrpou", 0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
