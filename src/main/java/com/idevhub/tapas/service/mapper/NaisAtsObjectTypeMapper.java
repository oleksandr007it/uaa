package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.address.NaisAtsObjectType;
import com.idevhub.tapas.service.dto.NaisAtsObjectTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity NaisAtsObjectType and its DTO NaisAtsObjectTypeDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface NaisAtsObjectTypeMapper extends EntityMapper<NaisAtsObjectTypeDTO, NaisAtsObjectType> {


    default NaisAtsObjectType fromId(Long level, Long code) {
        if (level == null || code == null) {
            return null;
        }
        NaisAtsObjectType naisAtsObjectType = new NaisAtsObjectType();
        naisAtsObjectType.setLevel(level);
        naisAtsObjectType.setCode(code);
        return naisAtsObjectType;
    }
}
