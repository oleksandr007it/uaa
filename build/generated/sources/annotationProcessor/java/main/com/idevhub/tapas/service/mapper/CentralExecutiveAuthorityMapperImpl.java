package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.CentralExecutiveAuthority;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.service.dto.CentralExecutiveAuthorityDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-11T01:53:04+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class CentralExecutiveAuthorityMapperImpl implements CentralExecutiveAuthorityMapper {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<CentralExecutiveAuthority> toEntity(List<CentralExecutiveAuthorityDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CentralExecutiveAuthority> list = new ArrayList<CentralExecutiveAuthority>( dtoList.size() );
        for ( CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO : dtoList ) {
            list.add( toEntity( centralExecutiveAuthorityDTO ) );
        }

        return list;
    }

    @Override
    public List<CentralExecutiveAuthorityDTO> toDto(List<CentralExecutiveAuthority> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CentralExecutiveAuthorityDTO> list = new ArrayList<CentralExecutiveAuthorityDTO>( entityList.size() );
        for ( CentralExecutiveAuthority centralExecutiveAuthority : entityList ) {
            list.add( toDto( centralExecutiveAuthority ) );
        }

        return list;
    }

    @Override
    public Set<CentralExecutiveAuthorityDTO> toDto(Set<CentralExecutiveAuthority> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CentralExecutiveAuthorityDTO> set = new HashSet<CentralExecutiveAuthorityDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CentralExecutiveAuthority centralExecutiveAuthority : entitySet ) {
            set.add( toDto( centralExecutiveAuthority ) );
        }

        return set;
    }

    @Override
    public CentralExecutiveAuthorityDTO toDto(CentralExecutiveAuthority centralExecutiveAuthority) {
        if ( centralExecutiveAuthority == null ) {
            return null;
        }

        CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO = new CentralExecutiveAuthorityDTO();

        centralExecutiveAuthorityDTO.setAddressId( centralExecutiveAuthorityAddressId( centralExecutiveAuthority ) );
        centralExecutiveAuthorityDTO.setId( centralExecutiveAuthority.getId() );
        centralExecutiveAuthorityDTO.setCentralExecutiveAuthorityStatus( centralExecutiveAuthority.getCentralExecutiveAuthorityStatus() );
        centralExecutiveAuthorityDTO.setCreatedDate( centralExecutiveAuthority.getCreatedDate() );
        centralExecutiveAuthorityDTO.setLastModifiedDate( centralExecutiveAuthority.getLastModifiedDate() );
        centralExecutiveAuthorityDTO.setDeletedAt( centralExecutiveAuthority.getDeletedAt() );
        centralExecutiveAuthorityDTO.setCode( centralExecutiveAuthority.getCode() );
        centralExecutiveAuthorityDTO.setFullNameUkr( centralExecutiveAuthority.getFullNameUkr() );
        centralExecutiveAuthorityDTO.setFullNameEng( centralExecutiveAuthority.getFullNameEng() );
        centralExecutiveAuthorityDTO.setTelNumber( centralExecutiveAuthority.getTelNumber() );
        centralExecutiveAuthorityDTO.setEmail( centralExecutiveAuthority.getEmail() );
        centralExecutiveAuthorityDTO.setLastModifiedBy( centralExecutiveAuthority.getLastModifiedBy() );
        centralExecutiveAuthorityDTO.setDeletedBy( centralExecutiveAuthority.getDeletedBy() );
        centralExecutiveAuthorityDTO.setCreatedBy( centralExecutiveAuthority.getCreatedBy() );

        return centralExecutiveAuthorityDTO;
    }

    @Override
    public CentralExecutiveAuthority toEntity(CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO) {
        if ( centralExecutiveAuthorityDTO == null ) {
            return null;
        }

        CentralExecutiveAuthority centralExecutiveAuthority = new CentralExecutiveAuthority();

        centralExecutiveAuthority.address( addressMapper.fromId( centralExecutiveAuthorityDTO.getAddressId() ) );
        centralExecutiveAuthority.setCreatedBy( centralExecutiveAuthorityDTO.getCreatedBy() );
        centralExecutiveAuthority.setCreatedDate( centralExecutiveAuthorityDTO.getCreatedDate() );
        centralExecutiveAuthority.setLastModifiedBy( centralExecutiveAuthorityDTO.getLastModifiedBy() );
        centralExecutiveAuthority.setLastModifiedDate( centralExecutiveAuthorityDTO.getLastModifiedDate() );
        centralExecutiveAuthority.setId( centralExecutiveAuthorityDTO.getId() );
        centralExecutiveAuthority.centralExecutiveAuthorityStatus( centralExecutiveAuthorityDTO.getCentralExecutiveAuthorityStatus() );
        centralExecutiveAuthority.deletedAt( centralExecutiveAuthorityDTO.getDeletedAt() );
        centralExecutiveAuthority.code( centralExecutiveAuthorityDTO.getCode() );
        centralExecutiveAuthority.fullNameUkr( centralExecutiveAuthorityDTO.getFullNameUkr() );
        centralExecutiveAuthority.fullNameEng( centralExecutiveAuthorityDTO.getFullNameEng() );
        centralExecutiveAuthority.telNumber( centralExecutiveAuthorityDTO.getTelNumber() );
        centralExecutiveAuthority.email( centralExecutiveAuthorityDTO.getEmail() );
        centralExecutiveAuthority.deletedBy( centralExecutiveAuthorityDTO.getDeletedBy() );

        return centralExecutiveAuthority;
    }

    private String centralExecutiveAuthorityAddressId(CentralExecutiveAuthority centralExecutiveAuthority) {
        if ( centralExecutiveAuthority == null ) {
            return null;
        }
        Address address = centralExecutiveAuthority.getAddress();
        if ( address == null ) {
            return null;
        }
        String id = address.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
