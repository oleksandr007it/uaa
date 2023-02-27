package com.idevhub.tapas.service.errors;

public class RepresentNotFoundException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public RepresentNotFoundException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
