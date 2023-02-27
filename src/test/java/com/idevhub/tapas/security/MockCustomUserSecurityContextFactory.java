package com.idevhub.tapas.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;

@RequiredArgsConstructor
public class MockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    private final OAuth2TokenMockUtil oAuth2TokenMock;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        OAuth2Authentication authentication = oAuth2TokenMock
            .createAuthentication(
                customUser.userId(),
                customUser.userName(),
                Collections.singleton(customUser.userRole()));
        context.setAuthentication(authentication);
        return context;
    }
}
