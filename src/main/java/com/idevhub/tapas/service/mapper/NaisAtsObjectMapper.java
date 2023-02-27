package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.address.NaisAtsObject;
import com.idevhub.tapas.service.dto.NaisAtsObjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity NaisAtsObject and its DTO NaisAtsObjectDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NaisAtsObjectStatusMapper.class, NaisAtsObjectTypeMapper.class})
public interface NaisAtsObjectMapper extends EntityMapper<NaisAtsObjectDTO, NaisAtsObject> {

    NaisAtsObjectDTO toDto(NaisAtsObject naisAtsObject);

    NaisAtsObject toEntity(NaisAtsObjectDTO naisAtsObjectDTO);

    default NaisAtsObject fromId(Long id) {
        if (id == null) {
            return null;
        }
        NaisAtsObject naisAtsObject = new NaisAtsObject();
        naisAtsObject.setId(id);
        return naisAtsObject;
    }
}
