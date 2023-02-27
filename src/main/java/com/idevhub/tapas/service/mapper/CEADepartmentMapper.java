package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.CEADepartment;
import com.idevhub.tapas.service.dto.CEADepartmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for the entity CEADepartment and its DTO CEADepartmentDTO.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CentralExecutiveAuthorityMapper.class, AddressMapper.class})
public interface CEADepartmentMapper extends EntityMapper<CEADepartmentDTO, CEADepartment> {

    @Mapping(source = "centralExecutiveAuthority.id", target = "centralExecutiveAuthorityId")
    @Mapping(source = "centralExecutiveAuthority.code", target = "centralExecutiveAuthorityCode")
    @Mapping(source = "address.id", target = "addressId")
    CEADepartmentDTO toDto(CEADepartment ceaDepartment);

    @Mapping(source = "centralExecutiveAuthorityId", target = "centralExecutiveAuthority")
    @Mapping(source = "addressId", target = "address")
    CEADepartment toEntity(CEADepartmentDTO ceaDepartmentDTO);

    default CEADepartment fromId(Long id) {
        if (id == null) {
            return null;
        }
        CEADepartment ceaDepartment = new CEADepartment();
        ceaDepartment.setId(id);
        return ceaDepartment;
    }
}
