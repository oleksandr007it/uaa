package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.address.NaisAtsDenormalizedObject;
import com.idevhub.tapas.service.CountryDictionaryProxy;
import com.idevhub.tapas.service.NaisAtsDictionaryService;
import com.idevhub.tapas.service.dto.AddressDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import static com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectType.LOCALITY_OBJECT;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public abstract class AddressMapper implements EntityMapper<AddressDTO, Address> {

    private CountryDictionaryProxy countryDictionaryProxy;
    private NaisAtsDictionaryService atsDictionaryService;
    private NaisAtsDenormalizedObjectMapper atsDenormalizedObjectMapper;

    private static final String UKRAINE_CODE = "804";

    @Autowired
    public void setCountryDictionaryProxy(CountryDictionaryProxy countryDictionaryProxy) {
        this.countryDictionaryProxy = countryDictionaryProxy;
    }

    @Autowired
    public void setAtsDictionaryService(NaisAtsDictionaryService atsDictionaryService) {
        this.atsDictionaryService = atsDictionaryService;
    }

    @Autowired
    public void setAtsDenormalizedObjectMapper(NaisAtsDenormalizedObjectMapper atsDenormalizedObjectMapper) {
        this.atsDenormalizedObjectMapper = atsDenormalizedObjectMapper;
    }

    public Address mapOrUpdateAddress(AddressDTO source, @MappingTarget Address target) {

        if (source == null) {
            return null;
        }

        if (target == null) {
            target = new Address();
        }

        target.setCountryCode(source.getCountryCode());
        target.setPostalCode(source.getPostalCode());
        target.setHouseNumber(source.getHouseNumber());
        target.setPavilionNumber(source.getPavilionNumber());
        target.setApartmentNumber(source.getApartmentNumber());

        if (UKRAINE_CODE.equals(source.getCountryCode())) {
            target.setRegion(null);
            target.setRegionalDistrict(null);
            target.setLocality(null);
            if (source.isStreetMissingOnDict() && source.getAtsStreetId() == null) {
                target.setAtsObjectId(source.getAtsLocalityId());
                target.setStreet(source.getStreet());
            } else {
                target.setAtsObjectId(source.getAtsStreetId());
                target.setStreet(null);
            }
        } else {
            target.setAtsObjectId(null);
            target.setRegion(source.getRegion());
            target.setRegionalDistrict(source.getRegionalDistrict());
            target.setLocality(source.getLocality());
            target.setStreet(source.getStreet());
        }

        return target;
    }

    @AfterMapping
    public void establishRelations(Address address, @MappingTarget AddressDTO addressDTO) {
        establishCountryRelations(addressDTO);
        if (UKRAINE_CODE.equals(address.getCountryCode())) {
            establishNaisAtsObjectRelations(address, addressDTO);
        }
        buildFullAddress(addressDTO);
    }

    private void establishCountryRelations(AddressDTO addressDTO) {
        countryDictionaryProxy.getCountryByNumCode(addressDTO.getCountryCode())
            .ifPresent(country -> {
                addressDTO.setCountryNameUk(country.getNameUk());
                addressDTO.setCountryNameEn(country.getNameEn());
                addressDTO.setCountryMissingOnDict(false);
            });
    }

    private void establishNaisAtsObjectRelations(Address address, AddressDTO addressDTO) {

        if (address.getAtsObjectId() != null) {
            NaisAtsDenormalizedObject atsObject = atsDictionaryService.getById(address.getAtsObjectId());

            boolean isLocalityObject = LOCALITY_OBJECT.equals(atsObject.getType());

            addressDTO.setKoatuuCode(atsObject.getKoatuuCode());
            addressDTO.setLocality(atsDenormalizedObjectMapper.buildLocalityName(atsObject));
            addressDTO.setLocalityStatus(isLocalityObject ? atsObject.getParent().getStatus() : atsObject.getStatus());
            addressDTO.setAtsLocalityId(isLocalityObject ? atsObject.getParent().getId() : atsObject.getId());
            addressDTO.setStreet(isLocalityObject ? atsObject.getLocalityObjectName() : address.getStreet());
            addressDTO.setStreetStatus(isLocalityObject ? atsObject.getStatus() : null);
            addressDTO.setStreetMissingOnDict(!isLocalityObject);
            addressDTO.setAtsStreetId(isLocalityObject ? atsObject.getId() : null);
        }
    }

    private void buildFullAddress(AddressDTO addressDTO) {
        StringBuilder fullAddressBuilder = new StringBuilder();
        fullAddressBuilder
            .append(addressDTO.getCountryNameUk())
            .append(addressDTO.getPostalCode() == null || addressDTO.getPostalCode().isBlank() ? "" : ", " + addressDTO.getPostalCode())
            .append(addressDTO.getRegion() == null || addressDTO.getRegion().isBlank() ? "" : ", " + addressDTO.getRegion())
            .append(addressDTO.getRegionalDistrict() == null || addressDTO.getRegionalDistrict().isBlank() ? "" : ", " + addressDTO.getRegionalDistrict())
            .append(", " + addressDTO.getLocality())
            .append(", " + addressDTO.getStreet())
            .append(" " + addressDTO.getHouseNumber())
            .append(addressDTO.getPavilionNumber() == null || addressDTO.getPavilionNumber().isBlank() ? "" : ", корп. " + addressDTO.getPavilionNumber())
            .append(addressDTO.getApartmentNumber() == null || addressDTO.getApartmentNumber().isBlank() ? "" : ", кв./оф. " + addressDTO.getApartmentNumber());
        addressDTO.setFullAddress(fullAddressBuilder.toString());
    }

    public Address fromId(String id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
