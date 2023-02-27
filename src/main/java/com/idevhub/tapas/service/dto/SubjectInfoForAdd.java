package com.idevhub.tapas.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SubjectInfoForAdd {


    @NotNull
    @Size(min = 8, max = 10)
    private String subjectCode;

    @NotNull
    @Size(min = 1, max = 512)
    private String subjectName;


    @Size(min = 2, max = 15)
    private String telNumber;


    @Size(min = 5, max = 254)
    private String email;


    @Size(min = 3, max = 17)
    private String eori;

    @JsonProperty("isInternal")
    private  boolean isInternal;

    private String rawDataBase64;
    private String signBase64;


}
