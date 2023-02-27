package com.idevhub.tapas.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AdminServiceRequestBrokerageCreateDTO extends AdminServiceRequestMainDataDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //TODO move duplicate with AdminServiceRequestWarehouseCreateDTO fields to parent
    private String edrpou;
    private String kopfg;
    private String subjectName;
    private String headFullName;
    private String legalAddress;
    private String koatuu;
    private String subjectTelNumber;
    private String subjectEmail;
    private Long statehoodSubjectId;
    private Long declarantId;
    private String createdBy;
    private String executedBy;
}
