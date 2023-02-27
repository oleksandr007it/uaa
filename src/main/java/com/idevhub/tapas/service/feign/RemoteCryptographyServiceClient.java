package com.idevhub.tapas.service.feign;

import com.idevhub.tapas.client.AuthorizedUserFeignClient;
import ua.idevhub.feign.RemoteCryptographyService;

@AuthorizedUserFeignClient(name = "crypto-service", url = "crypto-service")
public interface RemoteCryptographyServiceClient extends RemoteCryptographyService {
}
