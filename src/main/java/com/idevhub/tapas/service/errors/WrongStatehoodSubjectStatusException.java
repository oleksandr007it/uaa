package com.idevhub.tapas.service.errors;

public class WrongStatehoodSubjectStatusException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public WrongStatehoodSubjectStatusException(String messagePath) {
        super(messagePath);
    }
}
