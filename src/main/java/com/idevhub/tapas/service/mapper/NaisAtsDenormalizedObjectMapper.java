package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.address.NaisAtsDenormalizedObject;
import com.idevhub.tapas.service.dto.NaisAtsObjectNameDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface NaisAtsDenormalizedObjectMapper {

    @Mapping(target = "name", expression = "java(getObjectName(atsObject))")
    NaisAtsObjectNameDTO toDto(NaisAtsDenormalizedObject atsObject);

    List<NaisAtsObjectNameDTO> toDto(List<NaisAtsDenormalizedObject> atsObject);

    default String getObjectName(NaisAtsDenormalizedObject atsObject) {
        String name = null;
        switch (atsObject.getType()) {
            case LOCALITY:
                name = buildLocalityName(atsObject);
                break;
            case LOCALITY_OBJECT:
                name = atsObject.getLocalityObjectName();
                break;
        }
        return name;
    }

    default String buildLocalityName(NaisAtsDenormalizedObject atsObject) {
        StringBuilder nameBuilder = new StringBuilder();
        nameBuilder.append(atsObject.getRegionName() == null || atsObject.getRegionName().isBlank() ? "" : atsObject.getRegionName() + ", ")
            .append(atsObject.getDistrictName() == null || atsObject.getDistrictName().isBlank() ? "" : atsObject.getDistrictName() + ", ")
            .append(atsObject.getLocalityName());
        return nameBuilder.toString();
    }
}
