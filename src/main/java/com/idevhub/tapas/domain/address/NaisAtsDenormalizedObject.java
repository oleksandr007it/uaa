package com.idevhub.tapas.domain.address;

import com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectStatus;
import com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "nais_ats_denorm_object")
public class NaisAtsDenormalizedObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private NaisAtsDenormalizedObjectType type;

    @ManyToOne(cascade = {PERSIST, MERGE})
    @JoinColumn(name = "parent_id")
    private NaisAtsDenormalizedObject parent;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private NaisAtsDenormalizedObjectStatus status;

    @NotBlank
    @Size(min = 1, max = 256)
    @Column(name = "search_name", length = 256, nullable = false)
    private String searchName;

    @Pattern(regexp = "[\\d]{10}")
    @Column(name = "koatuu_code", length = 10)
    private String koatuuCode;

    @Size(min = 1, max = 256)
    @Column(name = "region_name", length = 256)
    private String regionName;

    @Size(min = 1, max = 256)
    @Column(name = "district_name", length = 256)
    private String districtName;

    @NotBlank
    @Size(min = 1, max = 256)
    @Column(name = "locality_name", length = 256, nullable = false)
    private String localityName;

    @Size(min = 1, max = 512)
    @Column(name = "locality_object_name", length = 512)
    private String localityObjectName;
}
