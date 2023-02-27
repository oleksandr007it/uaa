package com.idevhub.tapas.security.backoffice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BackOfficeAuthenticationTokenTest {

    private BackOfficeAuthenticationToken backOfficeAuthenticationTokenUnderTest;

    @BeforeEach
    void setUp() {
        backOfficeAuthenticationTokenUnderTest = new BackOfficeAuthenticationToken("principal", "credentials");

    }

    @Test
    void testAuthenticate() {
        assertThat(backOfficeAuthenticationTokenUnderTest.getPrincipal()).isEqualTo("principal");
    }
}
