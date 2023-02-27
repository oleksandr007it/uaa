package com.idevhub.tapas.service.errors;

public class RepresentIdAndAccessToSSIdException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public RepresentIdAndAccessToSSIdException(String messagePath) {
        super(messagePath);
    }

    public RepresentIdAndAccessToSSIdException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
