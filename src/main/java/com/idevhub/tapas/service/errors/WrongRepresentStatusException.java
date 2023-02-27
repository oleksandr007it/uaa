package com.idevhub.tapas.service.errors;

public class WrongRepresentStatusException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public WrongRepresentStatusException(String messagePath) {
        super(messagePath);
    }
}
