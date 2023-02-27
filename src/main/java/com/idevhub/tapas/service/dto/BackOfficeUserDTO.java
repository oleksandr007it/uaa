package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.User;
import lombok.Data;


@Data
public class BackOfficeUserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private String fullName;

    private String org;

    private String orgCode;

    private String orgUnit;

    private String rnokpp;

    private String edrpouCode;

    private String position;

    private String email;

    private String phoneNumber;

    public BackOfficeUserDTO(User user) {

        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.middleName = user.getMiddleName();
        this.fullName = user.getFullName();
        this.rnokpp = user.getRnokpp();
        this.edrpouCode = user.getEdrpouCode();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.org = user.getOrg();
        this.orgCode = user.getOrgCode();
        this.orgUnit = user.getOrgUnit();
        this.position = user.getPosition();

    }

    public BackOfficeUserDTO() {
    }
}
