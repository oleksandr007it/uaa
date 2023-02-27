package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.service.dto.StatehoodSubjectCreateDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity StatehoodSubject and its DTO StatehoodSubjectDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, KopfgMapper.class, AddressMapper.class})
public interface StatehoodSubjectMapper extends EntityMapper<StatehoodSubjectDTO, StatehoodSubject> {

    @Mapping(source = "kopfg.id", target = "kopfgId")
    @Mapping(source = "kopfg.code", target = "kopfgCode")
    @Mapping(source = "emailApproved", target = "isEmailApproved")
    StatehoodSubjectDTO toDto(StatehoodSubject statehoodSubject);

    @Mapping(source = "kopfgId", target = "kopfg")
    @Mapping(target = "legalAddress", ignore = true)
    @Mapping(target = "actualAddress", ignore = true)
    StatehoodSubject toEntity(StatehoodSubjectCreateDTO statehoodSubjectDTO);

    @Mapping(source = "kopfgId", target = "kopfg")
    @Mapping(source = "isEmailApproved", target = "emailApproved")
    StatehoodSubject toEntity(StatehoodSubjectDTO statehoodSubjectDTO);

    @AfterMapping
    default void establishActualAddress(StatehoodSubject subject, @MappingTarget StatehoodSubjectDTO subjectDTO) {
        if (subject.isActualSameAsLegalAddress()) {
            subjectDTO.setActualAddress(subjectDTO.getLegalAddress());
        }
    }

    default StatehoodSubject fromId(Long id) {
        if (id == null) {
            return null;
        }
        StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setId(id);
        return statehoodSubject;
    }
}
