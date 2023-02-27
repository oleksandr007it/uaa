package com.idevhub.tapas.service.ldap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "identifierFromLdap")
@AllArgsConstructor
public class UserFromLdap {
    private String identifierFromLdap;
    private String nameFromLdap;
    private String loginFromLdap;
    private String userAccountControl;
    private String memberOf;
}
