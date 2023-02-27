package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.Kopfg;
import com.idevhub.tapas.repository.KopfgRepository;
import com.idevhub.tapas.service.KopfgService;
import com.idevhub.tapas.service.dto.KopfgDTO;
import com.idevhub.tapas.service.mapper.KopfgMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Kopfg.
 */
@Service
@Transactional
public class KopfgServiceImpl implements KopfgService {

    private final Logger log = LoggerFactory.getLogger(KopfgServiceImpl.class);

    private final KopfgRepository kopfgRepository;

    private final KopfgMapper kopfgMapper;

    public KopfgServiceImpl(KopfgRepository kopfgRepository, KopfgMapper kopfgMapper) {
        this.kopfgRepository = kopfgRepository;
        this.kopfgMapper = kopfgMapper;
    }

    /**
     * Save a kopfg.
     *
     * @param kopfgDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public KopfgDTO save(KopfgDTO kopfgDTO) {
        log.debug("Request to save Kopfg : {}", kopfgDTO);
        Kopfg kopfg = kopfgMapper.toEntity(kopfgDTO);
        kopfg = kopfgRepository.save(kopfg);
        return kopfgMapper.toDto(kopfg);
    }

    /**
     * Get all the kopfgS.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<KopfgDTO> findAll() {
        log.debug("Request to get all KopfgS");
        return kopfgRepository.findAll().stream()
            .map(kopfgMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one kopfg by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<KopfgDTO> findOne(Long id) {
        log.debug("Request to get KopfgG : {}", id);
        return kopfgRepository.findById(id)
            .map(kopfgMapper::toDto);
    }

    /**
     * Delete the kopfg by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Kopfg : {}", id);

        kopfgRepository.deleteById(id);
    }
}
