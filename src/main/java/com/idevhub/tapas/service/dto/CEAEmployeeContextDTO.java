package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.enumeration.PositionType;
import com.idevhub.tapas.domain.enumeration.PrivilegeCode;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CEAEmployeeContextDTO {

    private Long userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String fullName;
    private String ceaCode;
    private Long ceaDepartmentId;
    private String position;
    private PositionType positionType;
    private Set<PrivilegeCode> privilegeCodes = new HashSet<>();
}
