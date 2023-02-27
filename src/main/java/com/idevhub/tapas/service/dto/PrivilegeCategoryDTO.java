package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import lombok.Data;

import java.util.Set;

@Data
public class PrivilegeCategoryDTO {
    private Set<PrivilegeDTO> privileges;
    private String code;
    private String fullNameUkr;
    private String fullNameEng;
    private PrivilegeType privilegeCategoryType;
}
