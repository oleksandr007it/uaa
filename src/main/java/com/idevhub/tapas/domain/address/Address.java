package com.idevhub.tapas.domain.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A Address.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    private String id;

    @NotBlank
    @Pattern(regexp = "[\\d]{3}")
    @Column(name = "country_code", length = 3)
    private String countryCode;

    @Size(min = 1, max = 64)
    @Column(name = "postal_code", length = 64)
    private String postalCode;

    @Column(name = "ats_object_id")
    private Long atsObjectId;

    @Size(min = 1, max = 256)
    @Column(name = "region", length = 256)
    private String region;

    @Size(min = 1, max = 256)
    @Column(name = "regional_district", length = 256)
    private String regionalDistrict;

    @Size(min = 1, max = 256)
    @Column(name = "locality", length = 256)
    private String locality;

    @Size(min = 1, max = 256)
    @Column(name = "street", length = 256)
    private String street;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "house_number", length = 10, nullable = false)
    private String houseNumber;

    @Size(min = 1, max = 10)
    @Column(name = "pavilion_number", length = 10)
    private String pavilionNumber;

    @Size(min = 1, max = 10)
    @Column(name = "apartment_number", length = 10)
    private String apartmentNumber;
}
