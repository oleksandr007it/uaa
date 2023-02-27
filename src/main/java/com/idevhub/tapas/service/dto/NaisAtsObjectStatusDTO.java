package com.idevhub.tapas.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NaisAtsObjectStatusDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long code;

    private String name;

    private Boolean isActive;

    private String description;

}
