package com.idevhub.tapas.privilege.service;

import com.idevhub.tapas.privilege.service.error.PrivilegeMissingException;

public interface PrivilegeContainsCheckService {
    void checkPrivilege(Long userId, String... privilegeToCheck) throws PrivilegeMissingException;

    boolean hasPrivilege(Long userId, String... privilegeToCheck);
}
