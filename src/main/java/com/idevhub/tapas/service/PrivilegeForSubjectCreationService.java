package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.PrivilegeGroup;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.repository.PrivilegeGroupRepository;
import com.idevhub.tapas.service.dto.PrivilegeGroupWithPrivilegesDTO;
import com.idevhub.tapas.service.mapper.PrivilegeGroupMapper;
import com.idevhub.tapas.service.mapper.PrivilegeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

import static com.idevhub.tapas.domain.enumeration.PrivilegeType.LEGAL_ENTITY;
import static com.idevhub.tapas.security.SecurityUtils.getCurrentUserIdIfExists;
import static java.util.UUID.randomUUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PrivilegeForSubjectCreationService {

    private final PrivilegeGroupRepository privilegeGroupRepository;

    private final PrivilegeGroupMapper privilegeGroupMapper;
    private final PrivilegeMapper privilegeMapper;

    private final PrivilegesCheckService privilegesCheckService;
    private final PrivilegeGroupCheckService privilegeGroupCheckService;
    private final StatehoodSubjectCheckService statehoodSubjectCheckService;
    private final StatehoodSubjectRepresentCheckService statehoodSubjectRepresentCheckService;

    public PrivilegeGroupWithPrivilegesDTO createOrUpdateGroup(Long subjectId, PrivilegeGroupWithPrivilegesDTO groupDto) {
        performChecks(subjectId, groupDto);

        var group = createEntity(subjectId, groupDto);
        var savedGroup = privilegeGroupRepository.save(group);

        return privilegeGroupMapper.toDto(savedGroup);
    }

    private void performChecks(Long subjectId, PrivilegeGroupWithPrivilegesDTO groupDto) {
        var isUpdating = !StringUtils.isEmpty(groupDto.getCode());
        if (isUpdating) {
            var storedGroup = privilegeGroupRepository.findById(groupDto.getCode()).orElseThrow();
            storedGroup.checkIsNotGlobal();
            storedGroup.checkIsNotDeleted();
            storedGroup.checkType(LEGAL_ENTITY);
        }
        privilegeGroupCheckService.checkOtherWithSameNameNotExists(groupDto, LEGAL_ENTITY);
        statehoodSubjectCheckService.checkSubjectIsActive(subjectId);
        statehoodSubjectRepresentCheckService.checkDeclarantIsActiveAndHaveSubject(getCurrentUserIdIfExists(), subjectId);
        privilegesCheckService.checkStoredPrivilegesType(groupDto.getPrivileges(), LEGAL_ENTITY);
    }

    private PrivilegeGroup createEntity(Long subjectId, PrivilegeGroupWithPrivilegesDTO groupDto) {
        var code = groupDto.getCode() == null ? randomUUID().toString() : groupDto.getCode();
        var subject = new StatehoodSubject(subjectId);
        var privileges = privilegeMapper.toEntity(groupDto.getPrivileges());

        return new PrivilegeGroup()
            .setCode(code)
            .setGlobal(false)
            .setFullNameUkr(groupDto.getFullNameUkr())
            .setPrivilegeGroupType(LEGAL_ENTITY)
            .setStatehoodSubject(subject)
            .setPrivileges(privileges);
    }
}
