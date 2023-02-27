package com.idevhub.tapas.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String DECLARANT = "ROLE_DECLARANT";
    public static final String THIRD_APP = "ROLE_THIRD_APP";
    public static final String BACK_OFFICE = "ROLE_BACK_OFFICE";
    public static final String CEA_EMPLOYEE = "ROLE_CEA_EMPLOYEE";
    public static final String USER_ID_KEY = "userId";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String BACK_GRANT_TYPE = "back_office";
    public static final String CEA_GRANT_TYPE = "cea_emploee";
    public static final String DECLARANT_GRANT_TYPE = "declarant";
    public static final String THIRD_APP_GRANT_TYPE = "third_app_access";

    private AuthoritiesConstants() {
    }
}
