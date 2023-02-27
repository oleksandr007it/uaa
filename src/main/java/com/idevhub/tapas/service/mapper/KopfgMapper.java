package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.Kopfg;
import com.idevhub.tapas.service.dto.KopfgDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Kopfg and its DTO KopfgDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface KopfgMapper extends EntityMapper<KopfgDTO, Kopfg> {


    default Kopfg fromId(Long id) {
        if (id == null) {
            return null;
        }
        Kopfg kopfg = new Kopfg();
        kopfg.setId(id);
        return kopfg;
    }
}
