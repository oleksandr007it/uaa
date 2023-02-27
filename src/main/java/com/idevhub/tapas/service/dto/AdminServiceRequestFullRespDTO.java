package com.idevhub.tapas.service.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class AdminServiceRequestFullRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //declarant
    private String RNOKPP;
    private String phone;
    private String email;
    private boolean isEmailApproved;
    private String passport;
    private String passportGivenBy;
    private Instant passportGivenAt;
    private String registrationAddress;
    private String position;

    //user
    private Long declarantId;
    private String userType;
    private String lastName;
    private String name;
    private String surName;
    private String fullName;
    private String langKey;
    private String userStatus;
    private Instant createdAt;
    private Long createdById;
    private Instant updatedAt;
    private Long updatedById;
    private Instant deletedAt;
    private Long deletedById;

    //statehoodSubject
    private String subjectCode;
    private String subjectName;
    private String subjectShortName;
    private String legalAddress;
    private String zipCode;
    private String edrpou;
    private String headFullName;
    private String koatuu;
    private String subjectTelNumber;
    private String subjectEmail;
}
