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
public class StatehoodSubjectRepresentCreateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String rnokpp;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String middleName;

    @NotNull
    private List<String> privilegeGroupsIds;
}
