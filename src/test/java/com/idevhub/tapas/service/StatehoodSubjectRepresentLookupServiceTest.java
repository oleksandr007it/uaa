package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import com.idevhub.tapas.repository.StatehoodSubjectRepresentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class StatehoodSubjectRepresentLookupServiceTest {

    @Mock
    private StatehoodSubjectRepresentRepository mockRepresentRepository;

    private StatehoodSubjectRepresentLookupService statehoodSubjectRepresentLookupServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        statehoodSubjectRepresentLookupServiceUnderTest = new StatehoodSubjectRepresentLookupService(mockRepresentRepository);
    }

    @Test
    void testTryToGetAndCheckRepresent1() {
        // Setup
        final StatehoodSubjectRepresent expectedResult = new StatehoodSubjectRepresent();
        expectedResult.setId(0L);
        expectedResult.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        expectedResult.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        expectedResult.setCreatedBy("createdBy");
        expectedResult.setUpdatedBy("updatedBy");
        expectedResult.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectStatus("subjectStatus");
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setStatehoodSubject(statehoodSubject);

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
        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject1);
        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent = Optional.of(statehoodSubjectRepresent1);
        when(mockRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(anyLong())).thenReturn(statehoodSubjectRepresent);

        // Run the test
        final StatehoodSubjectRepresent result = statehoodSubjectRepresentLookupServiceUnderTest.tryToGetActiveRepresent(0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }



    @Test
    void testTryToGetAndCheckRepresent2() {
        // Setup
        final StatehoodSubjectRepresent expectedResult = new StatehoodSubjectRepresent();
        expectedResult.setId(0L);
        expectedResult.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);
        expectedResult.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setDeletedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        expectedResult.setCreatedBy("createdBy");
        expectedResult.setUpdatedBy("updatedBy");
        expectedResult.setDeletedBy("deletedBy");
        final StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(0L);
        statehoodSubject.setSubjectStatus("subjectStatus");
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        expectedResult.setStatehoodSubject(statehoodSubject);

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
        final StatehoodSubject statehoodSubject1 = new StatehoodSubject();
        statehoodSubject1.setId(0L);
        statehoodSubject1.setSubjectStatus("subjectStatus");
        statehoodSubject1.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject1.setCreatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubject1.setUpdatedAt(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0).toInstant(ZoneOffset.UTC));
        statehoodSubjectRepresent1.setStatehoodSubject(statehoodSubject1);
        final Optional<StatehoodSubjectRepresent> statehoodSubjectRepresent = Optional.of(statehoodSubjectRepresent1);
        when(mockRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(anyLong(), anyLong())).thenReturn(statehoodSubjectRepresent);

        // Run the test
        final StatehoodSubjectRepresent result = statehoodSubjectRepresentLookupServiceUnderTest.tryToGetActiveRepresent(0L, 0L);

        // Verify the results
        assertEquals(expectedResult, result);
    }


}
