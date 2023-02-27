package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.Privilege;
import com.idevhub.tapas.service.dto.PrivilegeDTO;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-11T01:53:05+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class PrivilegeMapperImpl implements PrivilegeMapper {

    @Override
    public PrivilegeDTO toDto(Privilege privilege) {
        if ( privilege == null ) {
            return null;
        }

        PrivilegeDTO privilegeDTO = new PrivilegeDTO();

        privilegeDTO.setCode( privilege.getCode() );
        privilegeDTO.setFullNameUkr( privilege.getFullNameUkr() );
        privilegeDTO.setFullNameEng( privilege.getFullNameEng() );

        return privilegeDTO;
    }

    @Override
    public Privilege toEntity(PrivilegeDTO privilegeDTO) {
        if ( privilegeDTO == null ) {
            return null;
        }

        Privilege privilege = new Privilege();

        privilege.setCode( privilegeDTO.getCode() );
        privilege.setFullNameUkr( privilegeDTO.getFullNameUkr() );
        privilege.setFullNameEng( privilegeDTO.getFullNameEng() );

        return privilege;
    }

    @Override
    public Set<Privilege> toEntity(Iterable<PrivilegeDTO> privileges) {
        if ( privileges == null ) {
            return null;
        }

        Set<Privilege> set = new HashSet<Privilege>();
        for ( PrivilegeDTO privilegeDTO : privileges ) {
            set.add( toEntity( privilegeDTO ) );
        }

        return set;
    }
}
