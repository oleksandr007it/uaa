package com.idevhub.tapas.security;

import com.idevhub.tapas.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.MockitoAnnotations.initMocks;

class CustomUserDetailsTest {

    @Mock
    private User mockCurrentUser;

    private CustomUserDetails customUserDetailsUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        customUserDetailsUnderTest = new CustomUserDetails("username", "password", false, false, false, false, Arrays.asList(), mockCurrentUser.getId());
    }

    @Test
    void testEraseCredentials() {
        // Setup

        // Run the test
        customUserDetailsUnderTest.eraseCredentials();

        // Verify the results
    }


    @Test
    void create() {
        // Setup
        customUserDetailsUnderTest.getAuthorities();
        customUserDetailsUnderTest.getPassword();
        customUserDetailsUnderTest.getUsername();
        customUserDetailsUnderTest.isEnabled();
        customUserDetailsUnderTest.isAccountNonExpired();
        customUserDetailsUnderTest.isAccountNonLocked();
        customUserDetailsUnderTest.isCredentialsNonExpired();
        customUserDetailsUnderTest.getUsetId();
        assertThatThrownBy(() -> {
            new CustomUserDetails(null, "password", false, false, false, false, Arrays.asList(), mockCurrentUser.getId());
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testEquals() {
        // Setup
        // Run the test
        final boolean result = customUserDetailsUnderTest.equals("rhs");

        // Verify the results
        assertThat(result).isFalse();
        final boolean result2 = customUserDetailsUnderTest.equals(customUserDetailsUnderTest);

        // Verify the results
        assertThat(result2).isTrue();
    }

    @Test
    void testHashCode() {
        // Setup

        // Run the test
        final int result = customUserDetailsUnderTest.hashCode();

        // Verify the results
        assertThat(result).isEqualTo(result);
    }

    @Test
    void testToString() {
        // Setup

        // Run the test
        final String result = customUserDetailsUnderTest.toString();

        // Verify the results
        assertThat(result).isEqualTo(result);
    }
}
