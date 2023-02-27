package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.CEADepartment;
import com.idevhub.tapas.domain.CentralExecutiveAuthority;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.service.dto.CEADepartmentDTO;
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
public class CEADepartmentMapperImpl implements CEADepartmentMapper {

    @Autowired
    private CentralExecutiveAuthorityMapper centralExecutiveAuthorityMapper;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<CEADepartment> toEntity(List<CEADepartmentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CEADepartment> list = new ArrayList<CEADepartment>( dtoList.size() );
        for ( CEADepartmentDTO cEADepartmentDTO : dtoList ) {
            list.add( toEntity( cEADepartmentDTO ) );
        }

        return list;
    }

    @Override
    public List<CEADepartmentDTO> toDto(List<CEADepartment> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CEADepartmentDTO> list = new ArrayList<CEADepartmentDTO>( entityList.size() );
        for ( CEADepartment cEADepartment : entityList ) {
            list.add( toDto( cEADepartment ) );
        }

        return list;
    }

    @Override
    public Set<CEADepartmentDTO> toDto(Set<CEADepartment> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<CEADepartmentDTO> set = new HashSet<CEADepartmentDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( CEADepartment cEADepartment : entitySet ) {
            set.add( toDto( cEADepartment ) );
        }

        return set;
    }

    @Override
    public CEADepartmentDTO toDto(CEADepartment ceaDepartment) {
        if ( ceaDepartment == null ) {
            return null;
        }

        CEADepartmentDTO cEADepartmentDTO = new CEADepartmentDTO();

        cEADepartmentDTO.setCentralExecutiveAuthorityId( ceaDepartmentCentralExecutiveAuthorityId( ceaDepartment ) );
        cEADepartmentDTO.setCentralExecutiveAuthorityCode( ceaDepartmentCentralExecutiveAuthorityCode( ceaDepartment ) );
        cEADepartmentDTO.setAddressId( ceaDepartmentAddressId( ceaDepartment ) );
        cEADepartmentDTO.setId( ceaDepartment.getId() );
        cEADepartmentDTO.setCeaDepartmentStatus( ceaDepartment.getCeaDepartmentStatus() );
        cEADepartmentDTO.setFullNameUkr( ceaDepartment.getFullNameUkr() );
        cEADepartmentDTO.setFullNameEng( ceaDepartment.getFullNameEng() );
        cEADepartmentDTO.setDescription( ceaDepartment.getDescription() );
        cEADepartmentDTO.setTelNumber( ceaDepartment.getTelNumber() );
        cEADepartmentDTO.setEmail( ceaDepartment.getEmail() );

        return cEADepartmentDTO;
    }

    @Override
    public CEADepartment toEntity(CEADepartmentDTO ceaDepartmentDTO) {
        if ( ceaDepartmentDTO == null ) {
            return null;
        }

        CEADepartment cEADepartment = new CEADepartment();

        cEADepartment.centralExecutiveAuthority( centralExecutiveAuthorityMapper.fromId( ceaDepartmentDTO.getCentralExecutiveAuthorityId() ) );
        cEADepartment.address( addressMapper.fromId( ceaDepartmentDTO.getAddressId() ) );
        cEADepartment.setId( ceaDepartmentDTO.getId() );
        cEADepartment.ceaDepartmentStatus( ceaDepartmentDTO.getCeaDepartmentStatus() );
        cEADepartment.fullNameUkr( ceaDepartmentDTO.getFullNameUkr() );
        cEADepartment.fullNameEng( ceaDepartmentDTO.getFullNameEng() );
        cEADepartment.description( ceaDepartmentDTO.getDescription() );
        cEADepartment.telNumber( ceaDepartmentDTO.getTelNumber() );
        cEADepartment.email( ceaDepartmentDTO.getEmail() );

        return cEADepartment;
    }

    private Long ceaDepartmentCentralExecutiveAuthorityId(CEADepartment cEADepartment) {
        if ( cEADepartment == null ) {
            return null;
        }
        CentralExecutiveAuthority centralExecutiveAuthority = cEADepartment.getCentralExecutiveAuthority();
        if ( centralExecutiveAuthority == null ) {
            return null;
        }
        Long id = centralExecutiveAuthority.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String ceaDepartmentCentralExecutiveAuthorityCode(CEADepartment cEADepartment) {
        if ( cEADepartment == null ) {
            return null;
        }
        CentralExecutiveAuthority centralExecutiveAuthority = cEADepartment.getCentralExecutiveAuthority();
        if ( centralExecutiveAuthority == null ) {
            return null;
        }
        String code = centralExecutiveAuthority.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }

    private String ceaDepartmentAddressId(CEADepartment cEADepartment) {
        if ( cEADepartment == null ) {
            return null;
        }
        Address address = cEADepartment.getAddress();
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
