package com.idevhub.tapas.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idevhub.tapas.domain.enumeration.ActiveStatus;
import com.idevhub.tapas.security.PatternConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = "code")
@Accessors(chain = true)
public class PrivilegeGroupGeneralDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    @JsonProperty("isGlobal")
    private boolean global = true;

    private ActiveStatus status;

    @NotBlank
    @Pattern(regexp = PatternConstants.TEXT)
    private String fullNameUkr;

    private String fullNameEng;
}
