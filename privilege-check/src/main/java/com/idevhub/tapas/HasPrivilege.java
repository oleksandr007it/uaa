package com.idevhub.tapas;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface HasPrivilege {
    /**
     * Privilege name to check
     */
    String[] value();
}
