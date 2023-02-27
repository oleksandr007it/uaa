package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StatehoodSubjectRepresentWithNameDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private Long statehoodSubjectId;

    private Long declarantId;

    @NotNull
    private StatehoodSubjectRepresentStatus subjectRepresentStatus;

    @NotNull
    private StatehoodSubjectRepresentType subjectRepresentType;
}
