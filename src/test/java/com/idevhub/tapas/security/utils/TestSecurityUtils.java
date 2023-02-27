package com.idevhub.tapas.security.utils;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestSecurityUtils {

    public static final String USER_ID_KEY = "userId";

    public static void setSecurityContext(Long userId, String... authorities) {
        // create authentication
        var authentication = TestSecurityUtils.createAuthentication(
            userId.toString(),
            "default_login",
            "default_password",
            null,
            authorities);

        // create context
        var context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        // set context
        TestSecurityContextHolder.setContext(context);
    }


    public static UsernamePasswordAuthenticationToken createAuthentication(String userId, String username, String password, String value, String[] authorities) {
        User principal = createCustomUserDetails(username, password, value, authorities);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            principal, principal.getPassword(), principal.getAuthorities());

        OAuth2AuthenticationDetails details = createOAuthAuthenticationDetails(userId);
        authentication.setDetails(details);

        return authentication;
    }

    private static User createCustomUserDetails(String username, String password, String value, String[] authorities) {
        String login = StringUtils.hasLength(username)
            ? username
            : value;

        List<GrantedAuthority> grantedAuthorities = getGrantedAuthorities(authorities);

        return new User(login, password, grantedAuthorities);
    }

    private static OAuth2AuthenticationDetails createOAuthAuthenticationDetails(String userId) {
        var details = new OAuth2AuthenticationDetails(new MockHttpServletRequest());
        details.setDecodedDetails(new LinkedHashMap<>(Map.of(USER_ID_KEY, userId)));
        return details;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(String[] authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }

        return grantedAuthorities;
    }
}
