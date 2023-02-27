package com.idevhub.tapas.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressFromEDR {

    private String zip;
    private String country;
    @JsonProperty("address")
    private String fullAddress;
    private AddressParts parts;

}
