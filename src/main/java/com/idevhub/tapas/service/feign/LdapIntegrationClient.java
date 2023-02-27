package com.idevhub.tapas.service.feign;

import com.idevhub.tapas.domain.address.Country;
import com.idevhub.tapas.service.dto.UserFromLdapDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ldapIntegrationClient", url = "${ldap.integration.url}")
public interface LdapIntegrationClient {

    @GetMapping("/api/ad/users/by-inn/{inn}")
    List<UserFromLdapDTO> getUserFromLdap (@RequestParam("inn") String rnokpp);


}
