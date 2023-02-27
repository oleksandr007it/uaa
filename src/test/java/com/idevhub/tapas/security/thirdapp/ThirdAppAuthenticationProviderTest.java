package com.idevhub.tapas.security.thirdapp;

import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.security.DomainUserDetailsService;
import com.idevhub.tapas.security.LdapUserDetails;
import com.idevhub.tapas.service.dto.UserDTOfromIdGovUa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ThirdAppAuthenticationProviderTest {


    @Mock
    private DomainUserDetailsService mockUserDetailsService;

    private ThirdAppAuthenticationProvider thirdAppAuthenticationProviderUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        thirdAppAuthenticationProviderUnderTest = new ThirdAppAuthenticationProvider(mockUserDetailsService);
    }

    @Test
    void testAuthenticate() {

        // Setup


        UserDTOfromIdGovUa userFromIdGovUaDTO = new UserDTOfromIdGovUa();

        LdapUserDetails ldapUserDetails = new LdapUserDetails("test", userFromIdGovUaDTO, true, true, true, true, "authorities, user", "organization", "customsBodyCode", "employeeIdCardNumber", "ldapUserDirectoryId");

        ThirdAppAuthenticationToken authentication =
            new ThirdAppAuthenticationToken("eyJybm9rcHAiOiI2NjY1NTU2NjU1IiwiZWRycG91Q29kZSI6bnVsbCwiZnVsbE5hbWUiOiLQqNCy0YvQtNCw0L3QtdC90LrQviDQkNC70LXQutGB0LDQvdC00YAiLCJmaXJzdE5hbWUiOiLQkNC70LXQutGB0LDQvdC00YAiLCJtaWRkbGVOYW1lIjoiIiwibGFzdE5hbWUiOiLQqNCy0YvQtNCw0L3QtdC90LrQviIsIm9yZyI6ItCo0LLRi9C00LDQvdC10L3QutC+INCQ0LvQtdC60YHQsNC90LTRgCIsIm9yZ1VuaXQiOm51bGwsInBvc2l0aW9uIjoi0KTRltC30LjRh9C90LAg0L7RgdC+0LHQsCIsImVtYWlsIjoiIiwicGhvbmUiOiIrMzggKDAgNjMpIDI2MC01MS0xMiJ9",
                "password");


        when(thirdAppAuthenticationProviderUnderTest.tryToGetUserDetailsByLdap(any(UserDTOfromIdGovUa.class))).thenReturn(ldapUserDetails);

        Authentication result = thirdAppAuthenticationProviderUnderTest.authenticate(authentication);

        assertThat(result.getCredentials()).isEqualTo("password");
    }


    @Test
    void testAuthenticate_ThrowsAuthenticationException() {


        UserDTOfromIdGovUa userFromIdGovUaDTO = new UserDTOfromIdGovUa();

        LdapUserDetails ldapUserDetails = new LdapUserDetails("test", userFromIdGovUaDTO, true, true, true, true, "authorities, user", "organization", "customsBodyCode", "employeeIdCardNumber", "ldapUserDirectoryId");

        ThirdAppAuthenticationToken authentication =
            new ThirdAppAuthenticationToken("", "password");

        Optional<User> user1 = Optional.empty();

        when(thirdAppAuthenticationProviderUnderTest.tryToGetUserDetailsByLdap(any(UserDTOfromIdGovUa.class))).thenReturn(ldapUserDetails);



        // Run the test
        assertThatThrownBy(() -> {
            thirdAppAuthenticationProviderUnderTest.authenticate(authentication);

        }).isInstanceOf(BadCredentialsException.class);
    }


    @Test
    void testSupports() {
        // Setup

        // Run the test
        final boolean result = thirdAppAuthenticationProviderUnderTest.supports(ThirdAppAuthenticationToken.class);

        // Verify the results
        assertThat(result).isTrue();
    }
}
