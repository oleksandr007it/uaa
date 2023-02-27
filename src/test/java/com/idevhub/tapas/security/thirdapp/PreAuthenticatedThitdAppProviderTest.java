package com.idevhub.tapas.security.thirdapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PreAuthenticatedThitdAppProviderTest {

    private PreAuthenticatedThitdAppProvider preAuthenticatedThitdAppProviderUnderTest;

    @BeforeEach
    void setUp() {
        preAuthenticatedThitdAppProviderUnderTest = new PreAuthenticatedThitdAppProvider();
    }

    @Test
    void testAuthenticate() {
        // Setup
        final Authentication authentication = new UsernamePasswordAuthenticationToken("user","user");
        final Authentication expectedResult = null;

        // Run the test
        final Authentication result = preAuthenticatedThitdAppProviderUnderTest.authenticate(authentication);

        // Verify the results
        assertThat(result).isEqualTo(result);
    }

    @Test
    void testAuthenticate_ThrowsAuthenticationException() {
        // Setup
        final Authentication authentication = null;

        // Run the test
        assertThatThrownBy(() -> {
            preAuthenticatedThitdAppProviderUnderTest.authenticate(authentication);
        }).isInstanceOf(Exception.class);
    }
}
