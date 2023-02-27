package com.idevhub.tapas.service.errors;

public class PrivilegeGroupException extends LocalizedException {

    private static final long serialVersionUID = 1L;

    public PrivilegeGroupException(String messagePath) {
        super(messagePath);
    }

    public PrivilegeGroupException(String messagePath, String... params) {
        super(messagePath, params);
    }
}
