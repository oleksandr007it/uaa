package com.idevhub.tapas.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AddressParts {

    private String atu;
    private String street;
    @JsonProperty("house_type")
    private String houseType;
    private String house;
    @JsonProperty("building_type")
    private String buildingType;
    private String building;
    @JsonProperty("num_type")
    private String numType;
    private String num;

}
