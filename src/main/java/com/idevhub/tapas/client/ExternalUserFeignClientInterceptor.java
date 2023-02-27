package com.idevhub.tapas.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

public class ExternalUserFeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_CLIENT = "Authorization-client";
    private static final String BEARER_TOKEN_TYPE = "Bearer";
    @Autowired
    private OAuth2ExternalClient oAuth2ExternalClient;

    @Override
    public void apply(RequestTemplate template) {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String externalToken = oAuth2ExternalClient.getExternalAccessToken();
        if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
            template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE, externalToken));
            template.header(AUTHORIZATION_HEADER_CLIENT, String.format(details.getTokenValue()));
        }
    }
}
