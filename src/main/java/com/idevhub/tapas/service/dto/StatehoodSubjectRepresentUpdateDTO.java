package com.idevhub.tapas.service.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StatehoodSubjectRepresentUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long representerId;

    @NotNull
    private List<String> privilegeGroupsIds;
}
