package com.idevhub.tapas.security.ceaemployee;

import com.idevhub.tapas.domain.constants.UserType;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.BaseAuthenticationProvider;
import com.idevhub.tapas.security.CustomUserDetails;
import com.idevhub.tapas.security.DomainUserDetailsService;
import com.idevhub.tapas.service.dto.UserDTOfromIdGovUa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;

@Component("сeaEmploeeAuthenticationProvider")
public class CeaEmploeeAuthenticationProvider extends BaseAuthenticationProvider {
    private final static String MESSAGE_FOR_LOG = "cea";
    Logger logger = LoggerFactory.getLogger(CeaEmploeeAuthenticationProvider.class);

    public CeaEmploeeAuthenticationProvider(DomainUserDetailsService userDetailsService) {
        super(userDetailsService);
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        logger.info("Start authenticate " + MESSAGE_FOR_LOG + "provider");

        String userInfo = authentication.getName();
        checkUserInfo(userInfo, MESSAGE_FOR_LOG);


        UserDTOfromIdGovUa userFromIdGovUaDTO = tryToGetUserInfo(userInfo, MESSAGE_FOR_LOG);
        String rnokpp = userFromIdGovUaDTO.getRnokpp();
        String edrpouCode = userFromIdGovUaDTO.getEdrpouCode();


        CustomUserDetails userDetails = tryToGetUserDetailsByAndEdrpouCodeAndUserType(rnokpp, edrpouCode, UserType.CEA_EMPLOYEE);

        Collection<GrantedAuthority> authorities = userDetails.getAuthorities();

        checkAuthorities(authorities, AuthoritiesConstants.CEA_EMPLOYEE);

        UsernamePasswordAuthenticationToken authenticationWithAuthorities = tryToMakeAuthenticationTokenAndAddUserIdToDetails(userDetails, authorities, authentication);


        return authenticationWithAuthorities;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CeaEmploeeAuthenticationToken.class);
    }
}
