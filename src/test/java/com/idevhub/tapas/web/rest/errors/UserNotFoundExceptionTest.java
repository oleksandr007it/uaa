package com.idevhub.tapas.web.rest.errors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserNotFoundExceptionTest {

    private UserNotFoundException userNotFoundExceptionUnderTest;

    @BeforeEach
    void setUp() {
        userNotFoundExceptionUnderTest = new UserNotFoundException();
    }

    @Test
    public void testEntityNamee()  {

        assertThat(userNotFoundExceptionUnderTest.getEntityName()).isEqualTo("userManagement");


    }
}
