package com.idevhub.tapas.web.rest.vm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KeyAndPasswordVMTest {
    private KeyAndPasswordVM keyAndPasswordVM;

    @BeforeEach
    public void setUp() {
        this.keyAndPasswordVM = new KeyAndPasswordVM();
    }

    @Test
    public void testSettersGetters() {
        keyAndPasswordVM.setKey("new_key");
        keyAndPasswordVM.setNewPassword("new_password");

        assertThat(keyAndPasswordVM.getKey()).isEqualTo("new_key");
        assertThat(keyAndPasswordVM.getNewPassword()).isEqualTo("new_password");
    }
}
