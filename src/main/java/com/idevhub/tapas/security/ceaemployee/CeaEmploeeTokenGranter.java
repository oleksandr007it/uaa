package com.idevhub.tapas.security.ceaemployee;

import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.BaseAuthTokenGranter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

public class CeaEmploeeTokenGranter extends BaseAuthTokenGranter {


    public CeaEmploeeTokenGranter(
        AuthenticationManager authenticationManager,
        AuthorizationServerTokenServices tokenServices,
        ClientDetailsService clientDetailsService
    ) {
        super(
            authenticationManager,
            tokenServices,
            clientDetailsService,
            new DefaultOAuth2RequestFactory(clientDetailsService),
            AuthoritiesConstants.CEA_GRANT_TYPE);

    }


}
