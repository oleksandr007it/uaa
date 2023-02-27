package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.domain.enumeration.EmailConfirmationStatus;
import com.idevhub.tapas.domain.enumeration.EmailRejectionReason;
import com.idevhub.tapas.service.EmailConfirmationService;
import com.idevhub.tapas.service.dto.EmailConfirmationDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class EmailConfirmationResourceTest {
    @Mock
    private EmailConfirmationService emailConfirmationService;

    private EmailConfirmationResource emailConfirmationResource;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        emailConfirmationResource = new EmailConfirmationResource(emailConfirmationService);
    }

    @Test
    public void testConfirmEmail() {
        final EmailConfirmationDTO actualResult = getStubEmailConfirmationDTO();

        when(emailConfirmationService.confirmEmail(any(Long.class), any(String.class), any(String.class))).thenReturn(actualResult);

        final EmailConfirmationDTO expectedResult = emailConfirmationResource.confirmEmail("12345", "http://test@mail.com", "192.168.0.1");

        assertEquals(actualResult, expectedResult);
        assertThat(expectedResult.getId()).isNotNull();
    }

    @Test
    public void testGetAllEmailConfirmations() {
        final List<EmailConfirmationDTO> actualResult = List.of(getStubEmailConfirmationDTO());

        when(emailConfirmationService.findAllActive()).thenReturn(actualResult);

        final List<EmailConfirmationDTO> expectedResult = emailConfirmationResource.getAllEmailConfirmations();

        assertEquals(actualResult, expectedResult);
        assertThat(expectedResult.get(0).getId()).isNotNull();
    }

    @Test
    public void testGetEmailConfirmation() {
        final EmailConfirmationDTO actualResult = getStubEmailConfirmationDTO();

        when(emailConfirmationService.findOne(anyLong())).thenReturn(Optional.of(actualResult));

        final EmailConfirmationDTO expectedResult = emailConfirmationResource.getEmailConfirmation(0L).getBody();

        assertEquals(actualResult, expectedResult);
        assertThat(expectedResult.getId()).isNotNull();
    }

    @Test
    public void testDeleteEmailConfirmation() {
        final ResponseEntity<Void> responseEntity = emailConfirmationResource.deleteEmailConfirmation(0L);

        verify(emailConfirmationService).delete(0L);
    }

    private EmailConfirmationDTO getStubEmailConfirmationDTO() {
        final EmailConfirmationDTO dto = new EmailConfirmationDTO();
        dto.setId(0L);
        dto.setConfirmationStatus(EmailConfirmationStatus.ACTIVE);
        dto.setCreatedAt(Instant.ofEpochSecond(0L));
        dto.setValidUntil(Instant.now().plus(10, ChronoUnit.DAYS));
        dto.setEmail("http://test@mail.com");
        dto.setHtmlTemplateName("htmlTemplateName");
        dto.setLangKey("en");
        dto.setRejectedAt(Instant.ofEpochSecond(0L));
        dto.setRejectDescription(EmailRejectionReason.EMAIL_NOT_VALID);
        dto.setApprovedAt(Instant.ofEpochSecond(0L));
        dto.setIpAddress("192.168.0.1");
        dto.setDeclarantId(0L);

        return dto;
    }
}
