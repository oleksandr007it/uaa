package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.address.NaisAtsObjectStatus;
import com.idevhub.tapas.service.dto.NaisAtsObjectStatusDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity NaisAtsObjectStatus and its DTO NaisAtsObjectStatusDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface NaisAtsObjectStatusMapper extends EntityMapper<NaisAtsObjectStatusDTO, NaisAtsObjectStatus> {


    default NaisAtsObjectStatus fromId(Long code) {
        if (code == null) {
            return null;
        }
        NaisAtsObjectStatus naisAtsObjectStatus = new NaisAtsObjectStatus();
        naisAtsObjectStatus.setCode(code);
        return naisAtsObjectStatus;
    }
}
