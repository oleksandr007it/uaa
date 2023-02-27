package com.idevhub.tapas.service.errors;

public class StatehoodSubjectAuthNotFoundException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public StatehoodSubjectAuthNotFoundException(String messagePath) {
        super(messagePath);
    }
}
