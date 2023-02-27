package com.idevhub.tapas.service.errors;

public class WrongActiveContextException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public WrongActiveContextException(String messagePath) {
        super(messagePath);
    }
}
