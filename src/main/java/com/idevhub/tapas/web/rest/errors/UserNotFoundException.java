package com.idevhub.tapas.web.rest.errors;

import java.net.URI;

public class UserNotFoundException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super(URI.create("/user-could-not-be-found"), "User could not be found", "userManagement", "userexists");
    }
}
