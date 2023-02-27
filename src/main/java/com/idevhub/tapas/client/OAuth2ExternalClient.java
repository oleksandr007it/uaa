package com.idevhub.tapas.client;

import com.idevhub.tapas.config.ExternalOAuth2AccessProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OAuth2ExternalClient {
    private final RestTemplate restTemplate;
    private OAuth2AccessToken oAuth2AccessToken;
    private final ExternalOAuth2AccessProperties oAuth2Properties;

    public OAuth2ExternalClient(@Qualifier("vanillaRestTemplate") RestTemplate restTemplate, ExternalOAuth2AccessProperties oAuth2Properties) {
        this.restTemplate = restTemplate;
        this.oAuth2Properties = oAuth2Properties;
    }


    public String getExternalAccessToken() {
        if (oAuth2AccessToken == null || oAuth2AccessToken.isExpired()) {
            this.oAuth2AccessToken = tryToGetAccessToken();
        }
        return oAuth2AccessToken.getValue();
    }

    private OAuth2AccessToken tryToGetAccessToken() {

        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.set("client_id", oAuth2Properties.getClientId());
        formParams.set("client_secret", oAuth2Properties.getClientSecret());
        formParams.set("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formParams, reqHeaders);
        log.debug("contacting OAuth2 token endpoint to login user");
        ResponseEntity<OAuth2AccessToken>
            responseEntity = restTemplate.postForEntity(getTokenEndpoint(), entity, OAuth2AccessToken.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.debug("failed to authenticate user with OAuth2 token endpoint, status: {}", responseEntity.getStatusCodeValue());
            throw new HttpClientErrorException(responseEntity.getStatusCode());
        }
        OAuth2AccessToken accessToken = responseEntity.getBody();
        return accessToken;
    }

    protected String getTokenEndpoint() {
        String tokenEndpointUrl = oAuth2Properties.getAccessTokenUrl();
        if (tokenEndpointUrl == null) {
            throw new InvalidClientException("no token endpoint configured in application properties");
        }
        return tokenEndpointUrl;
    }
}
