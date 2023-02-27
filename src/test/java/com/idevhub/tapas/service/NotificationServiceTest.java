package com.idevhub.tapas.service;

import com.idevhub.tapas.UaaApp;
import com.idevhub.tapas.domain.Notification;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.repository.NotificationRepository;
import com.idevhub.tapas.service.dto.RequestApproveOrReissueDTO;
import com.idevhub.tapas.service.dto.RequestSuspenseOrRevocationDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectDTO;
import com.idevhub.tapas.service.impl.StatehoodSubjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


@SpringBootTest(classes = UaaApp.class)
class NotificationServiceTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Mock
    private MessageSource mockMessageSource;
    @Mock
    private StatehoodSubjectServiceImpl mockStatehoodSubjectService;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Mock
    private MailService mockMailService;

    private NotificationService notificationServiceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        notificationServiceUnderTest = new NotificationService(notificationRepository, mockMessageSource, mockStatehoodSubjectService, templateEngine, mockMailService);
    }


    @Test
    void testSaveAproveEmail() {
        // Setup
        final RequestApproveOrReissueDTO input = new RequestApproveOrReissueDTO(0L, 0L, "subjectFullNameFromRegister", "edrpouFromRegister", "legalAddressFromRegister", "brokerRegNumber", "recordingToRegisterDate");

        // Configure StatehoodSubjectServiceImpl.findOne(...).
        final StatehoodSubjectDTO statehoodSubjectDTO = new StatehoodSubjectDTO();
        statehoodSubjectDTO.setId(0L);
        statehoodSubjectDTO.setSubjectStatus("subjectStatus");
        statehoodSubjectDTO.setAccountDetailsStatus(AccountDetailsStatus.FULL_CONFIRMED);
        statehoodSubjectDTO.setCreatedAt(Instant.now());
        statehoodSubjectDTO.setUpdatedAt(Instant.now());
        statehoodSubjectDTO.setDeletedAt(Instant.now());
        statehoodSubjectDTO.setSubjectCode("subjectCode");
        statehoodSubjectDTO.setSubjectName("subjectName");
        statehoodSubjectDTO.setSubjectShortName("subjectShortName");
        statehoodSubjectDTO.setHeadFullName("headFullName");
        when(mockStatehoodSubjectService.findOne(any(Long.class))).thenReturn(statehoodSubjectDTO);
        when(mockMessageSource.getMessage(any(String.class), any(Object[].class), any(Locale.class))).thenReturn("result");
//        when(templateEngine.process(eq("mail/" + "businessSubjectApplicationAprAnswerEmail"), any(Context.class))).thenReturn("body mail");

        //someMethod(anyObject(), eq("String by matcher"));

        final Notification notification = new Notification();
        notification.setId(0L);
        notification.content("content");
        notification.setContent("content");
        notification.subject("subject");
        notification.setSubject("subject");
        notification.address("address");
        notification.setAddress("address");
        notification.type("type");
        notification.setType("type");
        notification.creationDate(Instant.now());

        // Run the test
        notificationServiceUnderTest.saveAproveEmail(input, "titleKey", "typeKey");
        final RequestSuspenseOrRevocationDTO input2 = new RequestSuspenseOrRevocationDTO(0L, 0L);
        notificationServiceUnderTest.saveSuspenseEmail(input2, "titleKey", "typeKey");
        // Verify the results
    }

    @Test
    void sendAndDelete() {
        final Notification notification = new Notification();
        notification.setId(0L);
        notification.content("content");
        notification.setContent("content");
        notification.subject("subject");
        notification.setSubject("subject");
        notification.address("address");
        notification.setAddress("address");
        notification.type("type");
        notification.setType("type");
        notification.creationDate(Instant.now().minus(3L, ChronoUnit.DAYS));

        notificationRepository.save(notification);
        final Notification notification2 = new Notification();
        notification.setId(1L);
        notification.content("content");
        notification.setContent("content");
        notification.subject("subject");
        notification.setSubject("subject");
        notification.address("address");
        notification.setAddress("address");
        notification.type("type");
        notification.setType("type");
        notification.creationDate(Instant.now().minus(2L, ChronoUnit.DAYS));
        notificationRepository.save(notification2);
        final Notification notification3 = new Notification();
        notification.setId(1L);
        notification.content("content");
        notification.setContent("content");
        notification.subject("subject");
        notification.setSubject("subject");
        notification.address("address");
        notification.setAddress("address");
        notification.type("type");
        notification.setType("type");
        notification.creationDate(Instant.now());
        notificationRepository.save(notification3);
        doNothing().when(mockMailService).sendEmail(any(String.class), any(String.class), any(String.class), eq(false), eq(true));
        int count = notificationRepository.findAll().size();
        assertThat(count == 3);
        notificationServiceUnderTest.sendAndDelete();
        count = notificationRepository.findAll().size();
        assertThat(count == 1);
    }

}
