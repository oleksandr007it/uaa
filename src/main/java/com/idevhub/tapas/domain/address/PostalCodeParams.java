package com.idevhub.tapas.domain.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.idevhub.tapas.domain.AbstractAuditingEntity;
import com.idevhub.tapas.domain.address.enumeration.PostalCodeAvailability;
import com.idevhub.tapas.domain.address.enumeration.PostalCodeParamsStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.Instant;

import static com.idevhub.tapas.domain.address.enumeration.PostalCodeParamsStatus.ACTIVE;

@Data
@Entity
@Table(name="postal_code_params")
@EqualsAndHashCode(callSuper = false)
public class PostalCodeParams extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostalCodeParamsStatus status = ACTIVE;

    @Pattern(regexp="[\\d]{3}")
    @Column(name = "country", length = 3)
    private String countryCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "availability", nullable = false)
    private PostalCodeAvailability availability;

    @Min(1)
    @Max(64)
    @Column(name = "min_length")
    private Integer minLength;

    @Min(1)
    @Max(64)
    @Column(name = "max_length")
    private Integer maxLength;

    @Size(min = 1, max = 1024)
    @Column(name = "format")
    private String format;

    @Size(min = 1, max = 63)
    @Column(name = "prefix")
    private String prefix;

    @Size(min = 1, max = 64)
    @Column(name = "const_value")
    private String constValue;

    @Size(min = 1, max = 1024)
    @Column(name = "prompt_ua")
    private String promptUa;

    @Size(min = 1, max = 1024)
    @Column(name = "prompt_en")
    private String promptEn;

    @Size(min = 10, max = 1024)
    @Column(name = "info_url_ua")
    private String infoUrlUa;

    @Size(min = 10, max = 1024)
    @Column(name = "info_url_en")
    private String infoUrlEn;

    @JsonIgnore
    @Column(name = "deleted_by", length = 50)
    private String deletedBy;

    @JsonIgnore
    @Column(name = "deleted_date")
    private Instant deletedDate;
}
