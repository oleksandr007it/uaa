package com.idevhub.tapas.security;

import lombok.val;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.idevhub.tapas.security.AuthoritiesConstants.USER_ID_KEY;

@Component
public class UserIdTokenEnhancer implements TokenEnhancer {


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        addClaims((DefaultOAuth2AccessToken) accessToken, authentication);
        return accessToken;
    }

    private void addClaims(DefaultOAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation() != null && !accessToken.getAdditionalInformation().isEmpty()
            ? accessToken.getAdditionalInformation()
            : new LinkedHashMap<>();

        addUserIdClaim(additionalInformation, authentication);

        accessToken.setAdditionalInformation(additionalInformation);
    }

    private void addUserIdClaim(Map<String, Object> additionalInformation, OAuth2Authentication authentication) {
        val principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) return;
        val currentUserId = ((CustomUserDetails) principal).getUsetId();
        additionalInformation.put(USER_ID_KEY, currentUserId);
    }
}
