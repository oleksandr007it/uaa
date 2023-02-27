package com.idevhub.tapas.config;

import com.idevhub.tapas.security.UserNameAuthenticationProvider;
import com.idevhub.tapas.security.backoffice.BackOfficeAuthenticationProvider;
import com.idevhub.tapas.security.ceaemployee.CeaEmploeeAuthenticationProvider;
import com.idevhub.tapas.security.declarant.DeclarantAuthenticationProvider;
import com.idevhub.tapas.security.thirdapp.ThirdAppAuthenticationProvider;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
public class UaaWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final DeclarantAuthenticationProvider declarantAuthenticationProvider;
    private final UserNameAuthenticationProvider userNameAuthenticationProvider;
    private final BackOfficeAuthenticationProvider backOfficeAuthenticationProvider;
    private final ThirdAppAuthenticationProvider thirdAppAuthenticationProvider;
    private final CeaEmploeeAuthenticationProvider ceaEmploeeAuthenticationProvider;

    public UaaWebSecurityConfiguration(UserDetailsService userDetailsService, AuthenticationManagerBuilder authenticationManagerBuilder, DeclarantAuthenticationProvider declarantAuthenticationProvider, UserNameAuthenticationProvider userNameAuthenticationProvider, BackOfficeAuthenticationProvider backOfficeAuthenticationProvider, ThirdAppAuthenticationProvider thirdAppAuthenticationProvider, CeaEmploeeAuthenticationProvider ceaEmploeeAuthenticationProvider) {
        this.userDetailsService = userDetailsService;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.declarantAuthenticationProvider = declarantAuthenticationProvider;
        this.userNameAuthenticationProvider = userNameAuthenticationProvider;
        this.backOfficeAuthenticationProvider = backOfficeAuthenticationProvider;
        this.thirdAppAuthenticationProvider = thirdAppAuthenticationProvider;
        this.ceaEmploeeAuthenticationProvider = ceaEmploeeAuthenticationProvider;
    }

    @PostConstruct
    public void init() throws Exception {
        try {
            authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new ProviderManager(Arrays.asList(declarantAuthenticationProvider, userNameAuthenticationProvider, backOfficeAuthenticationProvider, thirdAppAuthenticationProvider, ceaEmploeeAuthenticationProvider));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**")
            .antMatchers("/h2-console/**");
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Bean
    @Qualifier("vanillaRestTemplate")
    public RestTemplate vanillaRestTemplate() {
        return new RestTemplate();
    }
}
