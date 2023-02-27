package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.Privilege;
import com.idevhub.tapas.domain.PrivilegeGroup;
import com.idevhub.tapas.service.dto.PrivilegeDTO;
import com.idevhub.tapas.service.dto.PrivilegeGroupGeneralDTO;
import com.idevhub.tapas.service.dto.PrivilegeGroupWithPrivilegesDTO;
import java.util.ArrayList;
import java.util.Collection;
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
public class PrivilegeGroupMapperImpl implements PrivilegeGroupMapper {

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Override
    public Set<PrivilegeGroupGeneralDTO> toDto(Collection<PrivilegeGroup> groups) {
        if ( groups == null ) {
            return null;
        }

        Set<PrivilegeGroupGeneralDTO> set = new HashSet<PrivilegeGroupGeneralDTO>( Math.max( (int) ( groups.size() / .75f ) + 1, 16 ) );
        for ( PrivilegeGroup privilegeGroup : groups ) {
            set.add( toDto( privilegeGroup ) );
        }

        return set;
    }

    @Override
    public PrivilegeGroupWithPrivilegesDTO toDto(PrivilegeGroup group) {
        if ( group == null ) {
            return null;
        }

        PrivilegeGroupWithPrivilegesDTO privilegeGroupWithPrivilegesDTO = new PrivilegeGroupWithPrivilegesDTO();

        privilegeGroupWithPrivilegesDTO.setCode( group.getCode() );
        privilegeGroupWithPrivilegesDTO.setGlobal( group.isGlobal() );
        privilegeGroupWithPrivilegesDTO.setStatus( group.getStatus() );
        privilegeGroupWithPrivilegesDTO.setFullNameUkr( group.getFullNameUkr() );
        privilegeGroupWithPrivilegesDTO.setFullNameEng( group.getFullNameEng() );
        privilegeGroupWithPrivilegesDTO.setPrivileges( privilegeSetToPrivilegeDTOList( group.getPrivileges() ) );

        return privilegeGroupWithPrivilegesDTO;
    }

    protected List<PrivilegeDTO> privilegeSetToPrivilegeDTOList(Set<Privilege> set) {
        if ( set == null ) {
            return null;
        }

        List<PrivilegeDTO> list = new ArrayList<PrivilegeDTO>( set.size() );
        for ( Privilege privilege : set ) {
            list.add( privilegeMapper.toDto( privilege ) );
        }

        return list;
    }
}
