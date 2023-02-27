package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.CentralExecutiveAuthority;
import com.idevhub.tapas.service.dto.CentralExecutiveAuthorityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity CentralExecutiveAuthority and its DTO CentralExecutiveAuthorityDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AddressMapper.class})
public interface CentralExecutiveAuthorityMapper extends EntityMapper<CentralExecutiveAuthorityDTO, CentralExecutiveAuthority> {

    @Mapping(source = "address.id", target = "addressId")
    CentralExecutiveAuthorityDTO toDto(CentralExecutiveAuthority centralExecutiveAuthority);

    @Mapping(source = "addressId", target = "address")
    CentralExecutiveAuthority toEntity(CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO);

    default CentralExecutiveAuthority fromId(Long id) {
        if (id == null) {
            return null;
        }
        CentralExecutiveAuthority centralExecutiveAuthority = new CentralExecutiveAuthority();
        centralExecutiveAuthority.setId(id);
        return centralExecutiveAuthority;
    }
}
