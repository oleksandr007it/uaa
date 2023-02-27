package com.idevhub.tapas.service.errors;

public class EntityStatusException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public EntityStatusException(String messagePath) {
        super(messagePath);
    }

    public EntityStatusException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
