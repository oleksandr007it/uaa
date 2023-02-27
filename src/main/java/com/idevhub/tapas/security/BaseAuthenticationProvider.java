package com.idevhub.tapas.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idevhub.tapas.service.dto.UserDTOfromIdGovUa;
import com.idevhub.tapas.web.rest.errors.ErrorConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

import static com.idevhub.tapas.security.AuthoritiesConstants.USER_ID_KEY;

public abstract class BaseAuthenticationProvider implements AuthenticationProvider {
    private final DomainUserDetailsService userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(BaseAuthenticationProvider.class);

    protected BaseAuthenticationProvider(DomainUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private static Stream<String> getAuthorities(Collection<GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority);
    }

    public CustomUserDetails tryToGetUserDetailsByRnokppAndUserType(final String rnokpp, final String userType) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByRnokppAndUserType(rnokpp, userType);
            return userDetails;
        } catch (UsernameNotFoundException e) {
            logger.error(e.getMessage());
            throw new UsernameNotFoundException(ErrorConstants.USER_NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }
    }

    protected UserDTOfromIdGovUa tryToGetUserInfo(String userInfo, String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] decodedBytes = Base64.getDecoder().decode(userInfo);
        String userDTOfromIdGovUaJson = new String(decodedBytes);
        UserDTOfromIdGovUa userFromIdGovUaDTO;
        try {
            userFromIdGovUaDTO = objectMapper.readValue(userDTOfromIdGovUaJson, UserDTOfromIdGovUa.class);
        } catch (Exception x) {
            logger.error("Can not parse " + message + " user get from gateway" + x.getMessage());
            throw new BadCredentialsException("Can not parse " + message + " user get from gateway");
        }
        return userFromIdGovUaDTO;
    }

    protected void checkAuthorities(Collection<GrantedAuthority> authorities, String authority) {
        logger.info("Start authenticate checkAuthorities");
        if (getAuthorities(authorities).noneMatch(authority::equals)) {
            logger.error("Can not have Authority " + authority + " for Authorization");
            throw new BadCredentialsException("Can not have Authority " + authority + " for Authorization");
        }

    }

    protected UsernamePasswordAuthenticationToken tryToMakeAuthenticationTokenAndAddUserIdToDetails(CustomUserDetails userDetails, Collection<GrantedAuthority> authorities, Authentication authentication) {
        UsernamePasswordAuthenticationToken authenticationWithAuthorities =
            new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), authorities);
        addUserIdToDetails(authenticationWithAuthorities, userDetails);
        logger.info(userDetails.getUsername() + " Authentication success. Authorities: " + Arrays.toString(authentication.getAuthorities().toArray()));
        return authenticationWithAuthorities;
    }

    protected void checkUserInfo(String userInfo, String message) {
        logger.info("Start checkUserInfo");
        if (userInfo == null || "".equals(userInfo)) {
            logger.error("UserInfo" + message + " must not be empty");
            throw new BadCredentialsException("UserInfo " + message + " must not be empty");
        }

    }

    public CustomUserDetails tryToGetUserDetailsByAndEdrpouCodeAndUserType(final String rnokpp, final String edrpouCode, final String userType) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByRnokppAndEdrpouCodeAndUserType(rnokpp, edrpouCode, userType);
            return userDetails;
        } catch (UsernameNotFoundException e) {
            logger.error(e.getMessage());
            throw new UsernameNotFoundException(ErrorConstants.USER_NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }
    }

    public LdapUserDetails tryToGetUserDetailsByLdap(UserDTOfromIdGovUa user) {
        try {
            LdapUserDetails userDetails = (LdapUserDetails) userDetailsService.loadUserByLdap(user);
            return userDetails;
        } catch (UsernameNotFoundException e) {
            logger.error(e.getMessage());
            throw new UsernameNotFoundException(ErrorConstants.USER_NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }
    }

    protected void addUserIdToDetails(
        UsernamePasswordAuthenticationToken authenticationWithAuthorities,
        CustomUserDetails userDetails) {
        LinkedHashMap details = new LinkedHashMap();
        details.put(USER_ID_KEY, userDetails.getUsetId());
        authenticationWithAuthorities.setDetails(details);
    }


}
