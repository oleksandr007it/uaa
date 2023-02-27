package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the StatehoodSubject entity.
 */
@Data
public class StatehoodSubjectDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String subjectStatus;

    private AccountDetailsStatus accountDetailsStatus;

    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    @NotBlank
    @Size(min = 8, max = 10)
    private String subjectCode;

    @NotBlank
    @Size(min = 1, max = 512)
    private String subjectName;

    @NotBlank
    @Size(min = 1, max = 128)
    private String subjectShortName;

    private String headFullName;

    @NotBlank
    @Size(min = 2, max = 15)
    private String telNumber;

    @NotBlank
    @Size(min = 5, max = 254)
    private String email;

    @NotNull
    private Boolean isEmailApproved;

    @Size(min = 3, max = 17)
    private String eori;

    @NotNull
    private Boolean actualSameAsLegalAddress;

    private String createdBy;

    private String updatedBy;

    private String deletedBy;

    private Long kopfgId;

    private String kopfgCode;

    private AddressDTO legalAddress;

    private AddressDTO actualAddress;
}
