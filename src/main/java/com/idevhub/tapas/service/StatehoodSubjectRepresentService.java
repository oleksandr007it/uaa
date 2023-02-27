package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import com.idevhub.tapas.service.criteria.RepresentCriteria;
import com.idevhub.tapas.service.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing StatehoodSubjectRepresent.
 */
public interface StatehoodSubjectRepresentService {

    /**
     * Save a statehoodSubjectRepresent.
     *
     * @param input the entity to save
     * @return the persisted entity
     */
    StatehoodSubjectRepresentDTO create(StatehoodSubjectRepresentCreateDTO input);


    StatehoodSubjectRepresentDTO update(StatehoodSubjectRepresentUpdateDTO inputDto);

    /**
     * Get the "id" statehoodSubjectRepresent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    StatehoodSubjectRepresentWithNameDTO findOne(Long id);

    StatehoodSubjectRepresentDTO getCurrentRepresentDto();

    List<StatehoodSubjectRepresentMainInfoDTO> getAllActiveByDeclarantId(Long id);

    StatehoodSubjectRepresent createRepresenterAndSave(CreateRepresenterDTO  createRepresenterDTO);

    Optional<StatehoodSubjectRepresent> findByDeclarantIdAndStatehoodSubjectId(Long currentUserId, Long id);

    Boolean hasAccessByIds(Long declarantId, Long subjectId);

    void changeContextTo(Long statehoodSubjectId);

    Page<StatehoodSubjectRepresentMainInfoDTO> findAllRepresentersBySubjectId(Pageable pageable, RepresentCriteria criteria);

    StatehoodSubjectRepresentContextDTO getCurrentContextMainDTO();

    StatehoodSubjectRepresentDTO removeRepresent(Long declarantId, Long subjectId);

    List<StatehoodSubjectRepresentMainInfoDTO> getAllActiveByCurrentDeclarant();
}
