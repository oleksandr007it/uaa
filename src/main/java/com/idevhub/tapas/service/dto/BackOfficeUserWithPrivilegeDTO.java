package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


@Data
@EqualsAndHashCode(callSuper = true)
public class BackOfficeUserWithPrivilegeDTO extends BackOfficeUserDTO {

    private Set<String> privilegeCodes;

    public BackOfficeUserWithPrivilegeDTO(User user) {
        super(user);
        this.privilegeCodes = user.loadAndGetPrivilegeCodes();
    }

    public BackOfficeUserWithPrivilegeDTO() {
    }
}
