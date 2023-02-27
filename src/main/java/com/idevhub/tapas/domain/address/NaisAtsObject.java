package com.idevhub.tapas.domain.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "nais_ats_object")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NaisAtsObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private NaisAtsObject parent;

    @ManyToOne
    @JoinColumn(name = "type_level", referencedColumnName = "level", nullable = false)
    @JoinColumn(name = "type_code", referencedColumnName = "code", nullable = false)
    private NaisAtsObjectType type;

    @ManyToOne
    @JoinColumn(name = "status_code", referencedColumnName = "code", nullable = false)
    private NaisAtsObjectStatus status;

    @Pattern(regexp = "[\\d]{10}")
    @Column(name = "koatuu_code", length = 10)
    private String koatuuCode;

    @NotBlank
    @Size(min = 1, max = 256)
    @Column(name = "name", nullable = false, length = 256)
    private String name;

}
