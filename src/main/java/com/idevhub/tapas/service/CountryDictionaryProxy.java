package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.address.Country;
import com.idevhub.tapas.domain.address.PostalCodeParams;
import com.idevhub.tapas.repository.PostalCodeParamsRepository;
import com.idevhub.tapas.service.errors.NonUniqueResultException;
import com.idevhub.tapas.service.feign.CBackDictionariesClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryDictionaryProxy {

    private final CBackDictionariesClient cBackDictionariesClient;
    private final PostalCodeParamsRepository postalCodeParamsRepository;

    @Cacheable("allCountries")
    public List<Country> getAllCountries() {
        log.debug("Getting country list...");
        return cBackDictionariesClient.getAllCountries();
    }

    @Cacheable("countryByNumCode")
    public Optional<Country> getCountryByNumCode(String numCode) {
        log.debug("Getting country by code = {}...", numCode);
        List<Country> resultList = cBackDictionariesClient.getCountryByNumberCode(numCode);
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else if (resultList.size() > 1) {
            throw new NonUniqueResultException(resultList.size(), Country.class);
        } else
            return Optional.of(resultList.iterator().next());
    }

    public PostalCodeParams getCountryPostalCode(String numCode) {
        log.debug("Getting country postal code params by code = {}...", numCode);
        return postalCodeParamsRepository.findActiveByCountryCode(numCode)
            .orElseGet(() -> postalCodeParamsRepository.findActiveByCountryCode("")
                .orElseThrow(() -> new EntityNotFoundException(PostalCodeParams.class)));
    }
}
