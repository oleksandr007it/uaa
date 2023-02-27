package com.idevhub.tapas.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepresentativeInvitation {

    private String userToInviteFullName;
    private String userToInviteEmail;

    private String invitedByFullName;

    private String subjectCode;
    private String subjectShortName;

    private String langKey;

}
