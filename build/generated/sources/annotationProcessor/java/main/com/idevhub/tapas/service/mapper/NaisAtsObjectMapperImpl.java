package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.address.NaisAtsObject;
import com.idevhub.tapas.service.dto.NaisAtsObjectDTO;
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
public class NaisAtsObjectMapperImpl implements NaisAtsObjectMapper {

    @Override
    public List<NaisAtsObject> toEntity(List<NaisAtsObjectDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<NaisAtsObject> list = new ArrayList<NaisAtsObject>( dtoList.size() );
        for ( NaisAtsObjectDTO naisAtsObjectDTO : dtoList ) {
            list.add( toEntity( naisAtsObjectDTO ) );
        }

        return list;
    }

    @Override
    public List<NaisAtsObjectDTO> toDto(List<NaisAtsObject> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NaisAtsObjectDTO> list = new ArrayList<NaisAtsObjectDTO>( entityList.size() );
        for ( NaisAtsObject naisAtsObject : entityList ) {
            list.add( toDto( naisAtsObject ) );
        }

        return list;
    }

    @Override
    public Set<NaisAtsObjectDTO> toDto(Set<NaisAtsObject> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<NaisAtsObjectDTO> set = new HashSet<NaisAtsObjectDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( NaisAtsObject naisAtsObject : entitySet ) {
            set.add( toDto( naisAtsObject ) );
        }

        return set;
    }

    @Override
    public NaisAtsObjectDTO toDto(NaisAtsObject naisAtsObject) {
        if ( naisAtsObject == null ) {
            return null;
        }

        NaisAtsObjectDTO naisAtsObjectDTO = new NaisAtsObjectDTO();

        naisAtsObjectDTO.setId( naisAtsObject.getId() );
        naisAtsObjectDTO.setKoatuuCode( naisAtsObject.getKoatuuCode() );
        naisAtsObjectDTO.setName( naisAtsObject.getName() );

        return naisAtsObjectDTO;
    }

    @Override
    public NaisAtsObject toEntity(NaisAtsObjectDTO naisAtsObjectDTO) {
        if ( naisAtsObjectDTO == null ) {
            return null;
        }

        NaisAtsObject naisAtsObject = new NaisAtsObject();

        naisAtsObject.setId( naisAtsObjectDTO.getId() );
        naisAtsObject.setKoatuuCode( naisAtsObjectDTO.getKoatuuCode() );
        naisAtsObject.setName( naisAtsObjectDTO.getName() );

        return naisAtsObject;
    }
}
