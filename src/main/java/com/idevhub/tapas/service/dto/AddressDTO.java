package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the Address entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @NotBlank
    @Pattern(regexp = "[\\d]{3}")
    private String countryCode;

    private String countryNameUk;

    private String countryNameEn;

    private boolean countryMissingOnDict = true;

    @Size(min = 1, max = 64)
    private String postalCode;

    @Size(min = 1, max = 128)
    private String region;

    @Size(min = 1, max = 128)
    private String regionalDistrict;

    @Size(min = 1, max = 128)
    private String locality;

    private NaisAtsDenormalizedObjectStatus localityStatus;

    private Long atsLocalityId;

    @Size(min = 1, max = 128)
    private String street;

    private NaisAtsDenormalizedObjectStatus streetStatus;

    private boolean streetMissingOnDict = true;

    private Long atsStreetId;

    @NotBlank
    @Size(min = 1, max = 10)
    private String houseNumber;

    @Size(min = 1, max = 10)
    private String pavilionNumber;

    @Size(min = 1, max = 10)
    private String apartmentNumber;

    private String koatuuCode;

    @Size(min = 1, max = 512)
    private String fullAddress;
}
