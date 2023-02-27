package com.idevhub.tapas.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUserDTO {

    private Long id;

    private String rnokpp;

    private String firstName;

    private String lastName;

    private String middleName;

    private boolean activated;

    private String status;

    private String propertyStatus;

    private String email;

    private boolean emailApprove;

    private String phoneNumber;

    private String pasportSn;

    private String pasportIssuedBy;

    private Instant pasportDate;

    private AddressDTO legalAddress;

}
