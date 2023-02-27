package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.repository.Oaus2ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2")
public class OauthResource {
    private final Oaus2ClientRepository oaus2ClientRepository;

    public OauthResource(Oaus2ClientRepository oaus2ClientRepository) {
        this.oaus2ClientRepository = oaus2ClientRepository;
    }


    @GetMapping("/oauthclient/{clientId}")
    public boolean checkOauthClient(@PathVariable String clientId) {
        return oaus2ClientRepository.findOneByClientId(clientId).isPresent();
    }


}
