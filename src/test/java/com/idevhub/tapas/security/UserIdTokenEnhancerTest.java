package com.idevhub.tapas.security;

import com.idevhub.tapas.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import javax.management.relation.Role;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserIdTokenEnhancerTest {

    private UserIdTokenEnhancer userIdTokenEnhancerUnderTest;

    @BeforeEach
    void setUp() {
        userIdTokenEnhancerUnderTest = new UserIdTokenEnhancer();
    }

    @Test
    void testEnhance() {
        // Setup
        final OAuth2AccessToken accessToken = new DefaultOAuth2AccessToken("token");

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.DECLARANT));

        User user = new User();
        user.setId(999L);
        CustomUserDetails customUserDetails = new CustomUserDetails("test", "user", true, true, true, true, authorities, user.getId());

        UsernamePasswordAuthenticationToken authentications =
            new UsernamePasswordAuthenticationToken(customUserDetails, "");

        final OAuth2Authentication authentication = new OAuth2Authentication(new OAuth2Request(new HashMap<>(), "clientId", Arrays.asList(),
            false, new HashSet<>(Arrays.asList("value")), new HashSet<>(Arrays.asList("value")), "redirectUri",
            new HashSet<>(Arrays.asList("value")), new HashMap<>()), authentications);

        // Run the test
        final OAuth2AccessToken result = userIdTokenEnhancerUnderTest.enhance(accessToken, authentication);

        Map<String, Object> map = result.getAdditionalInformation();
        Long userId = (Long) map.get("userId");

        // Verify the results
        assertEquals(userId, user.getId());


        // Verify the results
    }

}
