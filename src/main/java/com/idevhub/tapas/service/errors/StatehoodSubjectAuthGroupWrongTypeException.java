package com.idevhub.tapas.service.errors;

public class StatehoodSubjectAuthGroupWrongTypeException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public StatehoodSubjectAuthGroupWrongTypeException(String messagePath) {
        super(messagePath);
    }

    public StatehoodSubjectAuthGroupWrongTypeException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
