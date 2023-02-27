package com.idevhub.tapas.domain.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nais_ats_object_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NaisAtsObjectStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "code")
    private Long code;

    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name", nullable = false, unique = true, length = 64)
    private String name;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Size(min = 1, max = 256)
    @Column(name = "description", length = 256)
    private String description;

}
