package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.enumeration.PrivilegeCode;
import com.idevhub.tapas.service.criteria.CEAEmployeeCriteria;
import com.idevhub.tapas.service.dto.CEAEmployeeContextDTO;
import com.idevhub.tapas.service.dto.CEAEmployeeDTO;
import com.idevhub.tapas.service.dto.CeaEmployeeMainInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CEAEmployeeService {

    CEAEmployeeDTO saveEmployee(CEAEmployeeDTO employeeDTO);

    void deleteEmployee(Long employeeId);

    Page<CEAEmployeeDTO> findAll(Pageable pageable, CEAEmployeeCriteria criteria);

    CEAEmployeeDTO getById(Long employeeId);

    CEAEmployeeContextDTO getDepartmentHead(Long departmentId);

    CEAEmployeeContextDTO getCurrentContext();

    CeaEmployeeMainInfoDTO getCeaUserMainInfo();

    CEAEmployeeContextDTO getCeaEmployeeContext(Long userId, String ceaCode);

    List<CEAEmployeeContextDTO> getAllActiveCeaEmployeeHasAuth(String fullNameLike, PrivilegeCode privilegeCode);
}
