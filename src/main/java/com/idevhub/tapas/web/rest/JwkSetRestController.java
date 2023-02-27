package com.idevhub.tapas.web.rest;

import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class JwkSetRestController {

    @Autowired
    private JWKSet jwkSet;

    @GetMapping("/oauth2/jwks")
    public Map<String, Object> keys() {
        return this.jwkSet.toJSONObject();
    }

}
