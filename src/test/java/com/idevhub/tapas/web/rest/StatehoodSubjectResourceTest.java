package com.idevhub.tapas.web.rest;

import com.hazelcast.util.UuidUtil;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.repository.StatehoodSubjectRepository;
import com.idevhub.tapas.repository.StatehoodSubjectRepresentRepository;
import com.idevhub.tapas.service.StatehoodSubjectService;
import com.idevhub.tapas.service.criteria.StatehoodSubjectCriteria;
import com.idevhub.tapas.service.dto.StatehoodSubjectCreateDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectSendConfirmEmailDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class StatehoodSubjectResourceTest {
    @Mock
    private StatehoodSubjectService statehoodSubjectService;
    @Mock
    private StatehoodSubjectRepresentRepository representRepository;
    @Mock
    private StatehoodSubjectRepository statehoodSubjectRepository;

    private StatehoodSubjectResource statehoodSubjectResource;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        statehoodSubjectResource = new StatehoodSubjectResource(statehoodSubjectService);
    }

    @Test
    public void testCreateStatehoodSubject() throws URISyntaxException {
        final StatehoodSubjectDTO statehoodSubjectDTO = getStubStatehoodSubjectDTO();

        when(statehoodSubjectService.save(any(StatehoodSubjectCreateDTO.class))).thenReturn(statehoodSubjectDTO);

        final StatehoodSubjectDTO result = statehoodSubjectResource.createStatehoodSubject(getStubStatehoodSubjectCreateDTO()).getBody();

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isNotEmpty();
        assertThat(result.getTelNumber()).isNotEmpty();
    }

    @Test
    public void testUpdateStatehoodSubject() throws URISyntaxException {
        final StatehoodSubjectDTO actualResult = getStubStatehoodSubjectDTO();

        when(statehoodSubjectService.update(any(StatehoodSubjectDTO.class))).thenReturn(actualResult);

        final StatehoodSubjectDTO result = statehoodSubjectResource.updateStatehoodSubject(getStubStatehoodSubjectDTO()).getBody();

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isNotEmpty();
        assertThat(result.getTelNumber()).isNotEmpty();
    }

    @Test
    public void testSendConfirmationEmail() {
        final StatehoodSubjectSendConfirmEmailDTO statehoodSubjectSendConfirmEmailDTO = getStubStatehoodSubjectSendConfirmEmailDTO();

        doNothing().when(statehoodSubjectService).sendSubjectConfirmationEmail(statehoodSubjectSendConfirmEmailDTO);

        final ResponseEntity<Void> result = statehoodSubjectResource.sendConfirmationEmail(statehoodSubjectSendConfirmEmailDTO);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGetAllStatehoodSubjects() {
        final StatehoodSubjectDTO statehoodSubjectDTO = getStubStatehoodSubjectDTO();
        final Page<StatehoodSubjectDTO> page = new PageImpl<>(List.of(statehoodSubjectDTO));

        when(statehoodSubjectService.findAll(any(Pageable.class), any(StatehoodSubjectCriteria.class))).thenReturn(page);

        final MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        final PageRequest pageable = PageRequest.of(0, 1);
        final StatehoodSubjectCriteria criteria = new StatehoodSubjectCriteria();
        final ResponseEntity<List<StatehoodSubjectDTO>> result = statehoodSubjectResource.getAllStatehoodSubjects(pageable, criteria);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testGetStatehoodSubject() {
        final StatehoodSubjectDTO statehoodSubjectDTO = getStubStatehoodSubjectDTO();

        when(statehoodSubjectService.findOne(anyLong())).thenReturn(statehoodSubjectDTO);

        final ResponseEntity<StatehoodSubjectDTO> result = statehoodSubjectResource.getStatehoodSubject(1L);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testDeleteStatehoodSubject() {
        doNothing().when(statehoodSubjectService).delete(1L);

        final ResponseEntity<Void> result = statehoodSubjectResource.deleteStatehoodSubject(1L);

        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private StatehoodSubjectSendConfirmEmailDTO getStubStatehoodSubjectSendConfirmEmailDTO() {
        final StatehoodSubjectSendConfirmEmailDTO dto = new StatehoodSubjectSendConfirmEmailDTO();
        dto.setEmail("http://test@mail.com");
        dto.setStatehoodSubjectId(1L);

        return dto;
    }

    private StatehoodSubjectDTO getStubStatehoodSubjectDTO() {
        final StatehoodSubjectDTO dto = new StatehoodSubjectDTO();
        dto.setId(1L);
        dto.setSubjectStatus("subjectStatus");
        dto.setAccountDetailsStatus(AccountDetailsStatus.FULL_CONFIRMED);
        dto.setSubjectCode("subjectCode");
        dto.setSubjectName("subjectName");
        dto.setSubjectShortName("subjectShortName");
        dto.setEmail("http://test@mail.com");
        dto.setTelNumber("123456789");

        return dto;
    }

    private StatehoodSubjectCreateDTO getStubStatehoodSubjectCreateDTO() {
        final StatehoodSubjectCreateDTO dto = new StatehoodSubjectCreateDTO();
        dto.setSubjectStatus("subject_status");
        dto.setSubjectCode("123456789");
        dto.setSubjectShortName("subject_short_name");
        dto.setHeadFullName("head_full_name");
        dto.setTelNumber("123456789");
        dto.setEmail("http://test@mail.com");
        dto.setEori("eori");
        dto.setIsActualSameAsLegalAddress(true);
        dto.setKopfgId(1L);
        dto.setLegalAddressId(UuidUtil.newSecureUuidString());
        dto.setActualAddressId(UuidUtil.newSecureUuidString());

        return dto;
    }
}
