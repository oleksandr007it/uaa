package com.idevhub.tapas.privilege.service.error;

public class PrivilegeMissingException extends RuntimeException {
    public PrivilegeMissingException() {
    }

    public PrivilegeMissingException(String message) {
        super(message);
    }

    public PrivilegeMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}
