package com.idevhub.tapas.security;

import com.idevhub.tapas.security.backoffice.BackOfficeAuthenticationToken;
import com.idevhub.tapas.security.ceaemployee.CeaEmploeeAuthenticationToken;
import com.idevhub.tapas.security.declarant.DeclarantAuthenticationToken;
import com.idevhub.tapas.security.thirdapp.ThirdAppAuthenticationToken;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.idevhub.tapas.security.AuthoritiesConstants.*;

public abstract class BaseAuthTokenGranter extends ResourceOwnerPasswordTokenGranter {

    private final AuthenticationManager authenticationManager;
    private final String grandType;

    public BaseAuthTokenGranter(
        AuthenticationManager authenticationManager,
        AuthorizationServerTokenServices tokenServices,
        ClientDetailsService clientDetailsService,
        OAuth2RequestFactory requestFactory,
        String grandType
    ) {
        super(
            authenticationManager,
            tokenServices,
            clientDetailsService,
            requestFactory,
            grandType);

        this.grandType = grandType;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String username = parameters.get("username");
        String password = parameters.get("password");
        // Protect from downstream leaks of password
        parameters.remove("password");

        Authentication userAuth = createToken(username, password);

        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException ase) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new InvalidGrantException(ase.getMessage());
        } catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw new InvalidGrantException(e.getMessage());
        }

        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

    private Authentication createToken(String username, String password) {
        switch (grandType) {
            case DECLARANT_GRANT_TYPE:
                return new DeclarantAuthenticationToken(username, password);
            case BACK_GRANT_TYPE:
                return new BackOfficeAuthenticationToken(username, password);
            case CEA_GRANT_TYPE:
                return new CeaEmploeeAuthenticationToken(username, password);
            case THIRD_APP_GRANT_TYPE:
                return new ThirdAppAuthenticationToken(username, password);
        }
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
