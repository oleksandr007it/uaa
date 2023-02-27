package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.PrivilegeCategory;
import com.idevhub.tapas.service.dto.PrivilegeCategoryDTO;
import org.mapstruct.Mapper;

import java.util.Set;

/**
 * Mapper for the entity Kopfg and its DTO KopfgDTO.
 */
@Mapper(componentModel = "spring", uses = {PrivilegeMapper.class})
public interface PrivilegeCategoryMapper {
    PrivilegeCategoryDTO toDto(PrivilegeCategory category);

    Set<PrivilegeCategoryDTO> toDto(Iterable<PrivilegeCategory> categories);
}
