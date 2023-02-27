package com.idevhub.tapas.service.errors;

public class RepresentHasNoRightsException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public RepresentHasNoRightsException(String messagePath) {
        super(messagePath);
    }
}
