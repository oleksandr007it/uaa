package com.idevhub.tapas.service.errors;

public class AuthException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public AuthException(String messagePath) {
        super(messagePath);
    }

    public AuthException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
