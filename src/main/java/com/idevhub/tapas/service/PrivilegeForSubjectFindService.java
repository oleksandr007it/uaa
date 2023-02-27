package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.PrivilegeGroup;
import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import com.idevhub.tapas.repository.PrivilegeGroupRepository;
import com.idevhub.tapas.service.dto.PrivilegeGroupGeneralDTO;
import com.idevhub.tapas.service.dto.PrivilegeGroupWithPrivilegesDTO;
import com.idevhub.tapas.service.mapper.PrivilegeGroupMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import static com.idevhub.tapas.domain.enumeration.ActiveStatus.ACTIVE;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivilegeForSubjectFindService {
    private final PrivilegeGroupMapper privilegeGroupMapper;
    private final PrivilegeGroupRepository privilegeGroupRepository;

    @Transactional(readOnly = true)
    public Set<PrivilegeGroupGeneralDTO> getGroupsForSubject(Long subjectId) {
        var groups = findAllGroupsForSubject(subjectId);
        return privilegeGroupMapper.toDto(groups);
    }

    @Transactional(readOnly = true)
    public PrivilegeGroupWithPrivilegesDTO getGroupForSubject(Long subjectId, String groupCode) {
        return privilegeGroupRepository.findByCodeAndPrivilegeGroupTypeAndStatus(groupCode, PrivilegeType.LEGAL_ENTITY, ACTIVE)
            .map(privilegeGroupMapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException(PrivilegeGroup.class, groupCode));
    }


    private Set<PrivilegeGroup> findAllGroupsForSubject(Long subjectId) {
        log.debug("Looking for privilege group metas for subjectId={}", subjectId);

        return privilegeGroupRepository.findAllByPrivilegeGroupTypeAndStatus(PrivilegeType.LEGAL_ENTITY, ACTIVE).stream()
            .filter(isGlobalGroupOrSubjectMatches(subjectId))
            .collect(toSet());
    }

    private Predicate<PrivilegeGroup> isGlobalGroupOrSubjectMatches(Long subjectId) {
        return group -> group.isGlobal() ||
            (group.getStatehoodSubject() != null &&
                Objects.equals(group.getStatehoodSubject().getId(), subjectId));
    }
}
