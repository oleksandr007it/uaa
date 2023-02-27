package com.idevhub.tapas.service;

import com.idevhub.tapas.service.dto.KopfgDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing kopfg.
 */
public interface KopfgService {

    /**
     * Save a kopfg.
     *
     * @param kopfgDTO the entity to save
     * @return the persisted entity
     */
    KopfgDTO save(KopfgDTO kopfgDTO);

    /**
     * Get all the kopfgS.
     *
     * @return the list of entities
     */
    List<KopfgDTO> findAll();


    /**
     * Get the "id" kopfg.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<KopfgDTO> findOne(Long id);

    /**
     * Delete the "id" kopfg.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
