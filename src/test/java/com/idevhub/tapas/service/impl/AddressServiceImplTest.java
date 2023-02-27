package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.repository.AddressRepository;
import com.idevhub.tapas.service.dto.AddressDTO;
import com.idevhub.tapas.service.feign.CBackDictionariesClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AddressServiceImplTest {

    @MockBean(name = "com.idevhub.tapas.service.feign.CBackDictionariesClient")
    private CBackDictionariesClient mock_dictionariesClient;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressServiceImpl addressService;

    @Test
    void testFindAll() {
        saveStubAddress();

        final List<AddressDTO> result = addressService.findAll();

        assertThat(result).hasSizeGreaterThanOrEqualTo(1);
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getHouseNumber()).isNotEmpty();
        assertThat(result.get(0).getPavilionNumber()).isNotEmpty();
    }

    @Test
    void testFindOne() {
        saveStubAddress();

        final List<Address> listDomains = addressRepository.findAll();
        final String id = listDomains.get(listDomains.size() - 1).getId();

        final Optional<AddressDTO> result = addressService.findOne(id);

        result.ifPresent(v -> assertThat(v).isNotNull());
        result.ifPresent(v -> assertThat(v.getHouseNumber()).isNotEmpty());
        result.ifPresent(v -> assertThat(v.getApartmentNumber()).isGreaterThanOrEqualTo("1"));
    }

    private void saveStubAddress() {
        final Address address = new Address();
        address.setCountryCode("804");
        address.setPostalCode("12345");
        address.setHouseNumber("house.12");
        address.setPavilionNumber("block");
        address.setApartmentNumber("3");
        addressRepository.saveAndFlush(address);
    }
}
