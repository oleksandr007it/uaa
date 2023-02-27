package com.idevhub.tapas.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubjectDTO {
    private List<String> headFullName;
    private String code;
    private String name;
    private String olfName;
    private AddressFromEDR addressFromEDR;
}
