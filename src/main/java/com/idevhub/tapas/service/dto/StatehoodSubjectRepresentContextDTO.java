package com.idevhub.tapas.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatehoodSubjectRepresentContextDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 1, max = 512)
    private String subjectName;

    private String subjectCode;

    private Long statehoodSubjectId;

    private Long declarantId;
}
