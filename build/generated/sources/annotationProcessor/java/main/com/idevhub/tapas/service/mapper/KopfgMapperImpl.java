package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.Kopfg;
import com.idevhub.tapas.service.dto.KopfgDTO;
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
public class KopfgMapperImpl implements KopfgMapper {

    @Override
    public Kopfg toEntity(KopfgDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Kopfg kopfg = new Kopfg();

        kopfg.setId( dto.getId() );
        kopfg.setCode( dto.getCode() );
        kopfg.setName( dto.getName() );
        kopfg.setValidUntil( dto.getValidUntil() );
        kopfg.setPreviousCodes( dto.getPreviousCodes() );

        return kopfg;
    }

    @Override
    public KopfgDTO toDto(Kopfg entity) {
        if ( entity == null ) {
            return null;
        }

        KopfgDTO kopfgDTO = new KopfgDTO();

        kopfgDTO.setId( entity.getId() );
        kopfgDTO.setCode( entity.getCode() );
        kopfgDTO.setName( entity.getName() );
        kopfgDTO.setValidUntil( entity.getValidUntil() );
        kopfgDTO.setPreviousCodes( entity.getPreviousCodes() );

        return kopfgDTO;
    }

    @Override
    public List<Kopfg> toEntity(List<KopfgDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Kopfg> list = new ArrayList<Kopfg>( dtoList.size() );
        for ( KopfgDTO kopfgDTO : dtoList ) {
            list.add( toEntity( kopfgDTO ) );
        }

        return list;
    }

    @Override
    public List<KopfgDTO> toDto(List<Kopfg> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<KopfgDTO> list = new ArrayList<KopfgDTO>( entityList.size() );
        for ( Kopfg kopfg : entityList ) {
            list.add( toDto( kopfg ) );
        }

        return list;
    }

    @Override
    public Set<KopfgDTO> toDto(Set<Kopfg> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<KopfgDTO> set = new HashSet<KopfgDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( Kopfg kopfg : entitySet ) {
            set.add( toDto( kopfg ) );
        }

        return set;
    }
}
