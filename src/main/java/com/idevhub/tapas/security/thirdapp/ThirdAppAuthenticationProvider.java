package com.idevhub.tapas.security.thirdapp;

import com.idevhub.tapas.security.BaseAuthenticationProvider;
import com.idevhub.tapas.security.DomainUserDetailsService;
import com.idevhub.tapas.security.LdapUserDetails;
import com.idevhub.tapas.service.dto.UserDTOfromIdGovUa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;

@Component("thirdAppAuthenticationProvider")
public class ThirdAppAuthenticationProvider extends BaseAuthenticationProvider {
    private final static String MESSAGE_FOR_LOG = "thirdApp";
    Logger logger = LoggerFactory.getLogger(ThirdAppAuthenticationProvider.class);

    public ThirdAppAuthenticationProvider(DomainUserDetailsService userDetailsService) {
        super(userDetailsService);
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        logger.info("Start authenticate " + MESSAGE_FOR_LOG + "provider");

        String userInfo = authentication.getName();
        String password = (String) authentication.getCredentials();
        checkUserInfo(userInfo, MESSAGE_FOR_LOG);
        UserDTOfromIdGovUa userFromIdGovUaDTO = tryToGetUserInfo(userInfo, MESSAGE_FOR_LOG);
        LdapUserDetails userDetails = tryToGetUserDetailsByLdap(userFromIdGovUaDTO);
        userDetails.setNonce(password);


        UsernamePasswordAuthenticationToken authenticationWithAuthorities =
            new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
        logger.info("Authentication success. Authorities: " + Arrays.toString(authentication.getAuthorities().toArray()));

        return authenticationWithAuthorities;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(ThirdAppAuthenticationToken.class);
    }
}
