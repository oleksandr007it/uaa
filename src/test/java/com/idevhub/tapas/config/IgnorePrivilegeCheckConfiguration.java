package com.idevhub.tapas.config;

import com.idevhub.tapas.privilege.service.PrivilegeContainsCheckService;
import com.idevhub.tapas.privilege.service.error.PrivilegeMissingException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgnorePrivilegeCheckConfiguration {
    @Bean
    PrivilegeContainsCheckService privilegeCheckService() {
        return new PrivilegeContainsCheckService() {
            @Override
            public void checkPrivilege(Long userId, String... privilegeToCheck) throws PrivilegeMissingException {
                // do nothing for checking
            }

            @Override
            public boolean hasPrivilege(Long userId, String... privilegeToCheck) {
                return true;
            }
        };
    }
}
