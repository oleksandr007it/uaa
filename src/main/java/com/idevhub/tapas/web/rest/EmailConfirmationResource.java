package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.service.EmailConfirmationService;
import com.idevhub.tapas.service.dto.EmailConfirmationDTO;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EmailConfirmation.
 */
@RestController
@RequestMapping("/api")
public class EmailConfirmationResource {

    private static final String ENTITY_NAME = "emailConfirmation";
    private final Logger log = LoggerFactory.getLogger(EmailConfirmationResource.class);
    private final EmailConfirmationService emailConfirmationService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public EmailConfirmationResource(EmailConfirmationService emailConfirmationService) {
        this.emailConfirmationService = emailConfirmationService;
    }

    @PostMapping("/email-confirmations/confirm")
    @Timed
    public EmailConfirmationDTO confirmEmail(@RequestParam("confirmationId") String confirmationId,
                                             @RequestParam("email") String email,
                                             @RequestParam("ipAddress") String ipAddress) {
        log.debug("REST request to confirm Email where confirmationId : {} and email(URLEncoded) : {}", confirmationId, email);

        EmailConfirmationDTO result = emailConfirmationService.confirmEmail(Long.valueOf(confirmationId), email, ipAddress);

        return result;
    }

    @GetMapping("/email-confirmations")
    @Timed
    public List<EmailConfirmationDTO> getAllEmailConfirmations() {
        log.debug("REST request to get all EmailConfirmations");
        return emailConfirmationService.findAllActive();
    }

    @GetMapping("/email-confirmations/{id}")
    @Timed
    public ResponseEntity<EmailConfirmationDTO> getEmailConfirmation(@PathVariable Long id) {
        log.debug("REST request to get EmailConfirmation : {}", id);
        Optional<EmailConfirmationDTO> emailConfirmationDTO = emailConfirmationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailConfirmationDTO);
    }

    @DeleteMapping("/email-confirmations/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmailConfirmation(@PathVariable Long id) {
        log.debug("REST request to delete EmailConfirmation : {}", id);

        emailConfirmationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
