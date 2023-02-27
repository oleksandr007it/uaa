package com.idevhub.tapas.privilege.service.client;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@AuthorizedFeignClient(name = "uaa", url = "uaa")
public interface UaaClient {
    @GetMapping("/api/user/{userId}/privilege-codes")
    Set<String> getUserPrivilegeCodes(@PathVariable(name = "userId") Long userId);
}
