package com.idevhub.tapas.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomAccessTokenConverterTest {

    private CustomAccessTokenConverter customAccessTokenConverterUnderTest;

    @BeforeEach
    void setUp() {
        customAccessTokenConverterUnderTest = new CustomAccessTokenConverter();
    }

    @Test
    void testExtractAuthentication() {
        // Setup
        final Map<String, ?> claims = Map.ofEntries();
        final OAuth2Authentication expectedResult = new OAuth2Authentication(new OAuth2Request(Map.ofEntries(Map.entry("value", "value")), "clientId", List.of(), false, Set.of("value"), Set.of("value"), "redirectUri", Set.of("value"), Map.ofEntries(Map.entry("value", "value"))), null);

        // Run the test
        final OAuth2Authentication result = customAccessTokenConverterUnderTest.extractAuthentication(claims);

    }
}
