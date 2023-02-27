package com.idevhub.tapas.service.errors;

public class UserAuthenticationNeedException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public UserAuthenticationNeedException(String messagePath) {
        super(messagePath);
    }
}
