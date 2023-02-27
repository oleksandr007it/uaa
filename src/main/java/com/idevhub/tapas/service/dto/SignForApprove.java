package com.idevhub.tapas.service.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class SignForApprove {


    private String action;
    private String legalEntityCode;
    private String legalEntityName;
    private Instant initiationTimestamp;

}
