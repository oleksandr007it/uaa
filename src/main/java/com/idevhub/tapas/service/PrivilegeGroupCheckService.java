package com.idevhub.tapas.service;


import com.idevhub.tapas.domain.enumeration.ActiveStatus;
import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import com.idevhub.tapas.repository.PrivilegeGroupRepository;
import com.idevhub.tapas.service.dto.PrivilegeGroupWithPrivilegesDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Objects;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivilegeGroupCheckService {
    private final PrivilegeGroupRepository privilegeGroupRepository;

    public void checkOtherWithSameNameNotExists(PrivilegeGroupWithPrivilegesDTO groupDTO, PrivilegeType type) {
        var groupWithSameName = privilegeGroupRepository.findFirstByFullNameUkrAndPrivilegeGroupTypeAndStatus(groupDTO.getFullNameUkr(), type, ActiveStatus.ACTIVE);
        if (groupWithSameName.isPresent() && !Objects.equals(groupWithSameName.get().getCode(), groupDTO.getCode()))
            throw new ValidationException(format("Group with name %s already exists", groupDTO.getFullNameUkr()));
    }
}
