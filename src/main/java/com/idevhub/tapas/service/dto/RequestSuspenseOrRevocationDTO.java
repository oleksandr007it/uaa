package com.idevhub.tapas.service.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestSuspenseOrRevocationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long applicantId;

    @NotNull
    private Long statehoodSubjectId;

}
