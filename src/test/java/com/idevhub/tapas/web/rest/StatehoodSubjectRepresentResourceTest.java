package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.domain.enumeration.ActiveStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import com.idevhub.tapas.service.StatehoodSubjectRepresentService;
import com.idevhub.tapas.service.criteria.RepresentCriteria;
import com.idevhub.tapas.service.dto.*;
import com.idevhub.tapas.service.impl.StatehoodSubjectRepresentOperationService;
import io.github.jhipster.service.filter.LongFilter;
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

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.MockitoAnnotations.openMocks;


@SpringBootTest
class StatehoodSubjectRepresentResourceTest {

    @Mock
    private StatehoodSubjectRepresentService mockRepresentService;
    @Mock
    private StatehoodSubjectRepresentOperationService subjectRepresentOperationService;

    private StatehoodSubjectRepresentResource statehoodSubjectRepresentResourceUnderTest;

    @BeforeEach
    void setUp() {
         openMocks(this);
        statehoodSubjectRepresentResourceUnderTest = new StatehoodSubjectRepresentResource(mockRepresentService,subjectRepresentOperationService);
    }

    @Test
    void testCreateStatehoodSubjectRepresent() throws Exception {
        // Setup
        final StatehoodSubjectRepresentCreateDTO input = new StatehoodSubjectRepresentCreateDTO("rnokpp", "firstName", "lastName", "middleName", List.of("value"));
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO.setCode("code");
        privilegeGroupGeneralDTO.setGlobal(false);
        privilegeGroupGeneralDTO.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO.setFullNameEng("fullNameEng");
        final ResponseEntity<StatehoodSubjectRepresentDTO> expectedResult = new ResponseEntity<>(new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO)), HttpStatus.OK);

        // Configure StatehoodSubjectRepresentService.create(...).
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO1 = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO1.setCode("code");
        privilegeGroupGeneralDTO1.setGlobal(false);
        privilegeGroupGeneralDTO1.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO1.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO1.setFullNameEng("fullNameEng");
        final StatehoodSubjectRepresentDTO statehoodSubjectRepresentDTO = new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO1));
        when(mockRepresentService.create(any(StatehoodSubjectRepresentCreateDTO.class))).thenReturn(statehoodSubjectRepresentDTO);

        // Run the test
        final ResponseEntity<StatehoodSubjectRepresentDTO> result = statehoodSubjectRepresentResourceUnderTest.createStatehoodSubjectRepresent(input);

        // Verify the results
        assertEquals(expectedResult.getBody(), result.getBody());
    }


    @Test
    void testUpdateStatehoodSubjectRepresent() {
        // Setup
        final StatehoodSubjectRepresentUpdateDTO inputDto = new StatehoodSubjectRepresentUpdateDTO(0L, List.of("value"));
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO.setCode("code");
        privilegeGroupGeneralDTO.setGlobal(false);
        privilegeGroupGeneralDTO.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO.setFullNameEng("fullNameEng");
        final ResponseEntity<StatehoodSubjectRepresentDTO> expectedResult = new ResponseEntity<>(new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO)), HttpStatus.OK);

        // Configure StatehoodSubjectRepresentService.update(...).
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO1 = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO1.setCode("code");
        privilegeGroupGeneralDTO1.setGlobal(false);
        privilegeGroupGeneralDTO1.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO1.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO1.setFullNameEng("fullNameEng");
        final StatehoodSubjectRepresentDTO statehoodSubjectRepresentDTO = new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO1));
        when(mockRepresentService.update(any(StatehoodSubjectRepresentUpdateDTO.class))).thenReturn(statehoodSubjectRepresentDTO);

        // Run the test
        final ResponseEntity<StatehoodSubjectRepresentDTO> result = statehoodSubjectRepresentResourceUnderTest.updateStatehoodSubjectRepresent(inputDto);

        // Verify the results
        assertEquals(expectedResult.getBody(), result.getBody());
    }

    @Test
    void testFindAllRepresentersBySubjectId() {
        // Setup
        final RepresentCriteria criteria = new RepresentCriteria();
        criteria.setSubjectId(new LongFilter());
        criteria.setFullName(new StringFilter());
        criteria.setType(new RepresentCriteria.TypeFilter());
        criteria.setStatus(new RepresentCriteria.StatusFilter());

        final ResponseEntity<List<StatehoodSubjectRepresentMainInfoDTO>> expectedResult = new ResponseEntity<>(List.of(new StatehoodSubjectRepresentMainInfoDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "subjectStatus", AccountDetailsStatus.NOT_FULL, 0L, "statehoodSubjectFullName", "statehoodSubjectEdrpou", 0L, "declarantFirstName", "declarantLastName", "declarantMiddleName", "declarantFullName")), HttpStatus.OK);

        // Configure StatehoodSubjectRepresentService.findAllRepresentersBySubjectId(...).
        final Page<StatehoodSubjectRepresentMainInfoDTO> statehoodSubjectRepresentMainInfoDTOS = new PageImpl<>(List.of(new StatehoodSubjectRepresentMainInfoDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "subjectStatus", AccountDetailsStatus.NOT_FULL, 0L, "statehoodSubjectFullName", "statehoodSubjectEdrpou", 0L, "declarantFirstName", "declarantLastName", "declarantMiddleName", "declarantFullName")));
        when(mockRepresentService.findAllRepresentersBySubjectId(any(Pageable.class), any(RepresentCriteria.class))).thenReturn(statehoodSubjectRepresentMainInfoDTOS);

        // Run the test
        final ResponseEntity<List<StatehoodSubjectRepresentMainInfoDTO>> result = statehoodSubjectRepresentResourceUnderTest.findAllRepresentersBySubjectId(PageRequest.of(0, 1), criteria);

        // Verify the results
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testGetAllActiveByCurrentDeclarant() {
        // Setup
        final List<StatehoodSubjectRepresentMainInfoDTO> expectedResult = List.of(new StatehoodSubjectRepresentMainInfoDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "subjectStatus", AccountDetailsStatus.NOT_FULL, 0L, "statehoodSubjectFullName", "statehoodSubjectEdrpou", 0L, "declarantFirstName", "declarantLastName", "declarantMiddleName", "declarantFullName"));

        // Configure StatehoodSubjectRepresentService.getAllActiveByCurrentDeclarant(...).
        final List<StatehoodSubjectRepresentMainInfoDTO> statehoodSubjectRepresentMainInfoDTOS = List.of(new StatehoodSubjectRepresentMainInfoDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "subjectStatus", AccountDetailsStatus.NOT_FULL, 0L, "statehoodSubjectFullName", "statehoodSubjectEdrpou", 0L, "declarantFirstName", "declarantLastName", "declarantMiddleName", "declarantFullName"));
        when(mockRepresentService.getAllActiveByCurrentDeclarant()).thenReturn(statehoodSubjectRepresentMainInfoDTOS);

        // Run the test
        final List<StatehoodSubjectRepresentMainInfoDTO> result = statehoodSubjectRepresentResourceUnderTest.getAllActiveByCurrentDeclarant();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testHasAccessToSubj() {
        // Setup
        final ResponseEntity<Boolean> expectedResult = new ResponseEntity<>(false, HttpStatus.OK);
        when(mockRepresentService.hasAccessByIds(anyLong(), anyLong())).thenReturn(false);

        // Run the test
        final ResponseEntity<Boolean> result = statehoodSubjectRepresentResourceUnderTest.hasAccessToSubj(0L, 0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetCurrentContext() {
        // Setup
        final ResponseEntity<StatehoodSubjectRepresentContextDTO> expectedResult = new ResponseEntity<>(new StatehoodSubjectRepresentContextDTO("subjectName", "subjectCode", 0L, 0L), HttpStatus.OK);

        // Configure StatehoodSubjectRepresentService.getCurrentContextMainDTO(...).
        final StatehoodSubjectRepresentContextDTO statehoodSubjectRepresentContextDTO = new StatehoodSubjectRepresentContextDTO("subjectName", "subjectCode", 0L, 0L);
        when(mockRepresentService.getCurrentContextMainDTO()).thenReturn(statehoodSubjectRepresentContextDTO);

        // Run the test
        final ResponseEntity<StatehoodSubjectRepresentContextDTO> result = statehoodSubjectRepresentResourceUnderTest.getCurrentContext();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetCurrentSubjectWithAuthGroups() {
        // Setup
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO.setCode("code");
        privilegeGroupGeneralDTO.setGlobal(false);
        privilegeGroupGeneralDTO.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO.setFullNameEng("fullNameEng");
        final ResponseEntity<StatehoodSubjectRepresentDTO> expectedResult = new ResponseEntity<>(new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO)), HttpStatus.OK);

        // Configure StatehoodSubjectRepresentService.getCurrentRepresentDto(...).
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO1 = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO1.setCode("code");
        privilegeGroupGeneralDTO1.setGlobal(false);
        privilegeGroupGeneralDTO1.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO1.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO1.setFullNameEng("fullNameEng");
        final StatehoodSubjectRepresentDTO statehoodSubjectRepresentDTO = new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO1));
        when(mockRepresentService.getCurrentRepresentDto()).thenReturn(statehoodSubjectRepresentDTO);

        // Run the test
        final ResponseEntity<StatehoodSubjectRepresentDTO> result = statehoodSubjectRepresentResourceUnderTest.getCurrentSubjectWithAuthGroups();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetStatehoodSubjectRepresent() {
        // Setup
        final ResponseEntity<StatehoodSubjectRepresentWithNameDTO> expectedResult = new ResponseEntity<>(new StatehoodSubjectRepresentWithNameDTO(0L, "firstName", "lastName", "middleName", 0L, 0L, StatehoodSubjectRepresentStatus.INACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE), HttpStatus.OK);

        // Configure StatehoodSubjectRepresentService.findOne(...).
        final StatehoodSubjectRepresentWithNameDTO statehoodSubjectRepresentWithNameDTO = new StatehoodSubjectRepresentWithNameDTO(0L, "firstName", "lastName", "middleName", 0L, 0L, StatehoodSubjectRepresentStatus.INACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        when(mockRepresentService.findOne(anyLong())).thenReturn(statehoodSubjectRepresentWithNameDTO);

        // Run the test
        final ResponseEntity<StatehoodSubjectRepresentWithNameDTO> result = statehoodSubjectRepresentResourceUnderTest.getStatehoodSubjectRepresent(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAllActiveByDeclarantIdMainInfo() {
        // Setup
        final List<StatehoodSubjectRepresentMainInfoDTO> expectedResult = List.of(new StatehoodSubjectRepresentMainInfoDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "subjectStatus", AccountDetailsStatus.NOT_FULL, 0L, "statehoodSubjectFullName", "statehoodSubjectEdrpou", 0L, "declarantFirstName", "declarantLastName", "declarantMiddleName", "declarantFullName"));

        // Configure StatehoodSubjectRepresentService.getAllActiveByDeclarantId(...).
        final List<StatehoodSubjectRepresentMainInfoDTO> statehoodSubjectRepresentMainInfoDTOS = List.of(new StatehoodSubjectRepresentMainInfoDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "subjectStatus", AccountDetailsStatus.NOT_FULL, 0L, "statehoodSubjectFullName", "statehoodSubjectEdrpou", 0L, "declarantFirstName", "declarantLastName", "declarantMiddleName", "declarantFullName"));
        when(mockRepresentService.getAllActiveByDeclarantId(anyLong())).thenReturn(statehoodSubjectRepresentMainInfoDTOS);

        // Run the test
        final List<StatehoodSubjectRepresentMainInfoDTO> result = statehoodSubjectRepresentResourceUnderTest.getAllActiveByDeclarantIdMainInfo(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testDeleteStatehoodSubjectRepresent() {
        // Setup
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO.setCode("code");
        privilegeGroupGeneralDTO.setGlobal(false);
        privilegeGroupGeneralDTO.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO.setFullNameEng("fullNameEng");
        final ResponseEntity<StatehoodSubjectRepresentDTO> expectedResult = new ResponseEntity<>(new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO)), HttpStatus.OK);

        // Configure StatehoodSubjectRepresentService.removeRepresent(...).
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO1 = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO1.setCode("code");
        privilegeGroupGeneralDTO1.setGlobal(false);
        privilegeGroupGeneralDTO1.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO1.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO1.setFullNameEng("fullNameEng");
        final StatehoodSubjectRepresentDTO statehoodSubjectRepresentDTO = new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO1));
        when(mockRepresentService.removeRepresent(anyLong(), anyLong())).thenReturn(statehoodSubjectRepresentDTO);

        // Run the test
        final ResponseEntity<StatehoodSubjectRepresentDTO> result = statehoodSubjectRepresentResourceUnderTest.deleteStatehoodSubjectRepresent(0L, 0L);

        // Verify the results
        assertEquals(expectedResult.getBody(), result.getBody());
    }

    @Test
    void testChangeContextTo() {
        // Setup

        // Run the test
        final ResponseEntity<Void> result = statehoodSubjectRepresentResourceUnderTest.changeContextTo(0L);

        // Verify the results
        verify(mockRepresentService).changeContextTo(0L);
    }
}
