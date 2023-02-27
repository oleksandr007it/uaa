package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.address.NaisAtsDenormalizedObject;
import com.idevhub.tapas.service.dto.NaisAtsObjectNameDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-11T01:53:05+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class NaisAtsDenormalizedObjectMapperImpl implements NaisAtsDenormalizedObjectMapper {

    @Override
    public NaisAtsObjectNameDTO toDto(NaisAtsDenormalizedObject atsObject) {
        if ( atsObject == null ) {
            return null;
        }

        NaisAtsObjectNameDTO naisAtsObjectNameDTO = new NaisAtsObjectNameDTO();

        naisAtsObjectNameDTO.setId( atsObject.getId() );

        naisAtsObjectNameDTO.setName( getObjectName(atsObject) );

        return naisAtsObjectNameDTO;
    }

    @Override
    public List<NaisAtsObjectNameDTO> toDto(List<NaisAtsDenormalizedObject> atsObject) {
        if ( atsObject == null ) {
            return null;
        }

        List<NaisAtsObjectNameDTO> list = new ArrayList<NaisAtsObjectNameDTO>( atsObject.size() );
        for ( NaisAtsDenormalizedObject naisAtsDenormalizedObject : atsObject ) {
            list.add( toDto( naisAtsDenormalizedObject ) );
        }

        return list;
    }
}
