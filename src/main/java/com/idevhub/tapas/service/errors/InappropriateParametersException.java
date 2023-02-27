package com.idevhub.tapas.service.errors;

public class InappropriateParametersException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public InappropriateParametersException(String messagePath) {
        super(messagePath);
    }

    public InappropriateParametersException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
