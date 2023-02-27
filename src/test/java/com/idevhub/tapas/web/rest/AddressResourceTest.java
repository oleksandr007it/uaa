package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.service.AddressService;
import com.idevhub.tapas.service.dto.AddressDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectStatus.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class AddressResourceTest {

    @Mock
    private AddressService mockAddressService;

    private AddressResource addressResourceUnderTest;

    @BeforeEach
    void setUp() {
        initMocks(this);
        addressResourceUnderTest = new AddressResource(mockAddressService);
    }

    @Test
    void testGetAllAddresses() {
        // Setup
        final List<AddressDTO> expectedResult = Arrays.asList(new AddressDTO("550e8400e29b41d4a716446655440000", "804", "Україна", "Ukraine", false, "11111", "", "", "", ACTIVE, 1L, "", ACTIVE, false, 1L, "34", "1", "1", "", ""));

        // Configure AddressService.findAll(...).
        final List<AddressDTO> addressDTOS = Arrays.asList(new AddressDTO("550e8400e29b41d4a716446655440000", "804", "Україна", "Ukraine", false, "11111", "", "", "", ACTIVE, 1L, "", ACTIVE, false, 1L, "34", "1", "1", "", ""));
        when(mockAddressService.findAll()).thenReturn(addressDTOS);

        // Run the test
        final List<AddressDTO> result = addressResourceUnderTest.getAllAddresses();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAddress() {
        // Setup
        final ResponseEntity<AddressDTO> expectedResult = new ResponseEntity<>(new AddressDTO("550e8400e29b41d4a716446655440000", "804", "Україна", "Ukraine", false, "11111", "", "", "", ACTIVE, 1L, "", ACTIVE, false, 1L, "34", "1", "1", "", ""), HttpStatus.CONTINUE);

        // Configure AddressService.findOne(...).
        final Optional<AddressDTO> addressDTO = Optional.of(new AddressDTO("550e8400e29b41d4a716446655440000", "804", "Україна", "Ukraine", false, "11111", "", "", "", ACTIVE, 1L, "", ACTIVE, false, 1L, "34", "1", "1", "", ""));
        when(mockAddressService.findOne(anyString())).thenReturn(addressDTO);

        // Run the test
        final ResponseEntity<AddressDTO> result = addressResourceUnderTest.getAddress("550e8400e29b41d4a716446655440000");

        // Verify the results
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
