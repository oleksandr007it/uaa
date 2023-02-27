package com.idevhub.tapas.service.errors;

public class UserNotLoggedInException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserNotLoggedInException(String message) {
        super(message);
    }
}
