package com.idevhub.tapas;

import com.idevhub.tapas.privilege.service.PrivilegeContainsCheckService;
import com.idevhub.tapas.privilege.service.security.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class HasPrivilegeAspect {
    private final PrivilegeContainsCheckService privilegeContainsCheckService;

    public HasPrivilegeAspect(PrivilegeContainsCheckService privilegeContainsCheckService) {
        this.privilegeContainsCheckService = privilegeContainsCheckService;
    }

    @Around("@annotation(hasPrivilege)")
    public Object beforeCall(ProceedingJoinPoint pjp, HasPrivilege hasPrivilege) throws Throwable {
        log.debug("AOP: Checking for privilege");

        Long userId = SecurityUtils.getCurrentUserIdIfExists();

        privilegeContainsCheckService.checkPrivilege(userId, hasPrivilege.value());

        return pjp.proceed();
    }
}
