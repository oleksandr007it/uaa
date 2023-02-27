package com.idevhub.tapas.service.dto;

import lombok.Data;

@Data
public class StatehoodSubjectRepresentUpdateStatusDTO {
    private  String rawSign;
    private  String signedBase64;
    private   Long subjectId;
}
