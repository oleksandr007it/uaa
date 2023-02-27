package com.idevhub.tapas.service.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PrivilegeGroupWithPrivilegesDTO extends PrivilegeGroupGeneralDTO {
    private static final long serialVersionUID = 1L;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    @NotEmpty
    private List<PrivilegeDTO> privileges;

    public void setPrivileges(List<PrivilegeDTO> privileges) {
        if (privileges == null) this.privileges = Collections.emptyList();
        else
            this.privileges = privileges.stream().sorted(Comparator.comparing(PrivilegeDTO::getCode)).collect(Collectors.toList());
    }
}
