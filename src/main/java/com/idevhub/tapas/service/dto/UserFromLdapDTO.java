package com.idevhub.tapas.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFromLdapDTO {
    private String secondName;  // A02
    private String firstName;   // A03
    private String middleName;  // A04
    private String fullName;    // A05
    private String position;    // A06
    // A07 division
    private String customsName;   // --------------> organization

    private String customsCode;   // --------------> customs_body_code


    private String idCard;      // A09     // --------------> employee_id_card_number

    private String inn;         // A11
    private List<String> memberOf; // User group DNs list     ldap_member_of

    private String guid;     /// ldap_user_directory_id


}
