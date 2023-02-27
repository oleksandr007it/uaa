package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.service.dto.AddressDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-11T01:53:04+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class AddressMapperImpl extends AddressMapper {

    @Override
    public Address toEntity(AddressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Address address = new Address();

        address.setId( dto.getId() );
        address.setCountryCode( dto.getCountryCode() );
        address.setPostalCode( dto.getPostalCode() );
        address.setAtsObjectId( dto.getAtsObjectId() );
        address.setRegion( dto.getRegion() );
        address.setRegionalDistrict( dto.getRegionalDistrict() );
        address.setLocality( dto.getLocality() );
        address.setStreet( dto.getStreet() );
        address.setHouseNumber( dto.getHouseNumber() );
        address.setPavilionNumber( dto.getPavilionNumber() );
        address.setApartmentNumber( dto.getApartmentNumber() );

        return address;
    }

    @Override
    public AddressDTO toDto(Address entity) {
        if ( entity == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId( entity.getId() );
        addressDTO.setCountryCode( entity.getCountryCode() );
        addressDTO.setPostalCode( entity.getPostalCode() );
        addressDTO.setRegion( entity.getRegion() );
        addressDTO.setRegionalDistrict( entity.getRegionalDistrict() );
        addressDTO.setLocality( entity.getLocality() );
        addressDTO.setStreet( entity.getStreet() );
        addressDTO.setHouseNumber( entity.getHouseNumber() );
        addressDTO.setPavilionNumber( entity.getPavilionNumber() );
        addressDTO.setApartmentNumber( entity.getApartmentNumber() );
        addressDTO.setAtsObjectId( entity.getAtsObjectId() );

        establishRelations( entity, addressDTO );

        return addressDTO;
    }

    @Override
    public List<Address> toEntity(List<AddressDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Address> list = new ArrayList<Address>( dtoList.size() );
        for ( AddressDTO addressDTO : dtoList ) {
            list.add( toEntity( addressDTO ) );
        }

        return list;
    }

    @Override
    public List<AddressDTO> toDto(List<Address> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AddressDTO> list = new ArrayList<AddressDTO>( entityList.size() );
        for ( Address address : entityList ) {
            list.add( toDto( address ) );
        }

        return list;
    }

    @Override
    public Set<AddressDTO> toDto(Set<Address> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<AddressDTO> set = new HashSet<AddressDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( Address address : entitySet ) {
            set.add( toDto( address ) );
        }

        return set;
    }
}
