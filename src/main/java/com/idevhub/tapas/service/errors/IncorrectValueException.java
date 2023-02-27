package com.idevhub.tapas.service.errors;

public class IncorrectValueException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public IncorrectValueException(String messagePath) {
        super(messagePath);
    }
}
