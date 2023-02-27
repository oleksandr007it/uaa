package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.domain.enumeration.PositionType;
import com.idevhub.tapas.domain.enumeration.PrivilegeCode;
import com.idevhub.tapas.service.CEAEmployeeService;
import com.idevhub.tapas.service.criteria.CEAEmployeeCriteria;
import com.idevhub.tapas.service.dto.CEAEmployeeContextDTO;
import com.idevhub.tapas.service.dto.CEAEmployeeDTO;
import com.idevhub.tapas.service.dto.CeaEmployeeMainInfoDTO;
import io.github.jhipster.service.filter.StringFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
@SpringBootTest
class CEAEmployeeResourceTest {

    @Mock
    private CEAEmployeeService mockService;

    private CEAEmployeeResource ceaEmployeeResourceUnderTest;

    @BeforeEach
    void setUp() {
        openMocks(this);
        ceaEmployeeResourceUnderTest = new CEAEmployeeResource(mockService);
    }

    @Test
    void testCreateCeaEmployee() throws Exception {
        // Setup
        final CEAEmployeeDTO employeeDTO = new CEAEmployeeDTO(null, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD);
        final ResponseEntity<CEAEmployeeDTO> expectedResult = new ResponseEntity<>(new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD), HttpStatus.OK);

        // Configure CEAEmployeeService.saveEmployee(...).
        final CEAEmployeeDTO ceaEmployeeDTO = new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD);
        when(mockService.saveEmployee(any(CEAEmployeeDTO.class))).thenReturn(ceaEmployeeDTO);

        // Run the test
        final ResponseEntity<CEAEmployeeDTO> result = ceaEmployeeResourceUnderTest.createCeaEmployee(employeeDTO);

        // Verify the results
        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }



    @Test
    void testUpdateCeaEmployee() {
        // Setup
        final CEAEmployeeDTO employeeDTO = new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD);
        final ResponseEntity<CEAEmployeeDTO> expectedResult = new ResponseEntity<>(new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD), HttpStatus.OK);

        // Configure CEAEmployeeService.saveEmployee(...).
        final CEAEmployeeDTO ceaEmployeeDTO = new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD);
        when(mockService.saveEmployee(new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD))).thenReturn(ceaEmployeeDTO);

        // Run the test
        final ResponseEntity<CEAEmployeeDTO> result = ceaEmployeeResourceUnderTest.updateCeaEmployee(employeeDTO);

        // Verify the results
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }


    @Test
    void testFindAll() {
        // Setup
        final CEAEmployeeCriteria criteria = new CEAEmployeeCriteria(new StringFilter(), new StringFilter(), new StringFilter(), new StringFilter(), new StringFilter(), new CEAEmployeeCriteria.PositionTypeFilter());
        final ResponseEntity<List<CEAEmployeeDTO>> expectedResult = new ResponseEntity<>(List.of(new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD)), HttpStatus.OK);

        // Configure CEAEmployeeService.findAll(...).
        final Page<CEAEmployeeDTO> ceaEmployeeDTOS = new PageImpl<>(List.of(new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD)));

        when(mockService.findAll(any(Pageable.class), any(CEAEmployeeCriteria.class))).thenReturn(ceaEmployeeDTOS);

        // Run the test
        final ResponseEntity<List<CEAEmployeeDTO>> result = ceaEmployeeResourceUnderTest.findAll(PageRequest.of(0, 1), criteria);

        // Verify the results
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testGetCeaEmployeeById() {
        // Setup
        final ResponseEntity<CEAEmployeeDTO> expectedResult = new ResponseEntity<>(new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD), HttpStatus.OK);

        // Configure CEAEmployeeService.getById(...).
        final CEAEmployeeDTO ceaEmployeeDTO = new CEAEmployeeDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "edrpouCode", 0L, "departmentFullName", "position", PositionType.HEAD);
        when(mockService.getById(0L)).thenReturn(ceaEmployeeDTO);

        // Run the test
        final ResponseEntity<CEAEmployeeDTO> result = ceaEmployeeResourceUnderTest.getCeaEmployeeById(0L);

        // Verify the results
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testGetCeaDepartmentHead() {
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

        // Configure CEAEmployeeService.getDepartmentHead(...).
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
        when(mockService.getDepartmentHead(0L)).thenReturn(ceaEmployeeContextDTO);

        // Run the test
        final CEAEmployeeContextDTO result = ceaEmployeeResourceUnderTest.getCeaDepartmentHead(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetCeaEmployeeCurrentContext() {
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

        // Configure CEAEmployeeService.getCurrentContext(...).
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
        when(mockService.getCurrentContext()).thenReturn(ceaEmployeeContextDTO);

        // Run the test
        final CEAEmployeeContextDTO result = ceaEmployeeResourceUnderTest.getCeaEmployeeCurrentContext();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetCeaEmployee() {
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

        // Configure CEAEmployeeService.getCeaEmployeeContext(...).
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
        when(mockService.getCeaEmployeeContext(0L, "ceaCode")).thenReturn(ceaEmployeeContextDTO);

        // Run the test
        final CEAEmployeeContextDTO result = ceaEmployeeResourceUnderTest.getCeaEmployee(0L, "ceaCode");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllCeaEmployeeHasAuth() {
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

        // Configure CEAEmployeeService.getAllActiveCeaEmployeeHasAuth(...).
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
        final List<CEAEmployeeContextDTO> ceaEmployeeContextDTOS = List.of(ceaEmployeeContextDTO1);
        when(mockService.getAllActiveCeaEmployeeHasAuth("fullNameLike", PrivilegeCode.V1_1_1)).thenReturn(ceaEmployeeContextDTOS);

        // Run the test
        final List<CEAEmployeeContextDTO> result = ceaEmployeeResourceUnderTest.getAllCeaEmployeeHasAuth("fullNameLike", PrivilegeCode.V1_1_1);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetCeaUser() {
        // Setup
        final ResponseEntity<CeaEmployeeMainInfoDTO> expectedResult = new ResponseEntity<>(new CeaEmployeeMainInfoDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "orgUnit", "position"), HttpStatus.OK);

        // Configure CEAEmployeeService.getCeaUserMainInfo(...).
        final CeaEmployeeMainInfoDTO ceaEmployeeMainInfoDTO = new CeaEmployeeMainInfoDTO(0L, "firstName", "lastName", "middleName", "rnokpp", "email", "phoneNumber", "orgUnit", "position");
        when(mockService.getCeaUserMainInfo()).thenReturn(ceaEmployeeMainInfoDTO);

        // Run the test
        final ResponseEntity<CeaEmployeeMainInfoDTO> result = ceaEmployeeResourceUnderTest.getCeaUser();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
