package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.enumeration.CEADepartmentStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the CEADepartment entity.
 */
@Data
public class CEADepartmentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private CEADepartmentStatus ceaDepartmentStatus;

    @NotNull
    @Size(max = 256)
    private String fullNameUkr;

    @Size(max = 256)
    private String fullNameEng;

    @NotNull
    @Size(max = 256)
    private String description;

    @NotNull
    @Size(max = 15)
    private String telNumber;

    @NotNull
    private String email;

    private Long centralExecutiveAuthorityId;

    private String addressId;

    private String centralExecutiveAuthorityCode;
}
