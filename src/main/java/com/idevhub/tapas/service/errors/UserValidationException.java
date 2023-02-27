package com.idevhub.tapas.service.errors;

public class UserValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserValidationException() {
        super("Not  valid  user  data");
    }

}
