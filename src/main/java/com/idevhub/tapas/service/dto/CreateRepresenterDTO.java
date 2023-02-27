package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRepresenterDTO {
    private User declarant;
    private StatehoodSubject subject;
    private StatehoodSubjectRepresentStatus subjectRepresentStatus;
    private StatehoodSubjectRepresentType subjectRepresentType;
    private String approveSignBase64;
    private String defaultPrivilegeGroupCode;
}
