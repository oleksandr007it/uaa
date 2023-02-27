package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.CEADepartment;
import com.idevhub.tapas.domain.CentralExecutiveAuthority;
import com.idevhub.tapas.service.dto.CEADepartmentDTO;
import com.idevhub.tapas.service.dto.CentralExecutiveAuthorityDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CentralExecutiveAuthority.
 */
public interface CentralExecutiveAuthorityService {

    /**
     * Save a centralExecutiveAuthority.
     *
     * @param centralExecutiveAuthorityDTO the entity to save
     * @return the persisted entity
     */
    CentralExecutiveAuthorityDTO save(CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO);

    /**
     * Get all the centralExecutiveAuthorities.
     *
     * @return the list of entities
     */
    List<CentralExecutiveAuthorityDTO> findAll();


    /**
     * Get the "id" centralExecutiveAuthority.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CentralExecutiveAuthorityDTO> findOne(Long id);

    /**
     * Delete the "id" centralExecutiveAuthority.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    CentralExecutiveAuthority getByCode(String edrpou);

    List<CEADepartmentDTO> getAllActiveDepartments(String edrpou);

    CEADepartment getDepartment(String edrpou, Long departmentId);

}
