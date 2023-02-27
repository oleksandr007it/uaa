package com.idevhub.tapas.service.errors;

public class WrongUserTypeException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public WrongUserTypeException(String messagePath) {
        super(messagePath);
    }
}
