package com.idevhub.tapas.security.thirdapp;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class ThirdAppAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public ThirdAppAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
