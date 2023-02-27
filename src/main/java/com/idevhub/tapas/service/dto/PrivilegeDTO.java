package com.idevhub.tapas.service.dto;

import lombok.Data;

@Data
public class PrivilegeDTO {
    private String code;

    private String fullNameUkr;

    private String fullNameEng;

    public PrivilegeDTO(String code) {
        this.code = code;
    }

    public PrivilegeDTO() {
    }
}
