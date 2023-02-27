package com.idevhub.tapas.security.thirdapp;

import com.idevhub.tapas.security.LdapUserDetails;
import lombok.val;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

public class PreAuthenticatedThitdAppProvider extends PreAuthenticatedAuthenticationProvider {
    private static final Log logger = LogFactory
        .getLog(PreAuthenticatedThitdAppProvider.class);


    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("PreAuthenticated authentication request: " + authentication);
        }

        val principal = authentication.getPrincipal();
        if ((principal instanceof LdapUserDetails)) {
            logger.debug("PreAuthenticated authentication request: " + authentication);
        }
        return super.authenticate(authentication);
    }
}
