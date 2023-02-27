package com.idevhub.tapas.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminServiceRequestBrokerageUpdateDTO extends AdminServiceRequestBrokerageCreateDTO implements Serializable {
    private static final long serialVersionUID = 1L;
}
