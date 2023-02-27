package com.idevhub.tapas.service.errors;

public class StatehoodSubjectAuthGroupGeneralException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public StatehoodSubjectAuthGroupGeneralException(String messagePath) {
        super(messagePath);
    }

    public StatehoodSubjectAuthGroupGeneralException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
