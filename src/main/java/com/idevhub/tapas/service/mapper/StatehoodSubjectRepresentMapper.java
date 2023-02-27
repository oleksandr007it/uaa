package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.service.dto.StatehoodSubjectRepresentDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectRepresentMainInfoDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectRepresentWithNameDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper for the entity StatehoodSubjectRepresent and its DTO StatehoodSubjectRepresentDTO.
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, StatehoodSubjectMapper.class})
public interface StatehoodSubjectRepresentMapper extends EntityMapper<StatehoodSubjectRepresentDTO, StatehoodSubjectRepresent> {

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "deletedBy", target = "deletedBy")
    @Mapping(source = "currentContext", target = "isCurrentContext")
    @Mapping(source = "statehoodSubject.id", target = "statehoodSubjectId")
    @Mapping(source = "declarant.id", target = "declarantId")
    @Mapping(source = "declarant.login", target = "declarantLogin")
    StatehoodSubjectRepresentDTO toDto(StatehoodSubjectRepresent statehoodSubjectRepresent);

    @Mapping(source = "statehoodSubject.id", target = "statehoodSubjectId")
    @Mapping(source = "statehoodSubject.subjectName", target = "statehoodSubjectFullName")
    @Mapping(source = "statehoodSubject.subjectCode", target = "statehoodSubjectEdrpou")
    @Mapping(source = "declarant.id", target = "declarantId")
    @Mapping(source = "declarant.firstName", target = "declarantFirstName")
    @Mapping(source = "declarant.lastName", target = "declarantLastName")
    @Mapping(source = "declarant.middleName", target = "declarantMiddleName")
    @Mapping(source = "declarant.fullName", target = "declarantFullName")
    @Mapping(source = "statehoodSubject.accountDetailsStatus", target = "accountDetailsStatus")
    @Mapping(source = "statehoodSubject.subjectStatus", target = "subjectStatus")
    @Mapping(source = "subjectRepresentType", target = "subjectRepresentType")
    StatehoodSubjectRepresentMainInfoDTO toMainInfoDto(StatehoodSubjectRepresent statehoodSubjectRepresent);

    @IterableMapping(qualifiedByName = "toMainInfoDto")
    List<StatehoodSubjectRepresentMainInfoDTO> toMainInfoDto(List<StatehoodSubjectRepresent> statehoodSubjectRepresent);

    @Mapping(source = "declarant.firstName", target = "firstName")
    @Mapping(source = "declarant.lastName", target = "lastName")
    @Mapping(source = "declarant.middleName", target = "middleName")
    @Mapping(source = "statehoodSubject.id", target = "statehoodSubjectId")
    @Mapping(source = "declarant.id", target = "declarantId")
    StatehoodSubjectRepresentWithNameDTO toDtoWithName(StatehoodSubjectRepresent represent);

    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "deletedBy", target = "deletedBy")
    @Mapping(source = "statehoodSubjectId", target = "statehoodSubject")
    @Mapping(source = "declarantId", target = "declarant")
    StatehoodSubjectRepresent toEntity(StatehoodSubjectRepresentDTO statehoodSubjectRepresentDTO);

    default StatehoodSubjectRepresent fromId(Long id) {
        if (id == null) {
            return null;
        }
        StatehoodSubjectRepresent statehoodSubjectRepresent = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent.setId(id);
        return statehoodSubjectRepresent;
    }
}
