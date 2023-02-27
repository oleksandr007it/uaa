package com.idevhub.tapas.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NaisAtsObjectTypeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long level;

    private Long code;

    private String shortName;

    private String fullName;

}
