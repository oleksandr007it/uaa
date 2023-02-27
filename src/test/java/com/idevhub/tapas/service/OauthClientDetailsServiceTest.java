package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.OauthClientDetails;
import com.idevhub.tapas.repository.OauthClientDetailsRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class OauthClientDetailsServiceTest {

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @Test
    public void testSave() {
        final OauthClientDetails actualClientDetails = getStubOauthClientDetails();
        final OauthClientDetails expectedOauthClientDetails = oauthClientDetailsService.save(actualClientDetails);

        assertThat(expectedOauthClientDetails).isNotNull();
        assertThat(expectedOauthClientDetails.getClientUrl()).isNotEmpty();
        assertThat(expectedOauthClientDetails.getClientSecret()).isNotEmpty();
    }

    @Test
    public void testFindAll() {
        final OauthClientDetails clientDetails = getStubOauthClientDetails();
        oauthClientDetailsRepository.saveAndFlush(clientDetails);

        final PageRequest pageRequest = PageRequest.of(0, 1);

        final List<OauthClientDetails> result = oauthClientDetailsService.findAll(pageRequest).getContent();

        assertThat(result).hasSizeGreaterThanOrEqualTo(1);
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getClientUrl()).isNotEmpty();
        assertThat(result.get(0).getClientSecret()).isNotEmpty();
    }

    public OauthClientDetails getStubOauthClientDetails() {
        final OauthClientDetails oauthClientDetails = new OauthClientDetails();
        oauthClientDetails.setId(1L);
        oauthClientDetails.setClientId("client_id");
        oauthClientDetails.setResourceIds("resource_ids");
        oauthClientDetails.setClientSecret("client_secret");
        oauthClientDetails.setScope("scope");
        oauthClientDetails.setAuthorizedGrantTypes("auth_grant_type");
        oauthClientDetails.setWebServerRedirectUri("http://link.com");
        oauthClientDetails.setAuthorities("authorities");
        oauthClientDetails.setAccessTokenValidity(1);
        oauthClientDetails.setRefreshTokenValidity(2);
        oauthClientDetails.setAdditionalInformation("additional_info");
        oauthClientDetails.setAutoapprove("auto_approve");
        oauthClientDetails.setClientUrl("http://client.com");

        return oauthClientDetails;
    }
}
