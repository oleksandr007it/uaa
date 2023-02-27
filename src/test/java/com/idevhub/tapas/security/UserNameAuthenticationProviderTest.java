package com.idevhub.tapas.security;

import com.idevhub.tapas.domain.*;
import com.idevhub.tapas.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class UserNameAuthenticationProviderTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserDetailsService mockUserDetailsService;

    private UserNameAuthenticationProvider userNameAuthenticationProviderUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        userNameAuthenticationProviderUnderTest = new UserNameAuthenticationProvider(mockUserRepository, mockUserDetailsService);
    }

    @Test
    void testAuthenticate() {
        // Setup

        User user = new User();
        user.setId(999L);
        user.setLogin("test");
        user.setActivated(true);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("user"));

        Optional<User> optionalUser=Optional.of(user);

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.BACK_OFFICE));

        CustomUserDetails customUserDetails = new CustomUserDetails("test", "user", true, true, true, true, authorities, user.getId());

        when(mockUserRepository.findOneWithAuthoritiesByLogin(anyString())).thenReturn(optionalUser);

        when(mockUserDetailsService.loadUserByUsername(anyString())).thenReturn(customUserDetails);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken("test", "user");
        // Run the test
        final Authentication result = userNameAuthenticationProviderUnderTest.authenticate(usernamePasswordAuthenticationToken);
        // Verify the results
        CustomUserDetails  expectedResult = (CustomUserDetails) result.getPrincipal();
        assertThat(user.getLogin()).isEqualTo(expectedResult.getUsername());

    }

    @Test
    void testAuthenticate_UserDetailsServiceThrowsUsernameNotFoundException() {

        User user = new User();
        user.setId(999L);
        user.setLogin("test");

        Optional<User> optionalUser=Optional.of(user);

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.BACK_OFFICE));

        CustomUserDetails customUserDetails = new CustomUserDetails("test", "user", true, true, true, true, authorities, user.getId());

        when(mockUserRepository.findOneWithAuthoritiesByLogin(eq("test"))).thenReturn(optionalUser);

        when(mockUserDetailsService.loadUserByUsername(anyString())).thenReturn(customUserDetails);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken("user", "user");

        assertThatThrownBy(() -> {
            userNameAuthenticationProviderUnderTest.authenticate(usernamePasswordAuthenticationToken);
        }).isInstanceOf(BadCredentialsException.class).hasMessageContaining("User  not found in DB");


        when(mockUserRepository.findOneWithAuthoritiesByLogin(anyString())).thenReturn(optionalUser);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken2=new UsernamePasswordAuthenticationToken("test", "user");


        assertThatThrownBy(() -> {
            userNameAuthenticationProviderUnderTest.authenticate(usernamePasswordAuthenticationToken);
        }).isInstanceOf(BadCredentialsException.class).hasMessageContaining("User is not activated");


        user.setActivated(true);

        assertThatThrownBy(() -> {
            userNameAuthenticationProviderUnderTest.authenticate(usernamePasswordAuthenticationToken);
        }).isInstanceOf(BadCredentialsException.class).hasMessageContaining("Wrong login or password");




    }

    @Test
    void testSupports() {
        // Setup

        // Run the test
        final boolean result = userNameAuthenticationProviderUnderTest.supports(UsernamePasswordAuthenticationToken.class);

        // Verify the results
        assertThat(result).isTrue();
    }
}
