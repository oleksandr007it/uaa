package com.idevhub.tapas.service.errors;

public class UserNotFoundException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
