package com.idevhub.tapas.security.declarant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeclarantAuthenticationTokenTest {

    private DeclarantAuthenticationToken declarantAuthenticationTokenUnderTest;

    @BeforeEach
    void setUp() {
        declarantAuthenticationTokenUnderTest = new DeclarantAuthenticationToken("principal", "credentials");
    }


    @Test
    void testAuthenticate() {
        assertThat(declarantAuthenticationTokenUnderTest.getPrincipal()).isEqualTo("principal");
    }
}
