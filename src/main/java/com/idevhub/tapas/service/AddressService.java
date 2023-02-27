package com.idevhub.tapas.service;

import com.idevhub.tapas.service.dto.AddressDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Address.
 */
public interface AddressService {

    /**
     * Get all the addresses.
     *
     * @return the list of entities
     */
    List<AddressDTO> findAll();


    /**
     * Get the "id" address.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AddressDTO> findOne(String id);

}
