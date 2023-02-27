package com.idevhub.tapas.service.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestApproveOrReissueDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long applicantId;


    private Long statehoodSubjectId;


    private String subjectFullNameFromRegister;


    private String edrpouFromRegister;


    private String legalAddressFromRegister;


    private String brokerRegNumber;


    private String recordingToRegisterDate;
}
