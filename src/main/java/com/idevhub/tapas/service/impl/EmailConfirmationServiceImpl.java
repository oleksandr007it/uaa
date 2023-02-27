package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.config.UaaProperties;
import com.idevhub.tapas.domain.EmailConfirmation;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.PropertyStatus;
import com.idevhub.tapas.domain.constants.RepoConst;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.domain.enumeration.EmailConfirmationStatus;
import com.idevhub.tapas.repository.EmailConfirmationRepository;
import com.idevhub.tapas.repository.StatehoodSubjectRepository;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.service.EmailConfirmationService;
import com.idevhub.tapas.service.dto.EmailConfirmationDTO;
import com.idevhub.tapas.service.errors.EmailConfirmationException;
import com.idevhub.tapas.service.mapper.EmailConfirmationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing EmailConfirmation.
 */
@Service
@Transactional
public class EmailConfirmationServiceImpl implements EmailConfirmationService {

    private final Logger log = LoggerFactory.getLogger(EmailConfirmationServiceImpl.class);

    private final EmailConfirmationRepository emailConfirmationRepository;
    private final EmailConfirmationMapper emailConfirmationMapper;
    private final UserRepository userRepository;
    private final StatehoodSubjectRepository statehoodSubjectRepository;
    private final CacheManager cacheManager;
    private final UaaProperties uaaProperties;

    public EmailConfirmationServiceImpl(EmailConfirmationRepository emailConfirmationRepository,
                                        EmailConfirmationMapper emailConfirmationMapper,
                                        UserRepository userRepository,
                                        StatehoodSubjectRepository statehoodSubjectRepository,
                                        CacheManager cacheManager, UaaProperties uaaProperties) {
        this.emailConfirmationRepository = emailConfirmationRepository;
        this.emailConfirmationMapper = emailConfirmationMapper;
        this.userRepository = userRepository;
        this.statehoodSubjectRepository = statehoodSubjectRepository;
        this.cacheManager = cacheManager;
        this.uaaProperties = uaaProperties;
    }

    /**
     * Save a emailConfirmation.
     *
     * @param emailConfirmationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EmailConfirmationDTO save(EmailConfirmationDTO emailConfirmationDTO) {
        log.debug("Request to save EmailConfirmation : {}", emailConfirmationDTO);
        EmailConfirmation emailConfirmation = emailConfirmationMapper.toEntity(emailConfirmationDTO);
        emailConfirmation = emailConfirmationRepository.save(emailConfirmation);
        return emailConfirmationMapper.toDto(emailConfirmation);
    }

    @Override
    public EmailConfirmation save(User user, String htmlTemplateName, String to, StatehoodSubject statehoodSubject) {
        log.debug("Request to save EmailConfirmation using User entity with id: {}", user.getId());
        EmailConfirmation emailConfirmation = emailConfirmationMapper.toEntity(user);

        int emailValidMinutes = uaaProperties.getMail().getEmailValidMinutes();
        Instant validUntil = Instant.now().plus(emailValidMinutes, ChronoUnit.MINUTES);

        emailConfirmation.setHtmlTemplateName(htmlTemplateName);
        emailConfirmation.setValidUntil(validUntil);

        if (statehoodSubject == null) {
            emailConfirmation.setEmail(to);
            emailConfirmation.setDeclarant(user);
        } else {
            emailConfirmation.setEmail(to);
            emailConfirmation.setStatehoodSubject(statehoodSubject);
        }

        emailConfirmation = emailConfirmationRepository.save(emailConfirmation);

        return emailConfirmation;
    }

    /**
     * Get all the emailConfirmations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EmailConfirmationDTO> findAllActive() {
        log.debug("Request to get all EmailConfirmations");
        return emailConfirmationRepository.findAllByConfirmationStatusIn(Collections.singletonList(EmailConfirmationStatus.ACTIVE)).stream()
            .map(emailConfirmationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one emailConfirmation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmailConfirmationDTO> findOne(Long id) {
        log.debug("Request to get EmailConfirmation : {}", id);
        return emailConfirmationRepository.findById(id)
            .map(emailConfirmationMapper::toDto);
    }

    /**
     * Delete the emailConfirmation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailConfirmation : {}", id);

        emailConfirmationRepository.deleteById(id);
    }

    @Override
    public EmailConfirmationDTO confirmEmail(Long confirmationId, String email, String ipAddress) {
        log.debug("Trying to confirm email:" + email + "with confirmation id: " + confirmationId);
        EmailConfirmation target = emailConfirmationRepository.findOneById(confirmationId)
            .orElseThrow(() -> new EmailConfirmationException("error.email.confirm.by.id.not.found"));

        if (target.getEmail().equals(email) && target.getConfirmationStatus() != EmailConfirmationStatus.ACTIVE)
            throw new EmailConfirmationException("error.email.confirm.already.used");

        if (Instant.now().compareTo(target.getValidUntil()) > 0)
            throw new EmailConfirmationException("error.email.confirm.link.not.valid");

        try {
            String emailToConfirm = URLDecoder.decode(email, StandardCharsets.UTF_8.toString());

            if (!emailToConfirm.equals(target.getEmail()))
                throw new EmailConfirmationException("error.email.confirm.wrong.email");

            target.setConfirmationStatus(EmailConfirmationStatus.CONFIRMED);
            target.setApprovedAt(Instant.now());
            target.setIpAddress(ipAddress);

            target = emailConfirmationRepository.save(target);

            StatehoodSubject subject = target.getStatehoodSubject();
            if (subject != null) {
                subject.setEmail(target.getEmail());
                subject.setEmailApproved(true);
                subject.setAccountDetailsStatus(AccountDetailsStatus.FULL_CONFIRMED);
                statehoodSubjectRepository.save(subject);
            } else {
                User declarant = target.getDeclarant();
                declarant.setEmail(target.getEmail());
                declarant.setEmailApprove(true);
                declarant.setPropertyStatus(PropertyStatus.CONFIRMED);
                User user = userRepository.save(declarant);
                this.clearUserCaches(user);
            }

            log.debug("Modified and saved EmailConfirmation with id: " + target.getId());

            return emailConfirmationMapper.toDto(target);
        } catch (UnsupportedEncodingException e) {
            log.error("Got an error during confirming email. err = " + e.getLocalizedMessage());
            throw new EmailConfirmationException("error.internal", e.getLocalizedMessage());
        }
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(RepoConst.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        Objects.requireNonNull(cacheManager.getCache(RepoConst.USERS_BY_ID_CACHE)).evict(user.getId());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(RepoConst.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }
}
