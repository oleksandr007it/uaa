package com.idevhub.tapas.service.errors;

public class StatehoodSubjectAuthGroupAlreadyDeletedException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public StatehoodSubjectAuthGroupAlreadyDeletedException(String messagePath) {
        super(messagePath);
    }

    public StatehoodSubjectAuthGroupAlreadyDeletedException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
