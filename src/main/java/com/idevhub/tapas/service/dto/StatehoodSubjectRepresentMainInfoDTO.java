package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatehoodSubjectRepresentMainInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private StatehoodSubjectRepresentStatus subjectRepresentStatus;

    private StatehoodSubjectRepresentType subjectRepresentType;

    private String subjectStatus;

    private AccountDetailsStatus accountDetailsStatus;

    private Long statehoodSubjectId;

    private String statehoodSubjectFullName;

    private String statehoodSubjectEdrpou;

    private Long declarantId;

    private String declarantFirstName;

    private String declarantLastName;

    private String declarantMiddleName;

    private String declarantFullName;
}
