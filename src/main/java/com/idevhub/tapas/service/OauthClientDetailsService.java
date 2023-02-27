package com.idevhub.tapas.service;


import com.idevhub.tapas.domain.OauthClientDetails;
import com.idevhub.tapas.repository.OauthClientDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing OauthClientDetails.
 */
@Service
@Transactional
public class OauthClientDetailsService {

    private final Logger log = LoggerFactory.getLogger(OauthClientDetailsService.class);

    private final OauthClientDetailsRepository oauthClientDetailsRepository;

    public OauthClientDetailsService(OauthClientDetailsRepository oauthClientDetailsRepository) {
        this.oauthClientDetailsRepository = oauthClientDetailsRepository;
    }

    /**
     * Save a oauthClientDetails.
     *
     * @param oauthClientDetails the entity to save
     * @return the persisted entity
     */
    public OauthClientDetails save(OauthClientDetails oauthClientDetails) {
        log.debug("Request to save OauthClientDetails : {}", oauthClientDetails);
        return oauthClientDetailsRepository.save(oauthClientDetails);
    }

    /**
     * Get all the oauthClientDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OauthClientDetails> findAll(Pageable pageable) {
        log.debug("Request to get all OauthClientDetails");
        return oauthClientDetailsRepository.findAll(pageable);
    }


}
