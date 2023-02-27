package com.idevhub.tapas.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserNotActivatedExceptionTest {
    private UserNotActivatedException exception;

    @BeforeEach
    public void setUp() {
        this.exception = new UserNotActivatedException("message", new Throwable("throwable message"));
    }

    @Test
    public void testToString() {
        final String result = exception.toString();

        assertThat(result).hasSizeGreaterThan(1);
        assertThat(result).contains("message");
    }
}
