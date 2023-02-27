package com.idevhub.tapas.config;

import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.CustomAccessTokenConverter;
import com.idevhub.tapas.security.JwtCustomHeadersAccessTokenConverter;
import com.idevhub.tapas.security.TokenServices;
import com.idevhub.tapas.security.backoffice.BackOfficeTokenGranter;
import com.idevhub.tapas.security.ceaemployee.CeaEmploeeTokenGranter;
import com.idevhub.tapas.security.declarant.DeclarantTokenGranter;
import com.idevhub.tapas.security.thirdapp.PreAuthenticatedThirdAppAuthenticationProvider;
import com.idevhub.tapas.security.thirdapp.ThirdAppTokenGranter;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import io.github.jhipster.config.JHipsterProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

@SuppressWarnings("unused")
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class UaaConfiguration extends AuthorizationServerConfigurerAdapter implements ApplicationContextAware {
    /**
     * Access tokens will not expire any earlier than this.
     */
    private static final int MIN_ACCESS_TOKEN_VALIDITY_SECS = 60;
    private final CustomAccessTokenConverter customAccessTokenConverter;
    private final JHipsterProperties jHipsterProperties;
    private final UaaProperties uaaProperties;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private ApplicationContext applicationContext;
    private static final String JWK_KID = "customs-key-id-v1";
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        int accessTokenValidity = uaaProperties.getWebClientConfiguration().getAccessTokenValidityInSeconds();
        accessTokenValidity = Math.max(accessTokenValidity, MIN_ACCESS_TOKEN_VALIDITY_SECS);
        int refreshTokenValidity = uaaProperties.getWebClientConfiguration().getRefreshTokenValidityInSecondsForRememberMe();
        refreshTokenValidity = Math.max(refreshTokenValidity, accessTokenValidity);
        /*
        For a better client design, this should be done by a ClientDetailsService (similar to UserDetailsService).
         */

        clients.jdbc(dataSource);

    }

    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //pick up all  TokenEnhancers incl. those defined in the application
        //this avoids changes to this class if an application wants to add its own to the chain

        TokenGranter defaultTokenGranter = endpoints.getTokenGranter();

        List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
        tokenGranters.add(defaultTokenGranter);
        tokenGranters.add(new CeaEmploeeTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService()));
        tokenGranters.add(new DeclarantTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService()));
        tokenGranters.add(new BackOfficeTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService()));
        tokenGranters.add(new ThirdAppTokenGranter(authenticationManager, endpoints.getTokenServices(), endpoints.getClientDetailsService()));
        TokenGranter tokenGranter = new CompositeTokenGranter(tokenGranters);


        Collection<TokenEnhancer> tokenEnhancers = applicationContext.getBeansOfType(TokenEnhancer.class).values();
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(new ArrayList<>(tokenEnhancers));
        endpoints
            .authorizationCodeServices(authorizationCodeServices())
            .approvalStore(approvalStore())
            .authenticationManager(authenticationManager)
            .tokenStore(tokenStore())
            .tokenEnhancer(tokenEnhancerChain)
            .tokenGranter(tokenGranter)
            .userDetailsService(userDetailsService)
            .tokenServices(tokenServices(tokenEnhancerChain, endpoints.getClientDetailsService()))
            .reuseRefreshTokens(false);             //don't reuse or we will run into session inactivity timeouts
    }

    @Bean
    @Primary
    public AuthorizationServerTokenServices tokenServices(TokenEnhancerChain tokenEnhancerChain, ClientDetailsService clientDetailsService) {
        TokenServices defaultTokenServices = new TokenServices();
        defaultTokenServices.setReuseRefreshToken(false);
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
        defaultTokenServices.setClientDetailsService(clientDetailsService);
        addUserDetailsService(defaultTokenServices, userDetailsService);
        return defaultTokenServices;
    }

    private void addUserDetailsService(TokenServices tokenServices, UserDetailsService userDetailsService) {
        if (userDetailsService != null) {
            PreAuthenticatedThirdAppAuthenticationProvider provider = new PreAuthenticatedThirdAppAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>(
                userDetailsService));
            tokenServices
                .setAuthenticationManager(new ProviderManager(Arrays.asList(provider)));
        }
    }

    @Bean
    public TokenStore tokenStore() {
        JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
        return jdbcTokenStore;
    }

    /**
     * This bean generates an token enhancer, which manages the exchange between JWT access tokens and Authentication
     * in both directions.
     *
     * @return an access token converter configured with the authorization server's public/private keys.
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        KeyPair keyPair = keyPair();
        Map<String, String> customHeaders = Collections.singletonMap("kid", JWK_KID);
        JwtAccessTokenConverter converter = new JwtCustomHeadersAccessTokenConverter(customHeaders, keyPair);
        converter.setAccessTokenConverter(customAccessTokenConverter);
        converter.setKeyPair(keyPair);
        return converter;
    }

    @Bean
    public KeyPair keyPair() {
        KeyPair keyPair = new KeyStoreKeyFactory(
            new ClassPathResource(uaaProperties.getKeyStore().getName()), uaaProperties.getKeyStore().getPassword().toCharArray())
            .getKeyPair(uaaProperties.getKeyStore().getAlias());
        return keyPair;
    }

    @Bean
    public JWKSet jwkSet() {
        RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic()).keyUse(KeyUse.SIGNATURE)
            .algorithm(JWSAlgorithm.RS256)
            .keyID(JWK_KID);
        return new JWKSet(builder.build());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess(
            "isAuthenticated()");
    }

    @EnableResourceServer
    @RequiredArgsConstructor
    public static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        private final TokenStore tokenStore;

        private final JHipsterProperties jHipsterProperties;

        private final CorsFilter corsFilter;

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .csrf()
                .disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .headers()
                .frameOptions()
                .disable()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/activate").permitAll()
                .antMatchers("/api/oauth2/jwks").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/account/reset-password/init").permitAll()
                .antMatchers("/api/account/reset-password/finish").permitAll()
                .antMatchers("/api/email-confirmations/confirm").permitAll()
                .antMatchers("/api/statehood-subjects/confirm-email").permitAll()
                .antMatchers("/api/users/confirm-email").permitAll()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/management/health").permitAll()
                .antMatchers("/management/prometheus").permitAll()
                .antMatchers("/management/info").permitAll()
                .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                .antMatchers("/v2/api-docs/**").permitAll()
                .antMatchers("/swagger-resources/configuration/ui").permitAll()
                .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN);
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId("jhipster-uaa").tokenStore(tokenStore);
        }
    }
}
