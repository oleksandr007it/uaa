package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.CEADepartment;
import com.idevhub.tapas.domain.CentralExecutiveAuthority;
import com.idevhub.tapas.domain.enumeration.CEADepartmentStatus;
import com.idevhub.tapas.domain.enumeration.CentralExecutiveAuthorityStatus;
import com.idevhub.tapas.repository.CEADepartmentRepository;
import com.idevhub.tapas.repository.CentralExecutiveAuthorityRepository;
import com.idevhub.tapas.service.CentralExecutiveAuthorityService;
import com.idevhub.tapas.service.dto.CEADepartmentDTO;
import com.idevhub.tapas.service.dto.CentralExecutiveAuthorityDTO;
import com.idevhub.tapas.service.errors.EntityNotFoundException;
import com.idevhub.tapas.service.mapper.CEADepartmentMapper;
import com.idevhub.tapas.service.mapper.CentralExecutiveAuthorityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CentralExecutiveAuthority.
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CentralExecutiveAuthorityServiceImpl implements CentralExecutiveAuthorityService {

    private final CentralExecutiveAuthorityRepository centralExecutiveAuthorityRepository;
    private final CEADepartmentRepository departmentRepository;
    private final CentralExecutiveAuthorityMapper centralExecutiveAuthorityMapper;
    private final CEADepartmentMapper ceaDepartmentMapper;

    /**
     * Save a centralExecutiveAuthority.
     *
     * @param centralExecutiveAuthorityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CentralExecutiveAuthorityDTO save(CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO) {
        log.debug("Request to save CentralExecutiveAuthority : {}", centralExecutiveAuthorityDTO);

        CentralExecutiveAuthority centralExecutiveAuthority = centralExecutiveAuthorityMapper.toEntity(centralExecutiveAuthorityDTO);
        centralExecutiveAuthority = centralExecutiveAuthorityRepository.save(centralExecutiveAuthority);

        return centralExecutiveAuthorityMapper.toDto(centralExecutiveAuthority);
    }

    /**
     * Get all the centralExecutiveAuthorities.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CentralExecutiveAuthorityDTO> findAll() {
        log.debug("Request to get all CentralExecutiveAuthorities");

        return centralExecutiveAuthorityRepository.findAll().stream()
            .map(centralExecutiveAuthorityMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one centralExecutiveAuthority by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CentralExecutiveAuthorityDTO> findOne(Long id) {
        log.debug("Request to get CentralExecutiveAuthority : {}", id);

        return centralExecutiveAuthorityRepository.findById(id)
            .map(centralExecutiveAuthorityMapper::toDto);
    }

    /**
     * Delete the centralExecutiveAuthority by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CentralExecutiveAuthority : {}", id);

        centralExecutiveAuthorityRepository.deleteById(id);
    }

    @Override
    public CentralExecutiveAuthority getByCode(String edrpou) {
        log.debug("Request to get CentralExecutiveAuthority : {}", edrpou);
        return centralExecutiveAuthorityRepository.findByCode(edrpou)
            .orElseThrow(() -> new EntityNotFoundException("error.cea.not.found.by.code", edrpou));
    }

    @Override
    public List<CEADepartmentDTO> getAllActiveDepartments(String edrpou) {
        log.debug("Trying to get department list of cea (code = {})", edrpou);
        return departmentRepository.findAllCeaDepartments(edrpou).stream()
            .filter(activeDepartmentPredicate())
            .map(ceaDepartmentMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public CEADepartment getDepartment(String edrpou, Long departmentId) {
        log.debug("Trying to get cea department by cea code = {} and department id = {}", edrpou, departmentId);
        return departmentRepository.findCeaDepartmentById(edrpou, departmentId)
            .orElseThrow(() -> new EntityNotFoundException("error.cea.department.not.found.by.id.and.code", edrpou, departmentId.toString()));
    }

    private Predicate<CEADepartment> activeDepartmentPredicate() {
        return department ->
            department.getCentralExecutiveAuthority().getCentralExecutiveAuthorityStatus() == CentralExecutiveAuthorityStatus.ACTIVE &&
                department.getCeaDepartmentStatus() == CEADepartmentStatus.ACTIVE;
    }
}
