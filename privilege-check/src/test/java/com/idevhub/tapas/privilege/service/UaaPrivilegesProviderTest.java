package com.idevhub.tapas.privilege.service;

import com.idevhub.tapas.privilege.config.BeanConfiguration;
import com.idevhub.tapas.privilege.service.client.UaaClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BeanConfiguration.class)
@AutoConfigureMockMvc
@DisplayName("Test remote client call format")
class UaaPrivilegesProviderTest {

    @MockBean
    UaaClient uaaClient;

    @Autowired
    UaaPrivilegesProvider uaaPrivilegesProvider;

    @Test
    @DisplayName("Provider got privileges from UAA client")
    void getPrivileges() {
        // GIVEN
        when(uaaClient.getUserPrivilegeCodes(anyLong())).thenReturn(Set.of("V1_1_1", "V2_2_2"));

        // WHEN
        var privileges = uaaPrivilegesProvider.getPrivileges(10L);

        // THEN
        assertThat(privileges).containsExactlyInAnyOrder("V1_1_1", "V2_2_2");
    }
}
