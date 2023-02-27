package com.idevhub.tapas.service.errors;

public class UserAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserAlreadyExistException() {
        super("User already existed!");
    }

}
