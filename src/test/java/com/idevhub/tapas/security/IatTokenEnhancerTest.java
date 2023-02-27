package com.idevhub.tapas.security;

import com.idevhub.tapas.security.thirdapp.ThirdAppAuthenticationToken;
import com.idevhub.tapas.service.dto.UserDTOfromIdGovUa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

class IatTokenEnhancerTest {

    private IatTokenEnhancer iatTokenEnhancerUnderTest;

    @BeforeEach
    void setUp() {
        iatTokenEnhancerUnderTest = new IatTokenEnhancer();
    }

    @Test
    void testEnhance() {
        // Setup
        UserDTOfromIdGovUa user = new UserDTOfromIdGovUa();

        ThirdAppAuthenticationToken thirdAppAuthenticationToken = new ThirdAppAuthenticationToken(new LdapUserDetails("test", "test", user, "organization", "customsBodyCode", "employeeIdCardNumber", "ldapUserDirectoryId"), "user");

        final OAuth2AccessToken accessToken = new DefaultOAuth2AccessToken("token");
        final OAuth2Authentication authentication = new OAuth2Authentication(
            new OAuth2Request(new HashMap<>(), "clientId", Arrays.asList(),
                false,
                new HashSet<>(Arrays.asList("value")), new HashSet<>(Arrays.asList("value")), "redirectUri", new HashSet<>(Arrays.asList("value")), new HashMap<>()),
            thirdAppAuthenticationToken);

        final OAuth2AccessToken result = iatTokenEnhancerUnderTest.enhance(accessToken, authentication);

        // Verify the results
    }
}
