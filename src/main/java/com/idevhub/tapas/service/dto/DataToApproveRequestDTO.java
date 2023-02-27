package com.idevhub.tapas.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataToApproveRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String edrpouStr;
    private String rnokppStr;
}
