package com.idevhub.tapas.config;

import com.idevhub.tapas.privilege.service.PrivilegesProvider;
import com.idevhub.tapas.service.PrivilegeForRepresentService;
import com.idevhub.tapas.service.PrivilegeForUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;

@Service
@Primary
@Slf4j
@RequiredArgsConstructor
public class LocalPrivilegeProvider implements PrivilegesProvider {
    private final PrivilegeForRepresentService privilegeForRepresentService;
    private final PrivilegeForUserService privilegeForUserService;

    @Override
    public Set<String> getPrivileges(Long userId) {
        log.debug("Search for privileges locally for userId={}", userId);

        var userPrivileges = privilegeForUserService.getPrivilegeCodesForUser(userId);
        var representPrivileges = privilegeForRepresentService.getPrivilegeCodesForUserCurrentRepresent(userId);

        return concat(userPrivileges.stream(), representPrivileges.stream()).collect(toSet());
    }
}
