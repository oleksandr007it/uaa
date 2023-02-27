package com.idevhub.tapas.service.errors;

public class UserAlreadyRepresentativeException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public UserAlreadyRepresentativeException(String messagePath) {
        super(messagePath);
    }
}
