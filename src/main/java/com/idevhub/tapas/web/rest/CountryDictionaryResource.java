package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.domain.address.Country;
import com.idevhub.tapas.domain.address.PostalCodeParams;
import com.idevhub.tapas.service.CountryDictionaryProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CountryDictionaryResource {

    private final CountryDictionaryProxy countryDictionaryProxy;

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getCountries() {
        log.debug("REST request to get country list");
        return ResponseEntity.ok().body(countryDictionaryProxy.getAllCountries());
    }

    @GetMapping("/countries/{numberCode}")
    public ResponseEntity<Optional<Country>> getCountryByNumberCode(@PathVariable String numberCode) {
        log.debug("REST request to get country by number code = {}", numberCode);
        return ResponseEntity.ok().body(countryDictionaryProxy.getCountryByNumCode(numberCode));
    }

    @GetMapping("/countries/{numberCode}/postal-code-params")
    public ResponseEntity<PostalCodeParams> getCountryPostalCodeParams(@PathVariable String numberCode) {
        log.debug("REST request to get country (code = {}) postal code parameters", numberCode);
        return ResponseEntity.ok().body(countryDictionaryProxy.getCountryPostalCode(numberCode));
    }
}
