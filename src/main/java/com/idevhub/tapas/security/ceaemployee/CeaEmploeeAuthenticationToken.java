package com.idevhub.tapas.security.ceaemployee;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CeaEmploeeAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public CeaEmploeeAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

}
