package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.CEADepartment;
import com.idevhub.tapas.repository.CEADepartmentRepository;
import com.idevhub.tapas.service.CEADepartmentService;
import com.idevhub.tapas.service.dto.CEADepartmentDTO;
import com.idevhub.tapas.service.mapper.CEADepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CEADepartment.
 */
@Service
@Transactional
public class CEADepartmentServiceImpl implements CEADepartmentService {

    private final Logger log = LoggerFactory.getLogger(CEADepartmentServiceImpl.class);

    private final CEADepartmentRepository ceaDepartmentRepository;

    private final CEADepartmentMapper ceaDepartmentMapper;

    public CEADepartmentServiceImpl(CEADepartmentRepository ceaDepartmentRepository,
                                    CEADepartmentMapper ceaDepartmentMapper) {

        this.ceaDepartmentRepository = ceaDepartmentRepository;
        this.ceaDepartmentMapper = ceaDepartmentMapper;
    }

    /**
     * Save a ceaDepartment.
     *
     * @param ceaDepartmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CEADepartmentDTO save(CEADepartmentDTO ceaDepartmentDTO) {
        log.debug("Request to save CEADepartment : {}", ceaDepartmentDTO);
        CEADepartment ceaDepartment = ceaDepartmentMapper.toEntity(ceaDepartmentDTO);
        ceaDepartment = ceaDepartmentRepository.save(ceaDepartment);
        return ceaDepartmentMapper.toDto(ceaDepartment);
    }

    /**
     * Get all the ceaDepartments.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CEADepartmentDTO> findAll() {
        log.debug("Request to get all CEADepartments");
        return ceaDepartmentRepository.findAll().stream()
            .map(ceaDepartmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one ceaDepartment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CEADepartmentDTO> findOne(Long id) {
        log.debug("Request to get CEADepartment : {}", id);
        return ceaDepartmentRepository.findById(id)
            .map(ceaDepartmentMapper::toDto);
    }

    /**
     * Delete the ceaDepartment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CEADepartment : {}", id);
        ceaDepartmentRepository.deleteById(id);
    }
}
