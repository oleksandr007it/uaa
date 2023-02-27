package com.idevhub.tapas.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

    long userId() default 3L;

    String userName() default "admin";

    String userRole() default "ROLE_USER";
}
