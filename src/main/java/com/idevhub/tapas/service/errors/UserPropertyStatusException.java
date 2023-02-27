package com.idevhub.tapas.service.errors;

public class UserPropertyStatusException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public UserPropertyStatusException(String messagePath) {
        super(messagePath);
    }
}
