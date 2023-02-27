package com.idevhub.tapas.domain;

import com.idevhub.tapas.domain.enumeration.PositionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CEAEmployee implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String rnokpp;
    private String email;
    private String phoneNumber;
    private CentralExecutiveAuthority cea;
    private CEADepartment ceaDepartment;
    private String position;
    private PositionType positionType;

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.lastName)
            .append(" ")
            .append(this.firstName);
        if (this.middleName != null && !this.middleName.isBlank()) {
            sb.append(" ")
                .append(this.middleName);
        }
        return sb.toString();
    }
}
