package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.PrivilegeGroup;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.enumeration.ActiveStatus;
import com.idevhub.tapas.repository.PrivilegeGroupRepository;
import com.idevhub.tapas.repository.StatehoodSubjectRepresentRepository;
import com.idevhub.tapas.service.dto.PrivilegeGroupGeneralDTO;
import com.idevhub.tapas.service.impl.CurrentRepresentService;
import com.idevhub.tapas.service.mapper.PrivilegeGroupMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptySet;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivilegeForRepresentService {

    private final StatehoodSubjectRepresentRepository statehoodSubjectRepresentRepository;
    private final PrivilegeGroupMapper privilegeGroupMapper;
    private final CurrentRepresentService currentRepresentService;
    private final PrivilegeGroupRepository privilegeGroupRepository;
    private final String GLOBAL_GROUP = "GR_V1_LEGAL_ENTITY";


    @Transactional
    public Set<String> getPrivilegeCodesForUserCurrentRepresent(Long userId) {
        log.debug("Getting privileges for current represent of user with id={}", userId);

        var currentRepresentIdOpt = currentRepresentService.getId(userId);
        if (currentRepresentIdOpt.isEmpty()) return emptySet();
        log.debug("Found current represent with id={} for userId={}", currentRepresentIdOpt.get(), userId);

        return getPrivilegeCodesForUserRepresent(userId, currentRepresentIdOpt.get());
    }

    @Transactional
    public Set<String> getPrivilegeCodesForUserRepresent(Long userId, Long representId) {
        var represent = getRepresentForUser(userId, representId);
        if (represent.isEmpty()) return emptySet();

        var codes = represent.get().loadAndGetPrivilegeCodes();
        log.debug("Found {} privilege codes for represent with id={} and userId={}", codes.size(), representId, userId);

        return codes;
    }


    @Transactional
    public PrivilegeGroup getGlobalGroupByName() {
        return privilegeGroupRepository.findByCodeAndStatus(GLOBAL_GROUP, ActiveStatus.ACTIVE)
            .orElseThrow(() -> new EntityNotFoundException(StatehoodSubjectRepresent.class, GLOBAL_GROUP));
    }


    @Transactional
    public Set<PrivilegeGroupGeneralDTO> getPrivilegeGroupsForRepresent(Long representId) {
        var represent = tryToFindRepresent(representId);

        return getAndMapGroups(represent);
    }

    @Transactional
    public  Set<PrivilegeGroup> convertStringListToPersistPrivilegeGroupSet  (List<String> privilegeGroupCodeList){
        Set<PrivilegeGroup> privilegeGroups = new HashSet<>();
        for (String code:privilegeGroupCodeList){
            privilegeGroupRepository.findById(code).filter(privilegeGroup -> privilegeGroup.getStatus().equals(ActiveStatus.ACTIVE)).ifPresent(privilegeGroups::add);
        }
       return privilegeGroups;
    }



    private StatehoodSubjectRepresent tryToFindRepresent(Long representId) {
        log.debug("Looking for represent with id={}", representId);

        return statehoodSubjectRepresentRepository.findByIdWithGroups(representId)
            .orElseThrow(() -> new EntityNotFoundException(StatehoodSubjectRepresent.class, representId));
    }

    private Set<PrivilegeGroupGeneralDTO> getAndMapGroups(StatehoodSubjectRepresent represent) {
        log.debug("Get and map privilege groups for represent with id={}", represent.getId());

        var groups = represent.getPrivilegeGroups();

        return privilegeGroupMapper.toDto(groups);
    }

    private Optional<StatehoodSubjectRepresent> getRepresentForUser(Long userId, Long representId) {
        log.debug("Looking for statehood subject represent with id {}", representId);

        return statehoodSubjectRepresentRepository.findByDeclarantIdAndId(userId, representId);
    }
}
