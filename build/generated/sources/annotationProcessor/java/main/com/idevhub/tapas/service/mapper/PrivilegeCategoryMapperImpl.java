package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.Privilege;
import com.idevhub.tapas.domain.PrivilegeCategory;
import com.idevhub.tapas.service.dto.PrivilegeCategoryDTO;
import com.idevhub.tapas.service.dto.PrivilegeDTO;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-11T01:53:05+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class PrivilegeCategoryMapperImpl implements PrivilegeCategoryMapper {

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Override
    public PrivilegeCategoryDTO toDto(PrivilegeCategory category) {
        if ( category == null ) {
            return null;
        }

        PrivilegeCategoryDTO privilegeCategoryDTO = new PrivilegeCategoryDTO();

        privilegeCategoryDTO.setPrivileges( privilegeSetToPrivilegeDTOSet( category.getPrivileges() ) );
        privilegeCategoryDTO.setCode( category.getCode() );
        privilegeCategoryDTO.setFullNameUkr( category.getFullNameUkr() );
        privilegeCategoryDTO.setFullNameEng( category.getFullNameEng() );
        privilegeCategoryDTO.setPrivilegeCategoryType( category.getPrivilegeCategoryType() );

        return privilegeCategoryDTO;
    }

    @Override
    public Set<PrivilegeCategoryDTO> toDto(Iterable<PrivilegeCategory> categories) {
        if ( categories == null ) {
            return null;
        }

        Set<PrivilegeCategoryDTO> set = new HashSet<PrivilegeCategoryDTO>();
        for ( PrivilegeCategory privilegeCategory : categories ) {
            set.add( toDto( privilegeCategory ) );
        }

        return set;
    }

    protected Set<PrivilegeDTO> privilegeSetToPrivilegeDTOSet(Set<Privilege> set) {
        if ( set == null ) {
            return null;
        }

        Set<PrivilegeDTO> set1 = new HashSet<PrivilegeDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Privilege privilege : set ) {
            set1.add( privilegeMapper.toDto( privilege ) );
        }

        return set1;
    }
}
