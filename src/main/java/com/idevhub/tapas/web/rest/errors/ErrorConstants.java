package com.idevhub.tapas.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "https://www.jhipster.tech/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");
    public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");


    public static final String USER_NOT_FOUND = "error.user.not.found";
    public static final String USER_NOT_ACTIVATED = "error.user.not.activated";
    public static final String ERR_AUTHENTICATION_BLOCKED = "error.authentication.blocked";
    public static final String ERR_AUTH_DATA_NOT_PROVIDED = "error.authentication.dataNotProvided";


    private ErrorConstants() {
    }
}
