package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import com.idevhub.tapas.repository.PrivilegeRepository;
import com.idevhub.tapas.service.dto.PrivilegeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrivilegesCheckService {

    private final PrivilegeRepository privilegeRepository;

    public void checkStoredPrivilegesType(List<PrivilegeDTO> privileges, PrivilegeType typeToCheck) {
        var privilegeCodes = privileges.stream().map(PrivilegeDTO::getCode).collect(toSet());

        var storedPrivileges = privilegeRepository.findAllByCodeInAndPrivilegeType(privilegeCodes, typeToCheck);

        var isAllPrivilegesHasSameTypeAsStored = privilegeCodes.size() == storedPrivileges.size();
        if (!isAllPrivilegesHasSameTypeAsStored)
            throw new ValidationException(format("All privileges must have type %s", typeToCheck));
    }
}
