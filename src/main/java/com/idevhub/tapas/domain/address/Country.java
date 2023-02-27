package com.idevhub.tapas.domain.address;

import lombok.Data;

import java.io.Serializable;

@Data
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    private String numberCode;
    private String alpha2;
    private String alpha3;
    private String nameEn;
    private String nameUk;
    private String nameUkShort;

}
