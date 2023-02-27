package com.idevhub.tapas.security.thirdapp;

import com.idevhub.tapas.security.LdapUserDetails;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

public class PreAuthenticatedThirdAppAuthenticationProvider extends PreAuthenticatedAuthenticationProvider {
    Logger logger = LoggerFactory.getLogger(PreAuthenticatedThirdAppAuthenticationProvider.class);
    private final boolean throwExceptionWhenTokenRejected = true;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            return null;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("PreAuthenticated authentication request: " + authentication);
        }

        if (authentication.getPrincipal() == null) {
            logger.debug("No pre-authenticated principal found in request.");

            if (throwExceptionWhenTokenRejected) {
                throw new BadCredentialsException(
                    "No pre-authenticated principal found in request.");
            }
            return null;
        }

        if (authentication.getCredentials() == null) {
            logger.debug("No pre-authenticated credentials found in request.");

            if (throwExceptionWhenTokenRejected) {
                throw new BadCredentialsException(
                    "No pre-authenticated credentials found in request.");
            }
            return null;
        }

        val principal = authentication.getPrincipal();
        if ((principal instanceof UsernamePasswordAuthenticationToken)) {
            val usernamePasswordAuthenticationToken = ((UsernamePasswordAuthenticationToken) principal);
            val details = usernamePasswordAuthenticationToken.getPrincipal();
            if ((details instanceof LdapUserDetails)) {
                val ldapUserDetails = ((LdapUserDetails) usernamePasswordAuthenticationToken.getPrincipal());
                UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                    ldapUserDetails, authentication.getCredentials(), ldapUserDetails.getAuthorities());
                result.setDetails(authentication.getDetails());
                return result;
            }
        }
        return super.authenticate(authentication);
    }

}
