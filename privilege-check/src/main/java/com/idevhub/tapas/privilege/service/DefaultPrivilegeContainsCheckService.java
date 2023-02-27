package com.idevhub.tapas.privilege.service;

import com.idevhub.tapas.privilege.service.error.PrivilegeMissingException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

import static java.lang.String.format;

@Slf4j
public class DefaultPrivilegeContainsCheckService implements PrivilegeContainsCheckService {

    private final PrivilegesProvider privilegesProvider;

    public DefaultPrivilegeContainsCheckService(PrivilegesProvider privilegesProvider) {
        this.privilegesProvider = privilegesProvider;
    }


    @Override
    public void checkPrivilege(Long userId, String... privilegeToCheck) {
        var hasPrivilege = hasPrivilege(userId, privilegeToCheck);

        if (!hasPrivilege) throw new PrivilegeMissingException(
            format("Missing expected %s privileges for userId=%s", Arrays.toString(privilegeToCheck), userId));
    }

    @Override
    public boolean hasPrivilege(Long userId, String... privilegeToCheck) {
        log.debug("Checking if user={} has privileges={}", userId, privilegeToCheck);

        var privileges = privilegesProvider.getPrivileges(userId);
        log.debug("Got {} privileges for userId={} to check", privileges.size(), userId);

        return privileges.containsAll(Arrays.asList(privilegeToCheck));
    }
}
