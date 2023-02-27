package com.idevhub.tapas.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    private User currentUser = new User();
    private User userUnderTest;

    @BeforeEach
    void setUp() {
        userUnderTest = new User();
        currentUser.setId(123L);
        currentUser.setLogin("name");
    }


    @Test
    void getId() {
        assertEquals(currentUser.getId(), 123L);
    }

    @Test
    void setId() {
        currentUser.setId(1245L);
        assertEquals(currentUser.getId(), 1245L);
    }


    @Test
    void getLogin() {
        assertEquals(currentUser.getLogin(), "name");
    }

    @Test
    void setLogin() {
        currentUser.setLogin("newname");
        assertEquals(currentUser.getLogin(), "newname");
    }

    @Test
    void testEquals() {
        // Setup

        // Run the test
        final boolean result = userUnderTest.equals(userUnderTest);
        // Verify the results
        assertThat(result).isTrue();
    }





}
