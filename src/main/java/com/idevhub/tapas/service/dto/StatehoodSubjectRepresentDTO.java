package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StatehoodSubjectRepresentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private StatehoodSubjectRepresentStatus subjectRepresentStatus;

    @NotNull
    private Instant createdAt;

    private Instant updatedAt;

    private Instant deletedAt;

    @NotNull
    private StatehoodSubjectRepresentType subjectRepresentType;

    private String createdBy;

    private String updatedBy;

    private String deletedBy;

    private Long statehoodSubjectId;

    private Long declarantId;

    private String declarantLogin;

    @NotNull
    private Boolean isCurrentContext;

    private List<PrivilegeGroupGeneralDTO> privilegeGroups = Collections.emptyList();
}
