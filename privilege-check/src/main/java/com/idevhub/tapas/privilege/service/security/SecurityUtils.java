package com.idevhub.tapas.privilege.service.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Map;

public class SecurityUtils {

    private static final String DETAILS_USER_ID = "userId";

    private SecurityUtils() {
    }


    @SuppressWarnings("unchecked")
    public static Long getCurrentUserIdIfExists() {
        var securityContext = SecurityContextHolder.getContext();
        var authentication = securityContext.getAuthentication();

        var hasAuthentication = authentication instanceof OAuth2Authentication;
        if (!hasAuthentication) return null;

        var oauthAuthentication = ((OAuth2Authentication) authentication);
        var hasUserAuthentication = oauthAuthentication.getUserAuthentication() instanceof AbstractAuthenticationToken;
        if (!hasUserAuthentication) return null;

        var userAuthentication = (AbstractAuthenticationToken) oauthAuthentication.getUserAuthentication();
        var details = userAuthentication.getDetails();
        var hasProperDetails = details instanceof Map;
        if (!hasProperDetails) return null;

        var detailsMap = (Map<String, Object>) details;
        var userId = detailsMap.get(DETAILS_USER_ID);
        if (userId == null) return null;

        return userId instanceof Long ? (Long) userId : Long.valueOf(userId.toString());
    }
}
