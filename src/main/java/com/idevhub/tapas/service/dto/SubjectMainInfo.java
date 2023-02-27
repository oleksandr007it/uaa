package com.idevhub.tapas.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SubjectMainInfo {

    private long id;
    private int state;
    @JsonProperty("state_text")
    private String stateText;
    private String code;
    private String name;
    private String url;

}
