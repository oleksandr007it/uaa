package com.idevhub.tapas.service;

import com.idevhub.tapas.repository.PrivilegeGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrivilegeForSubjectDeleteService {

    private final PrivilegeGroupRepository privilegeGroupRepository;

    private final StatehoodSubjectRepresentCheckService statehoodSubjectRepresentCheckService;

    public void deleteGroupForSubject(Long userId, String groupCode) {
        var group = privilegeGroupRepository.findById(groupCode).orElseThrow();
        group.checkIsNotGlobal();
        group.checkIsNotDeleted();

        if (group.getStatehoodSubject() != null)
            statehoodSubjectRepresentCheckService.checkDeclarantIsActiveAndHaveSubject(userId, group.getStatehoodSubject().getId());
        statehoodSubjectRepresentCheckService.checkNoRepresentHasGroup(groupCode);

        privilegeGroupRepository.delete(group);
    }
}
