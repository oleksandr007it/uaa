package com.idevhub.tapas.config;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "external-oauth2-client", ignoreUnknownFields = false)
public class ExternalOAuth2AccessProperties {
  private String clientId;
  private String clientSecret;
  private String accessTokenUrl;
}
