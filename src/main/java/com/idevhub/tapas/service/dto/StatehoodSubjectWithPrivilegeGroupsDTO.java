package com.idevhub.tapas.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class StatehoodSubjectWithPrivilegeGroupsDTO {

    private String code;
    private String shortName;
    private List<PrivilegeGroupGeneralDTO> privilegeGroups;

}
