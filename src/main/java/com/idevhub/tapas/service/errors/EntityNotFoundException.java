package com.idevhub.tapas.service.errors;

public class EntityNotFoundException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String messagePath) {
        super(messagePath);
    }

    public EntityNotFoundException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
