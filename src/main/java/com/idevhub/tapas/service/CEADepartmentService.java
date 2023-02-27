package com.idevhub.tapas.service;

import com.idevhub.tapas.service.dto.CEADepartmentDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing CEADepartment.
 */
public interface CEADepartmentService {

    /**
     * Save a ceaDepartment.
     *
     * @param ceaDepartmentDTO the entity to save
     * @return the persisted entity
     */
    CEADepartmentDTO save(CEADepartmentDTO ceaDepartmentDTO);

    /**
     * Get all the CEADepartments.
     *
     * @return the list of entities
     */
    List<CEADepartmentDTO> findAll();


    /**
     * Get the "id" ceaDepartment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<CEADepartmentDTO> findOne(Long id);

    /**
     * Delete the "id" ceaDepartment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
