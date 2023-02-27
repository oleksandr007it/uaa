package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.domain.address.Country;
import com.idevhub.tapas.domain.address.PostalCodeParams;
import com.idevhub.tapas.repository.PostalCodeParamsRepository;
import com.idevhub.tapas.service.feign.CBackDictionariesClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CountryDictionaryResourceIT {

    @MockBean(name = "com.idevhub.tapas.service.feign.CBackDictionariesClient")
    private CBackDictionariesClient mock_dictionariesClient;

    @Autowired
    private PostalCodeParamsRepository postalCodeParamsRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void getCountries() throws Exception {

        // ARRANGE
        when(mock_dictionariesClient.getAllCountries()).thenReturn(stubCountries());

        // ACT AND ASSERT
        mockMvc.perform(get("/api/countries"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(stubCountries().size())))
            .andExpect(jsonPath("$.[*].numberCode").value(hasItem(stubCountryCode)))
            .andExpect(jsonPath("$.[*].nameUk").value(hasItem(stubCountries().get(0).getNameUk())))
            .andExpect(jsonPath("$.[*].nameEn").value(hasItem(stubCountries().get(0).getNameEn())));
    }

    @Test
    @WithMockUser
    void getCountryByNumberCode() throws Exception {

        // ARRANGE
        when(mock_dictionariesClient.getCountryByNumberCode(any())).thenReturn(stubCountries());

        // ACT AND ASSERT
        mockMvc.perform(get("/api/countries/{}",stubCountryCode))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.numberCode").value(stubCountryCode))
            .andExpect(jsonPath("$.nameUk").value(stubCountries().get(0).getNameUk()))
            .andExpect(jsonPath("$.nameEn").value(stubCountries().get(0).getNameEn()));
    }

    @Test
    @WithMockUser
    void getCountryPostalCodeParams() throws Exception {

        // ARRANGE
        Optional<PostalCodeParams> expectedOpt = postalCodeParamsRepository.findActiveByCountryCode(stubCountryCode);
        assertTrue(expectedOpt.isPresent());
        PostalCodeParams expected = expectedOpt.get();

        // ACT AND ASSERT
        mockMvc.perform(get("/api/countries/{}}/postal-code-params", stubCountryCode))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.countryCode").value(expected.getCountryCode()))
            .andExpect(jsonPath("$.availability").value(expected.getAvailability().name()))
            .andExpect(jsonPath("$.minLength").value(expected.getMinLength()))
            .andExpect(jsonPath("$.maxLength").value(expected.getMaxLength()))
            .andExpect(jsonPath("$.format").value(expected.getFormat()));
    }

    private final String stubCountryCode = "804";

    private List<Country> stubCountries() {
        Country country = new Country();
        country.setNumberCode(stubCountryCode);
        country.setAlpha2("UA");
        country.setAlpha3("UKR");
        country.setNameEn("Ukraine");
        country.setNameUk("Україна");
        country.setNameUkShort("Україна");
        return Arrays.asList(country);
    }

}
