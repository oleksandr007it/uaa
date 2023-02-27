package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DmsuListUserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;

    public DmsuListUserDTO(User user) {
        id = user.getId();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        middleName = user.getMiddleName();


    }
}
