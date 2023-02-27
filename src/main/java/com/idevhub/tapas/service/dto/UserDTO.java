package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.service.validation.ValidNames;
import com.idevhub.tapas.service.validation.ValidPassportNo;
import com.idevhub.tapas.service.validation.ValidPhoneNumber;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * A DTO representing a user, with his authorities.
 */
@Data
@Accessors(chain = true)
public class UserDTO {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    @ValidNames
    private String firstName;

    @Size(max = 50)
    @ValidNames
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private boolean activated = false;

    @Size(min = 2, max = 10)
    private String langKey;

    private String userType;

    @ValidNames
    private String middleName;

    private String fullName;

    private String status;

    private String rnokpp;

    private String edrpouCode;

    private String org;

    private String orgUnit;

    private String position;

    private String propertyStatus;

    @ValidPhoneNumber
    private String phoneNumber;

    @ValidPassportNo
    private String pasportSn;

    private String pasportIssuedBy;

    private Instant pasportDate;

    private Boolean emailApprove;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private AddressDTO legalAddress;

    private Set<String> authorities;

    private CEADepartmentDTO ceaDepartment;

    private CentralExecutiveAuthorityDTO centralExecutiveAuthority;
}
