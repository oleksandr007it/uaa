package com.idevhub.tapas.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CeaEmployeeMainInfoDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String rnokpp;
    private String email;
    private String phoneNumber;
    private String orgUnit;
    private String position;
}
