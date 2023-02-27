package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.enumeration.PositionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CEAEmployeeDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank
    @Size(min = 1, max = 64)
    private String firstName;
    @NotBlank
    @Size(min = 1, max = 64)
    private String lastName;
    private String middleName;
    @NotBlank
    @Size(min = 8, max = 10)
    private String rnokpp;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    @Pattern(regexp = "[\\d]{8}")
    private String edrpouCode;
    private Long departmentId;
    private String departmentFullName;
    @NotBlank
    @Size(min = 1, max = 64)
    private String position;
    @NotNull
    private PositionType positionType;
}
