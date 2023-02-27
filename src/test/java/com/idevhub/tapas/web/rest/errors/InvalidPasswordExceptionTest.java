package com.idevhub.tapas.web.rest.errors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidPasswordExceptionTest {
    private InvalidPasswordException exception;

    @BeforeEach
    public void setUp() {
        this.exception = new InvalidPasswordException();
    }

    @Test
    public void testToTitle() {
        final String result = exception.getTitle();

        assertThat(result).hasSizeGreaterThan(1);
        assertThat(result).contains("Incorrect password");
    }
}
