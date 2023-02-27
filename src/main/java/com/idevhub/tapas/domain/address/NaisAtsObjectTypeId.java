package com.idevhub.tapas.domain.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class NaisAtsObjectTypeId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "level", nullable = false)
    private Long level;

    @Column(name = "code", nullable = false)
    private Long code;

}
