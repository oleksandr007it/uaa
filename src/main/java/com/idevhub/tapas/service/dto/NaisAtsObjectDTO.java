package com.idevhub.tapas.service.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class NaisAtsObjectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long parentId;

    private Long typeLevel;

    private Long typeCode;

    private Long statusCode;

    private String koatuuCode;

    private String name;

}
