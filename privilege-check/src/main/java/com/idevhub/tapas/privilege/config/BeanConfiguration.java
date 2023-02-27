package com.idevhub.tapas.privilege.config;

import com.idevhub.tapas.HasPrivilegeAspect;
import com.idevhub.tapas.privilege.service.DefaultPrivilegeContainsCheckService;
import com.idevhub.tapas.privilege.service.PrivilegeContainsCheckService;
import com.idevhub.tapas.privilege.service.PrivilegesProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableFeignClients
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackageClasses = HasPrivilegeAspect.class)
public class BeanConfiguration {
    @Bean
    @ConditionalOnMissingBean
    PrivilegeContainsCheckService privilegeContainsCheckService(PrivilegesProvider privilegesProvider) {
        return new DefaultPrivilegeContainsCheckService(privilegesProvider);
    }
}
