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
public class AdminServiceRequestWarehouseDTO extends AdminServiceRequestMainDataDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //TODO move duplicate with AdminServiceRequestBrokerageCreateDTO fields to parent
    private String edrpou;
    private String subjectFullName;
    private String subjectShortName;
    private String headFullName;
    private String registrationAddress;
    private Long statehoodSubjectId;
    private String applicantTelNumber;
    private String email;
}
