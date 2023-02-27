package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.Privilege;
import com.idevhub.tapas.service.dto.PrivilegeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

/**
 * Mapper for the entity Kopfg and its DTO KopfgDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PrivilegeMapper {

    PrivilegeDTO toDto(Privilege privilege);

    @Mapping(target = "privilegeType", ignore = true)
    @Mapping(target = "privilegeCategory", ignore = true)
    Privilege toEntity(PrivilegeDTO privilegeDTO);

    Set<Privilege> toEntity(Iterable<PrivilegeDTO> privileges);
}
