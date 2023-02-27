package com.idevhub.tapas.privilege.service;

import com.idevhub.tapas.privilege.service.client.UaaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UaaPrivilegesProvider implements PrivilegesProvider {
    private final UaaClient uaaClient;

    @Override
    public Set<String> getPrivileges(Long userId) {
        return uaaClient.getUserPrivilegeCodes(userId);
    }
}
