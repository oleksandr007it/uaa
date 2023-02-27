package com.idevhub.tapas.service.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StatehoodSubjectSendConfirmEmailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long statehoodSubjectId;

    @NotNull
    private String email;
}
