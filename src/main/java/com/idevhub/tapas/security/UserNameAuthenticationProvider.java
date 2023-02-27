package com.idevhub.tapas.security;


import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;


@Component("userNameAuthenticationProvider")
public class UserNameAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    Logger logger = LoggerFactory.getLogger(UserNameAuthenticationProvider.class);

    public UserNameAuthenticationProvider(UserRepository userRepository,
                                          UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userDetailsService = userDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        String login = authentication.getName();
        String password = (String) authentication.getCredentials();
        if (login == null || "".equals(login) || password == null || "".equals(password)) {
            throw new BadCredentialsException("Login and password must not be empty");
        }
        User user = userRepository.findOneWithAuthoritiesByLogin(login).orElse(null);
        if (user == null) {
            logger.warn("User {} not found in DB", login);
            throw new BadCredentialsException("User  not found in DB");
        }
        if (!user.isActivated()) {
            throw new BadCredentialsException("User is not activated");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong login or password");
        }

        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService
            .loadUserByUsername(login);

        Collection<GrantedAuthority> authorities = userDetails.getAuthorities();

        UsernamePasswordAuthenticationToken authenticationWithAuthorities =
            new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(),
                authorities);
        addUserIdToDetails(authenticationWithAuthorities, userDetails);
        logger.info("Authentication success. Authorities: " + Arrays
            .toString(authentication.getAuthorities().toArray()));
        return authenticationWithAuthorities;
    }

    private void addUserIdToDetails(
        UsernamePasswordAuthenticationToken authenticationWithAuthorities,
        CustomUserDetails userDetails) {
        LinkedHashMap details = new LinkedHashMap();
        details.put("userId", userDetails.getUsetId());
        authenticationWithAuthorities.setDetails(details);
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
