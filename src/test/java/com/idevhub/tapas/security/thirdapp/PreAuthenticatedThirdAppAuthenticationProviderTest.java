package com.idevhub.tapas.security.thirdapp;

import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.LdapUserDetails;
import com.idevhub.tapas.service.dto.UserDTOfromIdGovUa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PreAuthenticatedThirdAppAuthenticationProviderTest {

    private PreAuthenticatedThirdAppAuthenticationProvider preAuthenticatedThirdAppAuthenticationProviderUnderTest;

    @BeforeEach
    void setUp() {
        preAuthenticatedThirdAppAuthenticationProviderUnderTest = new PreAuthenticatedThirdAppAuthenticationProvider();

    }

    @Test
    void testAuthenticate() {
        // Setup


        // Run the test
        UserDTOfromIdGovUa user = new UserDTOfromIdGovUa();
        LdapUserDetails ldapUserDetails = new LdapUserDetails("test", "test", user, "organization", "customsBodyCode", "employeeIdCardNumber", "ldapUserDirectoryId");

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.DECLARANT));


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(ldapUserDetails, "pwd", authorities);
        usernamePasswordAuthenticationToken.setAuthenticated(false);

        PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(usernamePasswordAuthenticationToken, "gogo");

        final Authentication result = preAuthenticatedThirdAppAuthenticationProviderUnderTest.authenticate(preAuthenticatedAuthenticationToken);

        assertThat(result.getCredentials()).isEqualTo("gogo");

    }



    @Test
    void testAuthenticateEx() {

        PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(null, "gogo");
        assertThatThrownBy(() -> {
            preAuthenticatedThirdAppAuthenticationProviderUnderTest.authenticate(preAuthenticatedAuthenticationToken);
        }).isInstanceOf(BadCredentialsException.class).hasMessageContaining("No pre-authenticated principal");

    }


    @Test
    void testAuthenticateEx2() {

        PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken("ok", null);

        assertThatThrownBy(() -> {
            preAuthenticatedThirdAppAuthenticationProviderUnderTest.authenticate(preAuthenticatedAuthenticationToken);
        }).isInstanceOf(BadCredentialsException.class).hasMessageContaining("No pre-authenticated credentials");

    }


}
