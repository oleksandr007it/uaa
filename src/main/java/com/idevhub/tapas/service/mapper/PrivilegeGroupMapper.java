package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.PrivilegeGroup;
import com.idevhub.tapas.service.dto.PrivilegeGroupGeneralDTO;
import com.idevhub.tapas.service.dto.PrivilegeGroupWithPrivilegesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.Set;

/**
 * Mapper for the entity Address and its DTO AddressDTO.
 */
@Mapper(componentModel = "spring", uses = {PrivilegeMapper.class})
public interface PrivilegeGroupMapper {
    Set<PrivilegeGroupGeneralDTO> toDto(Collection<PrivilegeGroup> groups);

    PrivilegeGroupWithPrivilegesDTO toDto(PrivilegeGroup group);
}
