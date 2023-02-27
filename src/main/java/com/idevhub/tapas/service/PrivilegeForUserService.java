package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivilegeForUserService {

    private final UserRepository userRepository;

    @Transactional
    public Set<String> getPrivilegeCodesForUser(Long userId) {
        if (userId == null) return Collections.emptySet();

        var user = getUser(userId);

        return user.loadAndGetPrivilegeCodes();
    }


    private User getUser(Long userId) {
        log.debug("Looking for user with id {}", userId);

        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(User.class));
    }
}
