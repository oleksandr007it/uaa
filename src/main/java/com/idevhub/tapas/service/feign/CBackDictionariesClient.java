package com.idevhub.tapas.service.feign;

import com.idevhub.tapas.domain.address.Country;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "cbackDictionariesClient", url = "${cback.dictionaries.host}")
public interface CBackDictionariesClient {

    @GetMapping("/dictionaries/v1/country")
    List<Country> getAllCountries();

    @GetMapping("/dictionaries/v1/country")
    List<Country> getCountryByNumberCode(@RequestParam("numberCode") String numberCode);
}
