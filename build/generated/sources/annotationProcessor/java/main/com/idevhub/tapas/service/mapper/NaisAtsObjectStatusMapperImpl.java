package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.address.NaisAtsObjectStatus;
import com.idevhub.tapas.service.dto.NaisAtsObjectStatusDTO;
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
public class NaisAtsObjectStatusMapperImpl implements NaisAtsObjectStatusMapper {

    @Override
    public NaisAtsObjectStatus toEntity(NaisAtsObjectStatusDTO dto) {
        if ( dto == null ) {
            return null;
        }

        NaisAtsObjectStatus naisAtsObjectStatus = new NaisAtsObjectStatus();

        naisAtsObjectStatus.setCode( dto.getCode() );
        naisAtsObjectStatus.setName( dto.getName() );
        naisAtsObjectStatus.setDescription( dto.getDescription() );

        return naisAtsObjectStatus;
    }

    @Override
    public NaisAtsObjectStatusDTO toDto(NaisAtsObjectStatus entity) {
        if ( entity == null ) {
            return null;
        }

        NaisAtsObjectStatusDTO naisAtsObjectStatusDTO = new NaisAtsObjectStatusDTO();

        naisAtsObjectStatusDTO.setCode( entity.getCode() );
        naisAtsObjectStatusDTO.setName( entity.getName() );
        naisAtsObjectStatusDTO.setDescription( entity.getDescription() );

        return naisAtsObjectStatusDTO;
    }

    @Override
    public List<NaisAtsObjectStatus> toEntity(List<NaisAtsObjectStatusDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<NaisAtsObjectStatus> list = new ArrayList<NaisAtsObjectStatus>( dtoList.size() );
        for ( NaisAtsObjectStatusDTO naisAtsObjectStatusDTO : dtoList ) {
            list.add( toEntity( naisAtsObjectStatusDTO ) );
        }

        return list;
    }

    @Override
    public List<NaisAtsObjectStatusDTO> toDto(List<NaisAtsObjectStatus> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<NaisAtsObjectStatusDTO> list = new ArrayList<NaisAtsObjectStatusDTO>( entityList.size() );
        for ( NaisAtsObjectStatus naisAtsObjectStatus : entityList ) {
            list.add( toDto( naisAtsObjectStatus ) );
        }

        return list;
    }

    @Override
    public Set<NaisAtsObjectStatusDTO> toDto(Set<NaisAtsObjectStatus> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<NaisAtsObjectStatusDTO> set = new HashSet<NaisAtsObjectStatusDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( NaisAtsObjectStatus naisAtsObjectStatus : entitySet ) {
            set.add( toDto( naisAtsObjectStatus ) );
        }

        return set;
    }
}
