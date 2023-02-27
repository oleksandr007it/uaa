package com.idevhub.tapas.security.declarant;

import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.CustomUserDetails;
import com.idevhub.tapas.security.DomainUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class DeclarantAuthenticationProviderTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private DomainUserDetailsService mockUserDetailsService;
    @Mock
    private CacheManager cacheManager;

    private DeclarantAuthenticationProvider declarantAuthenticationProviderUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        declarantAuthenticationProviderUnderTest = new DeclarantAuthenticationProvider(mockUserRepository, mockUserDetailsService, cacheManager);
    }

    @Test
    void testAuthenticate() {

        // Setup


        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.DECLARANT));

        User user = new User();
        user.setId(999L);
        user.setActivated(true);

        CustomUserDetails customUserDetails = new CustomUserDetails("test", "user", true, true, true, true, authorities, user.getId());

        DeclarantAuthenticationToken authentication =
            new DeclarantAuthenticationToken("eyJybm9rcHAiOiI2NjY1NTU2NjU1IiwiZWRycG91Q29kZSI6bnVsbCwiZnVsbE5hbWUiOiLQqNCy0YvQtNCw0L3QtdC90LrQviDQkNC70LXQutGB0LDQvdC00YAiLCJmaXJzdE5hbWUiOiLQkNC70LXQutGB0LDQvdC00YAiLCJtaWRkbGVOYW1lIjoiIiwibGFzdE5hbWUiOiLQqNCy0YvQtNCw0L3QtdC90LrQviIsIm9yZyI6ItCo0LLRi9C00LDQvdC10L3QutC+INCQ0LvQtdC60YHQsNC90LTRgCIsIm9yZ1VuaXQiOm51bGwsInBvc2l0aW9uIjoi0KTRltC30LjRh9C90LAg0L7RgdC+0LHQsCIsImVtYWlsIjoiIiwicGhvbmUiOiIrMzggKDAgNjMpIDI2MC01MS0xMiJ9", "password");

        Optional<User> user1=Optional.empty();

        when(declarantAuthenticationProviderUnderTest.tryToGetUserDetailsByRnokppAndUserType(anyString(),anyString())).thenReturn(customUserDetails);

        when(mockUserRepository.findOneByRnokppAndUserType("rnokpp", "userType")).thenReturn(user1);
        when(mockUserRepository.saveAndFlush(any())).thenReturn(user);

        declarantAuthenticationProviderUnderTest.authenticate(authentication);


    }


    @Test
    void testAuthenticate_ThrowsAuthenticationException() {


        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));

        User user = new User();
        user.setId(999L);
        user.setActivated(true);

        CustomUserDetails customUserDetails = new CustomUserDetails("test", "user", true, true, true, true, authorities, user.getId());

        DeclarantAuthenticationToken authentication =
            new DeclarantAuthenticationToken("eyJybm9rcHAiOiI2NjY1NTU2NjU1IiwiZWRycG91Q29kZSI6bnVsbCwiZnVsbE5hbWUiOiLQqNCy0YvQtNCw0L3QtdC90LrQviDQkNC70LXQutGB0LDQvdC00YAiLCJmaXJzdE5hbWUiOiLQkNC70LXQutGB0LDQvdC00YAiLCJtaWRkbGVOYW1lIjoiIiwibGFzdE5hbWUiOiLQqNCy0YvQtNCw0L3QtdC90LrQviIsIm9yZyI6ItCo0LLRi9C00LDQvdC10L3QutC+INCQ0LvQtdC60YHQsNC90LTRgCIsIm9yZ1VuaXQiOm51bGwsInBvc2l0aW9uIjoi0KTRltC30LjRh9C90LAg0L7RgdC+0LHQsCIsImVtYWlsIjoiIiwicGhvbmUiOiIrMzggKDAgNjMpIDI2MC01MS0xMiJ9", "password");

        Optional<User> user1=Optional.empty();

        when(declarantAuthenticationProviderUnderTest.tryToGetUserDetailsByRnokppAndUserType(anyString(),anyString())).thenReturn(customUserDetails);

        when(mockUserRepository.findOneByRnokppAndUserType("rnokpp", "userType")).thenReturn(user1);
        when(mockUserRepository.saveAndFlush(any())).thenReturn(user);


        // Run the test
        assertThatThrownBy(() -> {
            declarantAuthenticationProviderUnderTest.authenticate(authentication);
        }).isInstanceOf(BadCredentialsException.class).hasMessageContaining("DECLARANT");
    }


    @Test
    void testSupports() {
        // Setup

        // Run the test
        final boolean result = declarantAuthenticationProviderUnderTest.supports(DeclarantAuthenticationToken.class);

        // Verify the results
        assertThat(result).isTrue();
    }
}
