package com.idevhub.tapas.security.backoffice;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class BackOfficeAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public BackOfficeAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
