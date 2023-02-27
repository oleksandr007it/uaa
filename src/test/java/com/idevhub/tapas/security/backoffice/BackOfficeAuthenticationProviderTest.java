package com.idevhub.tapas.security.backoffice;

import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.CustomUserDetails;
import com.idevhub.tapas.security.DomainUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class BackOfficeAuthenticationProviderTest {

    @Mock
    private DomainUserDetailsService mockUserDetailsService;

    private BackOfficeAuthenticationProvider backOfficeAuthenticationProviderUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        backOfficeAuthenticationProviderUnderTest = new BackOfficeAuthenticationProvider(mockUserDetailsService);
    }

    @Test
    void testAuthenticate() {

        // Setup


        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.BACK_OFFICE));

        User user = new User();
        user.setId(999L);

        CustomUserDetails customUserDetails = new CustomUserDetails("test", "user", true, true, true, true, authorities, user.getId());

        BackOfficeAuthenticationToken authentication =
            new BackOfficeAuthenticationToken("ewogICJybm9rcHAiIDogIjEwMTAxMDEwMTQiLAogICJlZHJwb3VDb2RlIiA6ICIzNDU1NDM2MiIsCiAgImZ1bGxOYW1lIiA6ICLQodC40LTQvtGA0LXQvdC60L4g0JLQsNGB0LjQu9GMINCb0LXQvtC90ZbQtNC+0LLQuNGHICjRgtC10YHRgikiLAogICJmaXJzdE5hbWUiIDogItCS0LDRgdC40LvRjCIsCiAgIm1pZGRsZU5hbWUiIDogItCb0LXQvtC90ZbQtNC+0LLQuNGHICjRgtC10YHRgikiLAogICJsYXN0TmFtZSIgOiAi0KHQuNC00L7RgNC10L3QutC+IiwKICAib3JnIiA6ICLQotC10YHRgtC+0LLQuNC5INC/0LvQsNGC0L3QuNC6IDMgKNCi0LXRgdGC0L7QstC40Lkg0YHQtdGA0YLQuNGE0ZbQutCw0YIpIiwKICAib3JnVW5pdCIgOiAiIiwKICAicG9zaXRpb24iIDogIiIsCiAgImVtYWlsIiA6ICJxYTAwNy50ZXN0MUBnbWFpbC5jb20iLAogICJwaG9uZSIgOiAiKzM4ICgwIDg4KSA4ODgtODYtMDkiCn0=", "password");


        when(backOfficeAuthenticationProviderUnderTest.tryToGetUserDetailsByAndEdrpouCodeAndUserType(anyString(), anyString(), anyString())).thenReturn(customUserDetails);


        backOfficeAuthenticationProviderUnderTest.authenticate(authentication);
        assertThat(user).isEqualTo(user);

    }


    @Test
    void testAuthenticate_ThrowsAuthenticationException() {


        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));

        User user = new User();
        user.setId(999L);

        CustomUserDetails customUserDetails = new CustomUserDetails("test", "user", true, true, true, true, authorities, user.getId());

        BackOfficeAuthenticationToken authentication =
            new BackOfficeAuthenticationToken("ewogICJybm9rcHAiIDogIjEwMTAxMDEwMTQiLAogICJlZHJwb3VDb2RlIiA6ICIzNDU1NDM2MiIsCiAgImZ1bGxOYW1lIiA6ICLQodC40LTQvtGA0LXQvdC60L4g0JLQsNGB0LjQu9GMINCb0LXQvtC90ZbQtNC+0LLQuNGHICjRgtC10YHRgikiLAogICJmaXJzdE5hbWUiIDogItCS0LDRgdC40LvRjCIsCiAgIm1pZGRsZU5hbWUiIDogItCb0LXQvtC90ZbQtNC+0LLQuNGHICjRgtC10YHRgikiLAogICJsYXN0TmFtZSIgOiAi0KHQuNC00L7RgNC10L3QutC+IiwKICAib3JnIiA6ICLQotC10YHRgtC+0LLQuNC5INC/0LvQsNGC0L3QuNC6IDMgKNCi0LXRgdGC0L7QstC40Lkg0YHQtdGA0YLQuNGE0ZbQutCw0YIpIiwKICAib3JnVW5pdCIgOiAiIiwKICAicG9zaXRpb24iIDogIiIsCiAgImVtYWlsIiA6ICJxYTAwNy50ZXN0MUBnbWFpbC5jb20iLAogICJwaG9uZSIgOiAiKzM4ICgwIDg4KSA4ODgtODYtMDkiCn0=", "password");


        when(backOfficeAuthenticationProviderUnderTest.tryToGetUserDetailsByAndEdrpouCodeAndUserType(anyString(), anyString(), anyString())).thenReturn(customUserDetails);


        // Run the test
        assertThatThrownBy(() -> {
            backOfficeAuthenticationProviderUnderTest.authenticate(authentication);
        }).isInstanceOf(BadCredentialsException.class).hasMessageContaining("BACK");
    }


    @Test
    void testSupports() {
        // Setup

        // Run the test
        final boolean result = backOfficeAuthenticationProviderUnderTest.supports(BackOfficeAuthenticationToken.class);

        // Verify the results
        assertThat(result).isTrue();
    }
}
