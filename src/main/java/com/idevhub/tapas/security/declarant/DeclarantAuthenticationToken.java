package com.idevhub.tapas.security.declarant;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class DeclarantAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public DeclarantAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
