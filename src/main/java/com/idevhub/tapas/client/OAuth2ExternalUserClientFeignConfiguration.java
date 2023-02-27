package com.idevhub.tapas.client;

import org.springframework.context.annotation.Bean;


public class OAuth2ExternalUserClientFeignConfiguration {

    @Bean(name = "externalUserFeignClientInterceptor")
    public ExternalUserFeignClientInterceptor getExternalUserFeignClientInterceptor() {
        return new ExternalUserFeignClientInterceptor();
    }
}
