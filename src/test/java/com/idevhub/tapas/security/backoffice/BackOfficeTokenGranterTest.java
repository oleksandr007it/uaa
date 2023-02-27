package com.idevhub.tapas.security.backoffice;

import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class BackOfficeTokenGranterTest {

    @Mock
    private AuthenticationManager mockAuthenticationManager;
    @Mock
    private AuthorizationServerTokenServices mockTokenServices;
    @Mock
    private ClientDetailsService mockClientDetailsService;

    private BackOfficeTokenGranter backOfficeTokenGranterUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        backOfficeTokenGranterUnderTest = new BackOfficeTokenGranter(mockAuthenticationManager, mockTokenServices, mockClientDetailsService);
    }

    @Test
    void getOAuth2Authentication() {
        Map<String,String> map = new HashMap<>();
        map.put("username","username");
        map.put("password","password");
        Collection<String> scope = new ArrayList<>();
        ClientDetails client=new BaseClientDetails();
        TokenRequest tokenRequest= new TokenRequest(map,"clientId",scope,"back_office");
        User user = new User();
        user.setId(999L);
        user.setLogin("test");
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.BACK_OFFICE));

        CustomUserDetails customUserDetails = new CustomUserDetails("test", "user", true, true, true, true, authorities, user.getId());
        UsernamePasswordAuthenticationToken authenticationWithAuthorities =
            new UsernamePasswordAuthenticationToken(customUserDetails, "user", authorities);


        when(mockAuthenticationManager.authenticate(any(Authentication.class))).thenReturn(authenticationWithAuthorities);
        OAuth2Authentication result= backOfficeTokenGranterUnderTest.getOAuth2Authentication(client,tokenRequest);

        assertThat(result.getUserAuthentication()).isEqualTo(authenticationWithAuthorities);

    }



    @Test
    void getOAuth2AuthenticationEX() {
        Map<String,String> map = new HashMap<>();
        map.put("username","username");
        map.put("password","password");
        Collection<String> scope = new ArrayList<>();
        ClientDetails client=new BaseClientDetails();
        TokenRequest tokenRequest= new TokenRequest(map,"clientId",scope,"back_office");
        assertThatThrownBy(() -> {
            backOfficeTokenGranterUnderTest.getOAuth2Authentication(client,tokenRequest);
        }).isInstanceOf( InvalidGrantException.class).hasMessageContaining("Could not authenticate");

    }


    @Test
    void getOAuth2AuthenticationEX2() {
        Map<String,String> map = new HashMap<>();
        map.put("username","username");
        map.put("password","password");
        Collection<String> scope = new ArrayList<>();
        ClientDetails client=new BaseClientDetails();
        when(mockAuthenticationManager.authenticate(any(Authentication.class))).thenThrow(new BadCredentialsException("Error occurred"));
        TokenRequest tokenRequest= new TokenRequest(map,"clientId",scope,"back_office");
        assertThatThrownBy(() -> {
            backOfficeTokenGranterUnderTest.getOAuth2Authentication(client,tokenRequest);
        }).isInstanceOf( InvalidGrantException.class).hasMessageContaining("Error occurred");

    }



    @Test
    void getOAuth2AuthenticationEX3() {
        Map<String,String> map = new HashMap<>();
        map.put("username","username");
        map.put("password","password");
        Collection<String> scope = new ArrayList<>();
        ClientDetails client=new BaseClientDetails();
        when(mockAuthenticationManager.authenticate(any(Authentication.class))).thenThrow(new CredentialsExpiredException("Error CredentialsExpiredException"));
        TokenRequest tokenRequest= new TokenRequest(map,"clientId",scope,"back_office");
        assertThatThrownBy(() -> {
            backOfficeTokenGranterUnderTest.getOAuth2Authentication(client,tokenRequest);
        }).isInstanceOf( InvalidGrantException.class).hasMessageContaining("Error CredentialsExpiredException");

    }

}
// when(dictMock.getMeaning(anyString()))
//      .thenThrow(new NullPointerException("Error occurred"));
