package com.idevhub.tapas.service.errors;

public class StatehoodSubjectAuthGroupNotFoundException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public StatehoodSubjectAuthGroupNotFoundException(String messagePath) {
        super(messagePath);
    }

    public StatehoodSubjectAuthGroupNotFoundException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
