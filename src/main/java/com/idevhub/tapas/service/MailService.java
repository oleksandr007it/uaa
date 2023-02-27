package com.idevhub.tapas.service;

import com.idevhub.tapas.config.UaaProperties;
import com.idevhub.tapas.domain.EmailConfirmation;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.service.dto.RepresentativeInvitation;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.apache.commons.lang3.ArrayUtils.toArray;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private static final String USER = "user";
    private static final String BASE_URL = "baseUrl";
    private final Logger log = LoggerFactory.getLogger(MailService.class);
    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    private final UaaProperties uaaProperties;

    public MailService(JHipsterProperties jHipsterProperties,
                       JavaMailSender javaMailSender,
                       MessageSource messageSource,
                       SpringTemplateEngine templateEngine,
                       UaaProperties uaaProperties) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.uaaProperties = uaaProperties;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendEmailFromTemplate(String email,
                                      Map<String, Object> additionalVariables,
                                      String templateName,
                                      String titleKey,
                                      String langKey) {
        Locale locale = Locale.forLanguageTag(langKey);
        Context context = new Context(locale);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariables(additionalVariables);
        String content = templateEngine.process("mail/" + templateName, context);
        String subject = messageSource.getMessage(titleKey, toArray(additionalVariables.get("code")), locale);
        sendEmail(email, subject, content, true, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendUserEmailConfirmationEmail(Long confirmationId, User user, String htmlTemplateName) {
        log.debug("Sending emailConfirmation email of '{}'", user.getEmail());

        String confirmationUrl = uaaProperties.getMail().getConfirmationUrl() +
            "?confirmationId=" + confirmationId +
            "&email=" + getUrlEncodedEmail(user.getEmail());
        HashMap<String, Object> additionalVariables = new HashMap<>();
        additionalVariables.put("user", user);
        additionalVariables.put("confirmationText", confirmationUrl);
        additionalVariables.put("confirmationUrl", confirmationUrl);


        sendEmailFromTemplate(user.getEmail(),
            additionalVariables,
            htmlTemplateName,
            "email.user.confirm.title",
            user.getLangKey());
    }

    @Async
    public void sendStatehoodSubjectEmailConfirmationEmail(EmailConfirmation confirmation, String htmlTemplateName, String subjectName) {
        log.debug("Sending emailConfirmation email of '{}'", confirmation.getEmail());

        String confirmationUrl = uaaProperties.getMail().getConfirmationUrl() +
            "?confirmationId=" + confirmation.getId() +
            "&email=" + getUrlEncodedEmail(confirmation.getEmail());

        HashMap<String, Object> additionalVariables = new HashMap<>();
        additionalVariables.put("statehoodSubjectName", subjectName);
        additionalVariables.put("createdByFullName", confirmation.getCreatedBy().getFullName());
        additionalVariables.put("email", confirmation.getEmail());
        additionalVariables.put("confirmationText", confirmationUrl);
        additionalVariables.put("confirmationUrl", confirmationUrl);


        sendEmailFromTemplate(confirmation.getEmail(),
            additionalVariables,
            htmlTemplateName,
            "email.organization.confirm.title",
            confirmation.getLangKey());
    }

    @Async
    public void sendStatehoodSubjectRepresentInvitationEmail(RepresentativeInvitation invitation, String htmlTemplateName) {

        log.debug("Sending invitation to become a legal entity's representative of {} to {}, email: {}", invitation.getSubjectShortName(), invitation.getUserToInviteFullName(), invitation.getUserToInviteEmail());

        HashMap<String, Object> additionalVariables = new HashMap<>();
        additionalVariables.put("userToInviteFullName", invitation.getUserToInviteFullName());
        additionalVariables.put("invitedByFullName", invitation.getInvitedByFullName());
        additionalVariables.put("subjectCode", invitation.getSubjectCode());
        additionalVariables.put("subjectShortName", invitation.getSubjectShortName());
        additionalVariables.put("cabinetUrl", jHipsterProperties.getMail().getBaseUrl());

        sendEmailFromTemplate(invitation.getUserToInviteEmail(),
            additionalVariables,
            htmlTemplateName,
            "email.representative.invitation.title",
            invitation.getLangKey());
    }

    private String getUrlEncodedEmail(String email) {
        try {
            String urlEncodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString());
            return urlEncodedEmail;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
