package com.idevhub.tapas.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CEAEmployeeFindAllDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String fullName;
    private String orgUnit;
    private String position;
    private CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO;
}
