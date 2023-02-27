package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.address.NaisAtsObjectType;
import com.idevhub.tapas.service.dto.NaisAtsObjectTypeDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-11T01:53:05+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class NaisAtsObjectTypeMapperImpl implements NaisAtsObjectTypeMapper {

    @Override
    public NaisAtsObjectType toEntity(NaisAtsObjectTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        NaisAtsObjectType naisAtsObjectType = new NaisAtsObjectType();

        naisAtsObjectType.setLevel( dto.getLevel() );
        naisAtsObjectType.setCode( dto.getCode() );
        naisAtsObjectType.setShortName( dto.getShortName() );
        naisAtsObjectType.setFullName( dto.getFullName() );

        return naisAtsObjectType;
    }

    @Override
    public NaisAtsObjectTypeDTO toDto(NaisAtsObjectType entity) {
        if ( entity == null ) {
            return null;
        }

        NaisAtsObjectTypeDTO naisAtsObjectTypeDTO = new NaisAtsObjectTypeDTO();

        naisAtsObjectTypeDTO.setLevel( entity.getLevel() );
        naisAtsObjectTypeDTO.setCode( entity.getCode() );
        naisAtsObjectTypeDTO.setShortName( entity.getShortName() );
        naisAtsObjectTypeDTO.setFullName( entity.getFullName() );

        return naisAtsObjectTypeDTO;
    }

    @Override
    public List<NaisAtsObjectType> toEntity(List<NaisAtsObjectTypeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<NaisAtsObjectType> list = new ArrayList<NaisAtsObjectType>( dtoList.size() );
        for ( NaisAtsObjectTypeDTO naisAtsObjectTypeDTO : dtoList ) {
            list.add( toEntity( naisAtsObjectTypeDTO ) );
        }

        return list;
    }

    @Override
    public List<NaisAtsObjectTypeDTO> toDto(List<NaisAtsObjectType> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NaisAtsObjectTypeDTO> list = new ArrayList<NaisAtsObjectTypeDTO>( entityList.size() );
        for ( NaisAtsObjectType naisAtsObjectType : entityList ) {
            list.add( toDto( naisAtsObjectType ) );
        }

        return list;
    }

    @Override
    public Set<NaisAtsObjectTypeDTO> toDto(Set<NaisAtsObjectType> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<NaisAtsObjectTypeDTO> set = new HashSet<NaisAtsObjectTypeDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( NaisAtsObjectType naisAtsObjectType : entitySet ) {
            set.add( toDto( naisAtsObjectType ) );
        }

        return set;
    }
}
