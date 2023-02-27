package com.idevhub.tapas.service.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StatehoodSubjectCreateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String subjectStatus;

    @NotNull
    @Size(min = 8, max = 10)
    private String subjectCode;

    @NotNull
    @Size(min = 1, max = 512)
    private String subjectName;

    @NotNull
    @Size(min = 1, max = 128)
    private String subjectShortName;

    private String headFullName;

    @NotNull
    @Size(min = 2, max = 15)
    private String telNumber;

    @NotNull
    @Size(min = 5, max = 254)
    private String email;

    @NotNull
    @Size(min = 3, max = 17)
    private String eori;

    @NotNull
    private Boolean isActualSameAsLegalAddress;

    private Long kopfgId;

    private String legalAddressId;

    private String actualAddressId;
}
