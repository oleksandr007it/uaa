package com.idevhub.tapas.service.errors;

public class EmailConfirmationException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public EmailConfirmationException(String messagePath) {
        super(messagePath);
    }

    public EmailConfirmationException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
