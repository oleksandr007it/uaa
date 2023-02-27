package com.idevhub.tapas.privilege.service;

import com.idevhub.tapas.privilege.config.BeanConfiguration;
import com.idevhub.tapas.privilege.service.client.UaaClient;
import com.idevhub.tapas.privilege.service.error.PrivilegeMissingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BeanConfiguration.class)
@AutoConfigureMockMvc
@DisplayName("Test privilege checks")
class DefaultPrivilegeContainsCheckServiceTest {

    @Autowired
    DefaultPrivilegeContainsCheckService privilegeCheckService;

    @MockBean
    UaaClient uaaClient;

    @Test
    @DisplayName("Provided correct UAA privileges checked")
    void checkPrivilege_Positive() {
        // GIVEN
        when(uaaClient.getUserPrivilegeCodes(anyLong())).thenReturn(Set.of("V1_1_1", "V2_2_2"));

        // WHEN
        assertDoesNotThrow(() -> privilegeCheckService.checkPrivilege(10L, "V1_1_1", "V2_2_2"));
    }

    @Test
    @DisplayName("Provided incorrect UAA privileges throws exception")
    void checkPrivilege_Negative() {
        // GIVEN
        when(uaaClient.getUserPrivilegeCodes(anyLong())).thenReturn(Set.of("V7_7_7", "V2_2_2"));

        // WHEN
        assertThrows(PrivilegeMissingException.class, () -> privilegeCheckService.checkPrivilege(10L, "V1_1_1", "V2_2_2"));
    }
}
