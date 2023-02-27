package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.EmailConfirmation;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.service.dto.EmailConfirmationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing EmailConfirmation.
 */
public interface EmailConfirmationService {

    /**
     * Save a emailConfirmation.
     *
     * @param emailConfirmationDTO the entity to save
     * @return the persisted entity
     */
    EmailConfirmationDTO save(EmailConfirmationDTO emailConfirmationDTO);

    EmailConfirmation save(User user, String htmlTemplateName, String to, StatehoodSubject statehoodSubject);

    /**
     * Get all the emailConfirmations.
     *
     * @return the list of entities
     */
    List<EmailConfirmationDTO> findAllActive();


    /**
     * Get the "id" emailConfirmation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EmailConfirmationDTO> findOne(Long id);

    /**
     * Delete the "id" emailConfirmation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    EmailConfirmationDTO confirmEmail(Long confirmationId, String email, String ipAddress);
}
