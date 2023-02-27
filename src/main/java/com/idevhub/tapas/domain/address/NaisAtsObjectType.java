package com.idevhub.tapas.domain.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(NaisAtsObjectTypeId.class)
@Table(name = "nais_ats_object_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NaisAtsObjectType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long level;

    @Id
    private Long code;

    @Size(min = 1, max = 32)
    @Column(name = "short_name", length = 32)
    private String shortName;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "full_name", nullable = false, length = 64)
    private String fullName;

}
