package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.service.criteria.StatehoodSubjectCriteria;
import com.idevhub.tapas.service.dto.StatehoodSubjectCreateDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectSendConfirmEmailDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectWithPrivilegeGroupsDTO;
import com.idevhub.tapas.service.dto.SubjectInfoForAdd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing StatehoodSubject.
 */
public interface StatehoodSubjectService {

    StatehoodSubjectDTO save(StatehoodSubjectCreateDTO inputDTO);

    StatehoodSubjectDTO update(StatehoodSubjectDTO inputDTO);

    Page<StatehoodSubjectDTO> findAll(Pageable pageable, StatehoodSubjectCriteria criteria);

    StatehoodSubjectDTO findOne(Long id);

    StatehoodSubject findOneById(Long id);

    StatehoodSubjectWithPrivilegeGroupsDTO getCurrentSubjectByRepresentativeWithPrivilegeGroups();

    Long createStatehoodSubjectAndAddRepreseter(SubjectInfoForAdd subjectInfoForAdd);

    SubjectInfoForAdd getActiveSubjectsByCodeOrPassport(String codeOrPassport);

    void delete(Long id);

    void sendSubjectConfirmationEmail(StatehoodSubjectSendConfirmEmailDTO inputData);
}
