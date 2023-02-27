package com.idevhub.tapas.privilege.service;

import java.util.Set;

public interface PrivilegesProvider {
    Set<String> getPrivileges(Long userId);
}
