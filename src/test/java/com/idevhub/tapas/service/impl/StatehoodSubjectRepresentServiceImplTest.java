package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.UaaApp;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.PropertyStatus;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.domain.enumeration.ActiveStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import com.idevhub.tapas.repository.StatehoodSubjectRepresentRepository;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.utils.TestSecurityUtils;
import com.idevhub.tapas.service.MailService;
import com.idevhub.tapas.service.PrivilegeForRepresentService;
import com.idevhub.tapas.service.StatehoodSubjectRepresentLookupService;
import com.idevhub.tapas.service.UserService;
import com.idevhub.tapas.service.criteria.RepresentCriteria;
import com.idevhub.tapas.service.criteria.RepresentSpecificationHelper;
import com.idevhub.tapas.service.dto.*;
import com.idevhub.tapas.service.mapper.StatehoodSubjectRepresentMapper;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static com.idevhub.tapas.util.EntityUtils.privilegeInGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApp.class)
@AutoConfigureMockMvc
@WithMockUser(value = "admin", username = "admin", authorities = AuthoritiesConstants.ADMIN)
class StatehoodSubjectRepresentServiceImplTest {

    @Mock
    private StatehoodSubjectRepresentRepository mockStatehoodSubjectRepresentRepository;
    @Mock
    private StatehoodSubjectRepresentMapper mockStatehoodSubjectRepresentMapper;
    @Mock
    private UserService mockUserService;
    @Mock
    private StatehoodSubjectRepresentLookupService mockStatehoodSubjectRepresentLookupService;
    @Autowired
    private RepresentSpecificationHelper mockRepresentSpecificationHelper;
    @Autowired
    private  PrivilegeForRepresentService privilegeForRepresentService;
    @Autowired
    private EntityManager em;

    @Mock
    private MailService mockMailService;

    private StatehoodSubjectRepresentServiceImpl statehoodSubjectRepresentServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        openMocks(this);

        // Create some local group with meta for created Subjec
        statehoodSubjectRepresentServiceImplUnderTest = new StatehoodSubjectRepresentServiceImpl(mockStatehoodSubjectRepresentRepository, mockStatehoodSubjectRepresentMapper, mockUserService, mockStatehoodSubjectRepresentLookupService, mockRepresentSpecificationHelper, mockMailService, privilegeForRepresentService);
    }

    @Test
    @Transactional
    void testCreate2() {

        TestSecurityUtils.setSecurityContext(12345L);

        var localGroup = privilegeInGroup(em, "V1_1_2_test", "GR_V1_LEGAL_ENTITY");
        em.persist(localGroup);

        // Setup
        final StatehoodSubjectRepresentCreateDTO input = new StatehoodSubjectRepresentCreateDTO("rnokpp", "firstName", "lastName", "middleName", List.of("value"));
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO.setCode("code");
        privilegeGroupGeneralDTO.setGlobal(false);
        privilegeGroupGeneralDTO.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO.setFullNameEng("fullNameEng");
        final StatehoodSubjectRepresentDTO expectedResult = new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO));

        // Configure UserService.getActiveUserByRnokppAndNames(...).
        final User user = new User();
        user.setId(12345L);
        user.setLogin("login");
        user.setPassword("password");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");
        user.setUserType("userType");
        user.setMiddleName("middleName");
        user.setFullName("fullName");
        user.setStatus(UserStatus.ACTIVE);
        user.setPropertyStatus(PropertyStatus.CONFIRMED);
        when(mockUserService.getActiveUserByRnokppAndNames(anyString(), anyString(), anyString(), anyString())).thenReturn(user);

        // Configure StatehoodSubjectRepresentCheckService.tryToGetAndCheckRepresent(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent.setId(0L);
        statehoodSubjectRepresent.setDeclarant(user);
        statehoodSubjectRepresent.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent.setCreatedBy("createdBy");
        statehoodSubjectRepresent.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent.setDeletedBy("deletedBy");

        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectStatus("subjectStatus");
        statehoodSubject.setSubjectStatus("subjectStatus");
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);

        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setStatehoodSubject(statehoodSubject);
        when(mockStatehoodSubjectRepresentLookupService.tryToGetActiveRepresent(anyLong())).thenReturn(statehoodSubjectRepresent);


        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent2 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent2.setId(0L);
        statehoodSubjectRepresent2.setDeclarant(user);
        statehoodSubjectRepresent2.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.DELETED);
        statehoodSubjectRepresent2.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent2.setCreatedBy("createdBy");
        statehoodSubjectRepresent2.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent2.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setStatehoodSubject(statehoodSubject1);
        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent1 = Optional.of(statehoodSubjectRepresent2);
        when(mockStatehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(anyLong(), anyLong())).thenReturn(statehoodSubjectRepresent1);


        //  statehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(

        // Configure StatehoodSubjectRepresentRepository.save(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent3 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent3.setId(0L);
        statehoodSubjectRepresent3.setDeclarant(user);
        statehoodSubjectRepresent3.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent3.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent3.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent3.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent3.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent3.setCreatedBy("createdBy");
        statehoodSubjectRepresent3.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent3.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject2 = new StatehoodSubject();
        statehoodSubject2.setId(0L);
        statehoodSubject2.setSubjectStatus("subjectStatus");
        statehoodSubject2.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject2.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject2.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent3.setStatehoodSubject(statehoodSubject2);
        when(mockStatehoodSubjectRepresentRepository.save(any(StatehoodSubjectRepresent.class))).thenReturn(statehoodSubjectRepresent3);

        // Configure StatehoodSubjectRepresentMapper.toDto(...).
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO1 = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO1.setCode("code");
        privilegeGroupGeneralDTO1.setGlobal(false);
        privilegeGroupGeneralDTO1.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO1.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO1.setFullNameEng("fullNameEng");
        final StatehoodSubjectRepresentDTO statehoodSubjectRepresentDTO = new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO1));
        when(mockStatehoodSubjectRepresentMapper.toDto(any(StatehoodSubjectRepresent.class))).thenReturn(statehoodSubjectRepresentDTO);

        // Run the test
        final StatehoodSubjectRepresentDTO result = statehoodSubjectRepresentServiceImplUnderTest.create(input);

        // Verify the results
        assertEquals(expectedResult, result);
        em.remove(localGroup);
    }




    @Test
    void testFindOne() {
        TestSecurityUtils.setSecurityContext(12345L);
        // Setup
        final StatehoodSubjectRepresentWithNameDTO expectedResult = new StatehoodSubjectRepresentWithNameDTO(0L, "firstName", "lastName", "middleName", 0L, 0L, StatehoodSubjectRepresentStatus.INACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);

        // Configure StatehoodSubjectRepresentRepository.findByRepresentId(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.INACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent1.setCreatedBy("createdBy");
        statehoodSubjectRepresent1.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent1.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectStatus("subjectStatus");
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject);
        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent = Optional.of(statehoodSubjectRepresent1);
        when(mockStatehoodSubjectRepresentRepository.findByRepresentId(anyLong())).thenReturn(statehoodSubjectRepresent);

        // Configure StatehoodSubjectRepresentCheckService.tryToGetAndCheckRepresent(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent2 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent2.setId(0L);
        statehoodSubjectRepresent2.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.INACTIVE);
        statehoodSubjectRepresent2.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent2.setCreatedBy("createdBy");
        statehoodSubjectRepresent2.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent2.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setStatehoodSubject(statehoodSubject1);
        when(mockStatehoodSubjectRepresentLookupService.tryToGetActiveRepresent(anyLong(), anyLong())).thenReturn(statehoodSubjectRepresent2);

        // Configure StatehoodSubjectRepresentMapper.toDtoWithName(...).
        final StatehoodSubjectRepresentWithNameDTO statehoodSubjectRepresentWithNameDTO = new StatehoodSubjectRepresentWithNameDTO(0L, "firstName", "lastName", "middleName", 0L, 0L, StatehoodSubjectRepresentStatus.INACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        when(mockStatehoodSubjectRepresentMapper.toDtoWithName(any(StatehoodSubjectRepresent.class))).thenReturn(statehoodSubjectRepresentWithNameDTO);

        // Run the test
        final StatehoodSubjectRepresentWithNameDTO result = statehoodSubjectRepresentServiceImplUnderTest.findOne(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }


    @Test
    void testGetCurrentRepresentDto() {
        TestSecurityUtils.setSecurityContext(12345L);
        // Setup
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO.setCode("code");
        privilegeGroupGeneralDTO.setGlobal(false);
        privilegeGroupGeneralDTO.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO.setFullNameEng("fullNameEng");
        final StatehoodSubjectRepresentDTO expectedResult = new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO));

        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent1.setCreatedBy("createdBy");
        statehoodSubjectRepresent1.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent1.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject);
        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent = Optional.of(statehoodSubjectRepresent1);
        when(mockStatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(anyLong())).thenReturn(statehoodSubjectRepresent);

        // Configure StatehoodSubjectRepresentMapper.toDto(...).
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO1 = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO1.setCode("code");
        privilegeGroupGeneralDTO1.setGlobal(false);
        privilegeGroupGeneralDTO1.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO1.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO1.setFullNameEng("fullNameEng");
        final StatehoodSubjectRepresentDTO statehoodSubjectRepresentDTO = new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO1));
        when(mockStatehoodSubjectRepresentMapper.toDto(any(StatehoodSubjectRepresent.class))).thenReturn(statehoodSubjectRepresentDTO);

        // Run the test
        final StatehoodSubjectRepresentDTO result = statehoodSubjectRepresentServiceImplUnderTest.getCurrentRepresentDto();

        // Verify the results
        assertEquals(expectedResult, result);
    }


    @Test
    void testGetAllActiveByDeclarantId() {

        TestSecurityUtils.setSecurityContext(12345L);
        // Setup
        final List<StatehoodSubjectRepresentMainInfoDTO> expectedResult = List.of(new StatehoodSubjectRepresentMainInfoDTO(0L, StatehoodSubjectRepresentStatus.ACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "subjectStatus", AccountDetailsStatus.NOT_FULL, 0L, "statehoodSubjectFullName", "statehoodSubjectEdrpou", 0L, "declarantFirstName", "declarantLastName", "declarantMiddleName", "declarantFullName"));

        // Configure StatehoodSubjectRepresentRepository.findAllByDeclarant_IdAndSubjectRepresentStatus(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent.setId(0L);
        statehoodSubjectRepresent.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent.setCreatedBy("createdBy");
        statehoodSubjectRepresent.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent.setDeletedBy("deletedBy");

        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setStatehoodSubject(statehoodSubject);
        final List<StatehoodSubjectRepresent> statehoodSubjectRepresents = List.of(statehoodSubjectRepresent);
        when(mockStatehoodSubjectRepresentRepository.findAllByDeclarant_IdAndSubjectRepresentStatus(anyLong(), any(StatehoodSubjectRepresentStatus.class))).thenReturn(statehoodSubjectRepresents);

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
        user.setStatus(UserStatus.ACTIVE);
        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Configure StatehoodSubjectRepresentMapper.toMainInfoDto(...).
        final List<StatehoodSubjectRepresentMainInfoDTO> statehoodSubjectRepresentMainInfoDTOS = List.of(new StatehoodSubjectRepresentMainInfoDTO(0L, StatehoodSubjectRepresentStatus.ACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "subjectStatus", AccountDetailsStatus.NOT_FULL, 0L, "statehoodSubjectFullName", "statehoodSubjectEdrpou", 0L, "declarantFirstName", "declarantLastName", "declarantMiddleName", "declarantFullName"));
        when(mockStatehoodSubjectRepresentMapper.toMainInfoDto(anyList())).thenReturn(statehoodSubjectRepresentMainInfoDTOS);

        // Run the test
        final List<StatehoodSubjectRepresentMainInfoDTO> result = statehoodSubjectRepresentServiceImplUnderTest.getAllActiveByDeclarantId(0L);

        // Verify the results
        assertThat(result.size()).isGreaterThan(0);
    }

    @Test
    void testHasAccessByIds() {
        // Setup
        TestSecurityUtils.setSecurityContext(12345L);
        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent1.setCreatedBy("createdBy");
        statehoodSubjectRepresent1.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent1.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject);
        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent = Optional.of(statehoodSubjectRepresent1);
        when(mockStatehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(anyLong(), anyLong())).thenReturn(statehoodSubjectRepresent);

        // Run the test
        final Boolean result = statehoodSubjectRepresentServiceImplUnderTest.hasAccessByIds(0L, 0L);

        // Verify the results
        assertTrue(result);
    }


    @Test
    void testChangeContextTo() {
        // Setup
        TestSecurityUtils.setSecurityContext(12345L);
        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent1.setCreatedBy("createdBy");
        statehoodSubjectRepresent1.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent1.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectStatus(UserStatus.ACTIVE);

        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject);
        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent = Optional.of(statehoodSubjectRepresent1);
        when(mockStatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(0L)).thenReturn(statehoodSubjectRepresent);

        // Configure StatehoodSubjectRepresentRepository.save(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent2 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent2.setId(0L);
        statehoodSubjectRepresent2.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent2.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent2.setCreatedBy("createdBy");
        statehoodSubjectRepresent2.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent2.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setStatehoodSubject(statehoodSubject1);
        when(mockStatehoodSubjectRepresentRepository.save(any(StatehoodSubjectRepresent.class))).thenReturn(statehoodSubjectRepresent2);

        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent4 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent4.setId(0L);
        statehoodSubjectRepresent4.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent4.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent4.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent4.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent4.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent4.setCreatedBy("createdBy");
        statehoodSubjectRepresent4.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent4.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject2 = new StatehoodSubject();
        statehoodSubject2.setId(0L);
        statehoodSubject2.setSubjectStatus(UserStatus.ACTIVE);

        statehoodSubject2.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject2.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject2.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent4.setStatehoodSubject(statehoodSubject2);
        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent3 = Optional.of(statehoodSubjectRepresent4);
        when(mockStatehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(anyLong(), anyLong())).thenReturn(statehoodSubjectRepresent3);

        // Run the test
        statehoodSubjectRepresentServiceImplUnderTest.changeContextTo(0L);

        // Verify the results
    }


    @Test
    void testFindAllRepresentersBySubjectId() {
        // Setup
        TestSecurityUtils.setSecurityContext(12345L);
        final RepresentCriteria criteria = new RepresentCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(12345L);
        criteria.setSubjectId(longFilter);

        criteria.setFullName(new StringFilter());
        criteria.setType(new RepresentCriteria.TypeFilter());
        criteria.setStatus(new RepresentCriteria.StatusFilter());

        // Configure StatehoodSubjectRepresentCheckService.tryToGetAndCheckRepresent(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent.setId(0L);
        statehoodSubjectRepresent.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.INACTIVE);
        statehoodSubjectRepresent.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent.setCreatedBy("createdBy");
        statehoodSubjectRepresent.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setStatehoodSubject(statehoodSubject);
        when(mockStatehoodSubjectRepresentLookupService.tryToGetActiveRepresent(anyLong(), anyLong())).thenReturn(statehoodSubjectRepresent);


        // Configure StatehoodSubjectRepresentRepository.findAll(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent1.setCreatedBy("createdBy");
        statehoodSubjectRepresent1.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent1.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject1);
        final Page<StatehoodSubjectRepresent> statehoodSubjectRepresents = new PageImpl<>(List.of(statehoodSubjectRepresent1));
        when(mockStatehoodSubjectRepresentRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(statehoodSubjectRepresents);

        // Configure StatehoodSubjectRepresentMapper.toMainInfoDto(...).
        final StatehoodSubjectRepresentMainInfoDTO statehoodSubjectRepresentMainInfoDTO = new StatehoodSubjectRepresentMainInfoDTO(0L, StatehoodSubjectRepresentStatus.ACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "subjectStatus", AccountDetailsStatus.NOT_FULL, 0L, "statehoodSubjectFullName", "statehoodSubjectEdrpou", 0L, "declarantFirstName", "declarantLastName", "declarantMiddleName", "declarantFullName");
        when(mockStatehoodSubjectRepresentMapper.toMainInfoDto(any(StatehoodSubjectRepresent.class))).thenReturn(statehoodSubjectRepresentMainInfoDTO);

        // Run the test
        final Page<StatehoodSubjectRepresentMainInfoDTO> result = statehoodSubjectRepresentServiceImplUnderTest.findAllRepresentersBySubjectId(PageRequest.of(0, 1), criteria);


    }


    @Test
    void testGetCurrentContextMainDTO() {
        // Setup
        final StatehoodSubjectRepresentContextDTO expectedResult = new StatehoodSubjectRepresentContextDTO("fullName", null, null, 0L);

        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent1.setCreatedBy("createdBy");
        statehoodSubjectRepresent1.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent1.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectName("fullName");
        statehoodSubject.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject.setSubjectCode("subjectCode");
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.FULL_CONFIRMED);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject);
        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent = Optional.of(statehoodSubjectRepresent1);
        when(mockStatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(anyLong())).thenReturn(statehoodSubjectRepresent);

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
        user.setEdrpouCode("subjectCode");
        user.setFullName("fullName");
        user.setStatus(UserStatus.ACTIVE);
        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Run the test
        final StatehoodSubjectRepresentContextDTO result = statehoodSubjectRepresentServiceImplUnderTest.getCurrentContextMainDTO();

        // Verify the results
        assertEquals(expectedResult, result);
    }


    @Test
    void testGetCurrentContextMainDTO2() {
        // Setup
        TestSecurityUtils.setSecurityContext(12345L);
        final StatehoodSubjectRepresentContextDTO expectedResult = new StatehoodSubjectRepresentContextDTO("fullName", "subjectCode", 0L, 12345L);

        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent1.setCreatedBy("createdBy");
        statehoodSubjectRepresent1.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent1.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectName("fullName");
        statehoodSubject.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject.setSubjectCode("subjectCode");
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.FULL_CONFIRMED);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject);
        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent = Optional.of(statehoodSubjectRepresent1);
        when(mockStatehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(anyLong())).thenReturn(statehoodSubjectRepresent);

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
        user.setEdrpouCode("subjectCode");
        user.setFullName("fullName");
        user.setStatus(UserStatus.ACTIVE);
        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Run the test
        final StatehoodSubjectRepresentContextDTO result = statehoodSubjectRepresentServiceImplUnderTest.getCurrentContextMainDTO();

        // Verify the results
        assertEquals(expectedResult, result);
    }


    @Test
    void testRemoveRepresent() {
        // Setup
        TestSecurityUtils.setSecurityContext(12345L);
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO.setCode("code");
        privilegeGroupGeneralDTO.setGlobal(false);
        privilegeGroupGeneralDTO.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO.setFullNameEng("fullNameEng");
        final StatehoodSubjectRepresentDTO expectedResult = new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO));

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
        user.setStatus(UserStatus.ACTIVE);

        // Configure StatehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent1 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent1.setId(0L);
        statehoodSubjectRepresent1.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent1.setCreatedBy("createdBy");
        statehoodSubjectRepresent1.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent1.setDeletedBy("deletedBy");
        statehoodSubjectRepresent1.setDeclarant(user);
        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject);
        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent = Optional.of(statehoodSubjectRepresent1);
        when(mockStatehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(anyLong(), anyLong())).thenReturn(statehoodSubjectRepresent);

        // Configure StatehoodSubjectRepresentCheckService.tryToGetAndCheckRepresent(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent2 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent2.setId(0L);
        statehoodSubjectRepresent2.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent2.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent2.setCreatedBy("createdBy");
        statehoodSubjectRepresent2.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent2.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent2.setStatehoodSubject(statehoodSubject1);
        when(mockStatehoodSubjectRepresentLookupService.tryToGetActiveRepresent(anyLong(), anyLong())).thenReturn(statehoodSubjectRepresent2);

        // Configure StatehoodSubjectRepresentRepository.save(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent3 = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent3.setId(0L);
        statehoodSubjectRepresent3.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent3.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent3.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent3.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent3.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent3.setCreatedBy("createdBy");
        statehoodSubjectRepresent3.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent3.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject2 = new StatehoodSubject();
        statehoodSubject2.setId(0L);
        statehoodSubject2.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject2.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject2.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject2.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent3.setStatehoodSubject(statehoodSubject2);
        when(mockStatehoodSubjectRepresentRepository.save(any(StatehoodSubjectRepresent.class))).thenReturn(statehoodSubjectRepresent3);

        // Configure StatehoodSubjectRepresentMapper.toDto(...).
        final PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO1 = new PrivilegeGroupGeneralDTO();
        privilegeGroupGeneralDTO1.setCode("code");
        privilegeGroupGeneralDTO1.setGlobal(false);
        privilegeGroupGeneralDTO1.setStatus(ActiveStatus.ACTIVE);
        privilegeGroupGeneralDTO1.setFullNameUkr("fullNameUkr");
        privilegeGroupGeneralDTO1.setFullNameEng("fullNameEng");
        final StatehoodSubjectRepresentDTO statehoodSubjectRepresentDTO = new StatehoodSubjectRepresentDTO(0L, StatehoodSubjectRepresentStatus.ACTIVE, LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC), StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "createdBy", "updatedBy", "deletedBy", 0L, 0L, "declarantLogin", false, List.of(privilegeGroupGeneralDTO1));
        when(mockStatehoodSubjectRepresentMapper.toDto(any(StatehoodSubjectRepresent.class))).thenReturn(statehoodSubjectRepresentDTO);

        // Run the test
        final StatehoodSubjectRepresentDTO result = statehoodSubjectRepresentServiceImplUnderTest.removeRepresent(0L, 0L);

        // Verify the results
        assertEquals(result.getSubjectRepresentStatus(), StatehoodSubjectRepresentStatus.ACTIVE);
    }


    @Test
    void testGetAllActiveByCurrentDeclarant() {
        TestSecurityUtils.setSecurityContext(12345L);
        // Setup
        final List<StatehoodSubjectRepresentMainInfoDTO> expectedResult = List.of(new StatehoodSubjectRepresentMainInfoDTO(0L, StatehoodSubjectRepresentStatus.ACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "subjectStatus", AccountDetailsStatus.NOT_FULL, 0L, "statehoodSubjectFullName", "statehoodSubjectEdrpou", 0L, "declarantFirstName", "declarantLastName", "declarantMiddleName", "declarantFullName"));

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
        user.setStatus(UserStatus.ACTIVE);
        when(mockUserService.getCurrentUser()).thenReturn(user);

        // Configure StatehoodSubjectRepresentRepository.findAllByDeclarant_IdAndSubjectRepresentStatus(...).
        final StatehoodSubjectRepresent statehoodSubjectRepresent = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent.setId(0L);
        statehoodSubjectRepresent.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        statehoodSubjectRepresent.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent.setCreatedBy("createdBy");
        statehoodSubjectRepresent.setUpdatedBy("updatedBy");
        statehoodSubjectRepresent.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent.setStatehoodSubject(statehoodSubject);
        final List<StatehoodSubjectRepresent> statehoodSubjectRepresents = List.of(statehoodSubjectRepresent);
        when(mockStatehoodSubjectRepresentRepository.findAllByDeclarant_IdAndSubjectRepresentStatus(anyLong(), any(StatehoodSubjectRepresentStatus.class))).thenReturn(statehoodSubjectRepresents);

        // Configure StatehoodSubjectRepresentMapper.toMainInfoDto(...).
        final List<StatehoodSubjectRepresentMainInfoDTO> statehoodSubjectRepresentMainInfoDTOS = List.of(new StatehoodSubjectRepresentMainInfoDTO(0L, StatehoodSubjectRepresentStatus.INACTIVE, StatehoodSubjectRepresentType.CHIEF_EXECUTIVE, "subjectStatus", AccountDetailsStatus.NOT_FULL, 0L, "statehoodSubjectFullName", "statehoodSubjectEdrpou", 0L, "declarantFirstName", "declarantLastName", "declarantMiddleName", "declarantFullName"));
        when(mockStatehoodSubjectRepresentMapper.toMainInfoDto(anyList())).thenReturn(statehoodSubjectRepresentMainInfoDTOS);

        // Run the test
        final List<StatehoodSubjectRepresentMainInfoDTO> result = statehoodSubjectRepresentServiceImplUnderTest.getAllActiveByCurrentDeclarant();

        // Verify the results
        assertThat(result.size()).isGreaterThan(0);
    }
}
