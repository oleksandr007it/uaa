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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LdapTokenEnhancerTest {

    private LdapTokenEnhancer ldapTokenEnhancerUnderTest;
    private final String testLastName = "Petrouchino";

    @BeforeEach
    void setUp() {
        ldapTokenEnhancerUnderTest = new LdapTokenEnhancer();
    }

    @Test
    void testEnhance() {
        // Setup

        UserDTOfromIdGovUa user = new UserDTOfromIdGovUa();
        user.setLastName(testLastName);
        ThirdAppAuthenticationToken thirdAppAuthenticationToken = new ThirdAppAuthenticationToken(new LdapUserDetails("test", "test", user, "organization", "customsBodyCode", "employeeIdCardNumber", "ldapUserDirectoryId"), "user");

        final OAuth2AccessToken accessToken = new DefaultOAuth2AccessToken("token");
        final OAuth2Authentication authentication = new OAuth2Authentication(
            new OAuth2Request(new HashMap<>(), "clientId", Arrays.asList(),
                false,
                new HashSet<>(Arrays.asList("value")), new HashSet<>(Arrays.asList("value")), "redirectUri", new HashSet<>(Arrays.asList("value")), new HashMap<>()),
            thirdAppAuthenticationToken);

        // Run the test
        final OAuth2AccessToken result = ldapTokenEnhancerUnderTest.enhance(accessToken, authentication);
        Map<String, Object> map = result.getAdditionalInformation();
        String lastName = (String) map.get(ldapTokenEnhancerUnderTest.FAMILY_NAME);

        // Verify the results
        assertEquals(lastName, testLastName);

    }
}
