package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.Notification;
import com.idevhub.tapas.repository.NotificationRepository;
import com.idevhub.tapas.service.dto.RequestApproveOrReissueDTO;
import com.idevhub.tapas.service.dto.RequestSuspenseOrRevocationDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectDTO;
import com.idevhub.tapas.service.impl.StatehoodSubjectServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Notification}.
 */
@Service
@Transactional
public class NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    private final MessageSource messageSource;

    private final StatehoodSubjectServiceImpl statehoodSubjectService;

    private final SpringTemplateEngine templateEngine;

    private final MailService mailService;

    public NotificationService(NotificationRepository notificationRepository, MessageSource messageSource, StatehoodSubjectServiceImpl statehoodSubjectService, SpringTemplateEngine templateEngine, MailService mailService) {
        this.notificationRepository = notificationRepository;
        this.messageSource = messageSource;
        this.statehoodSubjectService = statehoodSubjectService;

        this.templateEngine = templateEngine;
        this.mailService = mailService;
    }

    /**
     * Save a notification.
     *
     * @param notification the entity to save.
     * @return the persisted entity.
     */
    public Notification save(Notification notification) {
        log.debug("Request to save Notification : {}", notification);
        return notificationRepository.save(notification);
    }

    /**
     * Get all the notifications.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Notification> findAll() {
        log.debug("Request to get all Notifications");
        return notificationRepository.findAll();
    }


    // @Scheduled(cron = "0 0 9 ? * MON-FRI")
   // @Scheduled(cron = "0 0/10 * * * ?")
    public void sendAndDelete() {
        Instant before = Instant.now().minus(5L, ChronoUnit.DAYS);
        List<Notification> list = notificationRepository.findAllByCreationDateBefore(before);
        log.info("sendMail  - " + System.currentTimeMillis() / 1000);
        for (Notification n : list) {
            mailService.sendEmail(n.getAddress(), n.getSubject(), n.getContent(), false, true);
            delete(n.getId());
        }
    }


//    @Scheduled(cron = "0 0/10 * * * ?")
//    public void send() {
//        List<Notification> list = notificationRepository.findAll();
//        log.info("sendMail  - " + System.currentTimeMillis() / 1000);
//        for (Notification n : list) {
//            mailService.sendEmail(n.getAddress(), n.getSubject(), n.getContent(), false, true);
//            delete(n.getId());
//        }
//    }


    public void saveAproveEmail(RequestApproveOrReissueDTO input, String titleKey, String typeKey) {

        Locale locale = Locale.forLanguageTag("ua");
        Context context = new Context(locale);
        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectService.findOne(input.getStatehoodSubjectId());
        String title = messageSource.getMessage(titleKey, null, locale);
        String type = messageSource.getMessage(typeKey, null, locale);

        HashMap<String, Object> additionalVariables = new HashMap<>();
        additionalVariables.put("title", title);
        additionalVariables.put("type", type);
        additionalVariables.put("subjectFullName", statehoodSubjectDTO.getSubjectName());
        additionalVariables.put("subjectFullNameFromRegister", input.getSubjectFullNameFromRegister());
        additionalVariables.put("edrpouFromRegister", input.getEdrpouFromRegister());
        additionalVariables.put("legalAddressFromRegister", input.getLegalAddressFromRegister());
        additionalVariables.put("brokerRegNumber", input.getBrokerRegNumber());
        additionalVariables.put("recordingToRegisterDate", input.getRecordingToRegisterDate());

        context.setVariables(additionalVariables);
        String content = templateEngine.process("mail/" + "businessSubjectApplicationAprAnswerEmail", context);
        String subject = messageSource.getMessage("email.applications.approve.subject", null, locale);

        saveEmailNotification(content, subject, statehoodSubjectDTO.getEmail());
    }


    public void saveSuspenseEmail(RequestSuspenseOrRevocationDTO input, String titleKey, String typeKey) {

        Locale locale = Locale.forLanguageTag("ua");
        Context context = new Context(locale);

        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectService.findOne(input.getStatehoodSubjectId());
        String title = messageSource.getMessage(titleKey, null, locale);
        String type = messageSource.getMessage(typeKey, null, locale);

        HashMap<String, Object> additionalVariables = new HashMap<>();
        additionalVariables.put("title", title);
        additionalVariables.put("type", type);
        additionalVariables.put("subjectFullName", statehoodSubjectDTO.getSubjectName());


        context.setVariables(additionalVariables);
        String content = templateEngine.process("mail/" + "businessSubjectApplicationSuspnAnswerEmail", context);
        String subject = messageSource.getMessage("email.applications.suspense.subject", null, locale);

        saveEmailNotification(content, subject, statehoodSubjectDTO.getEmail());
    }


    private void saveEmailNotification(String content, String subject, String email) {

        Notification notification = new Notification();
        notification.setAddress(email);
        notification.setContent(content);
        notification.setSubject(subject);
        notification.setCreationDate(Instant.now());
        notification.setType("EMAIL");
        save(notification);
    }

    /**
     * Get one notification by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Notification> findOne(Long id) {
        log.debug("Request to get Notification : {}", id);
        return notificationRepository.findById(id);
    }

    /**
     * Delete the notification by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Notification : {}", id);

        notificationRepository.deleteById(id);
    }
}
