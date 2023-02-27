package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.CEADepartment;
import com.idevhub.tapas.domain.CEAEmployee;
import com.idevhub.tapas.domain.CentralExecutiveAuthority;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.enumeration.CEADepartmentStatus;
import com.idevhub.tapas.domain.enumeration.CentralExecutiveAuthorityStatus;
import com.idevhub.tapas.domain.enumeration.PositionType;
import com.idevhub.tapas.domain.enumeration.PrivilegeCode;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.SecurityUtils;
import com.idevhub.tapas.service.CEAEmployeeService;
import com.idevhub.tapas.service.CentralExecutiveAuthorityService;
import com.idevhub.tapas.service.UserService;
import com.idevhub.tapas.service.criteria.CEAEmployeeCriteria;
import com.idevhub.tapas.service.criteria.CEAEmployeeSpecificationHelper;
import com.idevhub.tapas.service.dto.CEAEmployeeContextDTO;
import com.idevhub.tapas.service.dto.CEAEmployeeDTO;
import com.idevhub.tapas.service.dto.CeaEmployeeMainInfoDTO;
import com.idevhub.tapas.service.errors.*;
import com.idevhub.tapas.service.mapper.UserMapper;
import io.github.jhipster.service.filter.StringFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CEAEmployeeServiceImpl implements CEAEmployeeService {

    private final CEAEmployeeSpecificationHelper specificationHelper;
    private final UserRepository repository;
    private final UserService userService;
    private final CentralExecutiveAuthorityService ceaService;
    private final UserMapper mapper;

    @Override
    public CEAEmployeeDTO saveEmployee(CEAEmployeeDTO toSave) {

        log.debug("Trying to save cea(edrpou={}) employee (id={})...", toSave.getEdrpouCode(), toSave.getId());

        CEAEmployeeContextDTO initiatorContext = toSave.getId() == null ?
            getInitiatorAndCheckAuth(PrivilegeCode.V2_1_5) :
            getInitiatorAndCheckAuth(PrivilegeCode.V2_1_3);

        if (!initiatorContext.getCeaCode().equals(toSave.getEdrpouCode()))
            throw new InappropriateParametersException("error.cea.inappropriate.codes", initiatorContext.getCeaCode(), toSave.getEdrpouCode());

        CEAEmployee employee = buildEmployee(toSave);

        return mapper.toCeaEmployeeDto(userService.saveCeaEmployeeAccount(employee));
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        log.debug("Trying to delete cea employee with id={}...", employeeId);
        CEAEmployeeContextDTO initiatorContext = getInitiatorAndCheckAuth(PrivilegeCode.V2_1_4);
        userService.softDeleteCeaEmployeeAccount(employeeId, initiatorContext.getCeaCode());
    }

    @Override
    public Page<CEAEmployeeDTO> findAll(Pageable pageable, CEAEmployeeCriteria criteria) {
        log.debug("Request to get all CEAEmployees by pageable and criteria");

        CEAEmployeeContextDTO initiatorContext = getInitiatorAndCheckAuth(PrivilegeCode.V2_1_1);

        StringFilter statusFilter = new StringFilter();
        statusFilter.setNotEquals(UserStatus.DELETED);

        StringFilter ceaCodeFilter = new StringFilter();
        ceaCodeFilter.setEquals(initiatorContext.getCeaCode());

        criteria.setStatus(statusFilter);
        criteria.setCeaCode(ceaCodeFilter);

        Specification<User> specification =
            specificationHelper.createSpecification(criteria);

        return repository.findAll(specification, pageable)
            .map(mapper::toCeaEmployeeDto);
    }

    @Override
    public CEAEmployeeDTO getById(Long employeeId) {
        log.debug("Trying to get cea employee with id={}...", employeeId);
        CEAEmployeeContextDTO initiatorContext = getInitiatorAndCheckAuth(PrivilegeCode.V2_1_2);
        User user = repository.findCeaEmployee(employeeId, initiatorContext.getCeaCode())
            .orElseThrow(() -> new UserNotFoundException("error.user.not.found.by.id.and.cea.code", employeeId.toString(), initiatorContext.getCeaCode()));
        return mapper.toCeaEmployeeDto(user);
    }

    @Override
    public CEAEmployeeContextDTO getDepartmentHead(Long departmentId) {
        log.debug("Trying to get head of cea department with id={}...", departmentId);
        CEAEmployeeContextDTO initiatorContext = getInitiatorAndCheckAuth(PrivilegeCode.V2_1_2);
        User user = repository.findAllCeaEmployeeByDepartmentId(departmentId, initiatorContext.getCeaCode()).stream()
            .filter(employee -> PositionType.HEAD == employee.getPositionType())
            .findFirst()
            .orElseThrow(() -> new UserNotFoundException("error.user.not.found"));
        return mapper.toCeaEmployeeContextDto(user);
    }

    @Override
    public CEAEmployeeContextDTO getCurrentContext() {
        log.debug("Request to get current CEAEmployee context");

        if (!SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.CEA_EMPLOYEE))
            throw new WrongUserTypeException("error.user.wrong.type");

        User currentUser = userService.getCurrentUser();

        return mapper.toCeaEmployeeContextDto(currentUser);
    }

    @Override
    public CeaEmployeeMainInfoDTO getCeaUserMainInfo() {
        User ceaUser = userService.getCurrentUser();

        CeaEmployeeMainInfoDTO target =
            mapper.toCeaEmployeeMainInfoDto(ceaUser);

        return target;
    }

    @Override
    public CEAEmployeeContextDTO getCeaEmployeeContext(Long userId, String ceaCode) {

        log.debug("Trying to get employee by user id={} and cea code={}", userId, ceaCode);

        User currentUser = repository.findCeaEmployee(userId, ceaCode)
            .orElseThrow(() -> new UserNotFoundException("error.user.not.found.by.id.and.cea.code", userId.toString(), ceaCode));

        return mapper.toCeaEmployeeContextDto(currentUser);
    }

    @Override
    public List<CEAEmployeeContextDTO> getAllActiveCeaEmployeeHasAuth(String fullNameLike, PrivilegeCode privilegeCode) {
        log.debug("Trying to get active cea employees that has privilegeCode={}", privilegeCode);

        CEAEmployeeContextDTO initiatorContext = getInitiatorAndCheckAuth(PrivilegeCode.V2_1_1);

        CEAEmployeeCriteria criteria = new CEAEmployeeCriteria();

        StringFilter ceaCodeFilter = new StringFilter();
        ceaCodeFilter.setEquals(initiatorContext.getCeaCode());
        criteria.setCeaCode(ceaCodeFilter);

        StringFilter statusFilter = new StringFilter();
        statusFilter.setEquals(UserStatus.ACTIVE);
        criteria.setStatus(statusFilter);

        if (fullNameLike != null) {
            StringFilter fullNameLikeFilter = new StringFilter();
            fullNameLikeFilter.setContains(fullNameLike);
            criteria.setFullName(fullNameLikeFilter);
        }

        Specification<User> specification = specificationHelper.createSpecification(criteria);

        return repository.findAll(specification).stream()
            .map(mapper::toCeaEmployeeContextDto)
            .filter(employee -> employee.getPrivilegeCodes().contains(privilegeCode))
            .collect(Collectors.toList());
    }

    private CEAEmployee buildEmployee(CEAEmployeeDTO employeeDTO) {

        CentralExecutiveAuthority cea = ceaService.getByCode(employeeDTO.getEdrpouCode());
        if (cea.getCentralExecutiveAuthorityStatus() != CentralExecutiveAuthorityStatus.ACTIVE)
            throw new EntityStatusException("error.cea.inactive.status", cea.getCode(), cea.getCentralExecutiveAuthorityStatus().name());

        CEADepartment department = employeeDTO.getDepartmentId() == null ? null : ceaService.getDepartment(employeeDTO.getEdrpouCode(), employeeDTO.getDepartmentId());
        if (department != null && department.getCeaDepartmentStatus() != CEADepartmentStatus.ACTIVE)
            throw new EntityStatusException("error.cea.department.inactive.status", department.getId().toString(), department.getCeaDepartmentStatus().name());

        return CEAEmployee.builder()
            .id(employeeDTO.getId())
            .firstName(employeeDTO.getFirstName())
            .lastName(employeeDTO.getLastName())
            .middleName(employeeDTO.getMiddleName())
            .rnokpp(employeeDTO.getRnokpp())
            .email(employeeDTO.getEmail())
            .phoneNumber(employeeDTO.getPhoneNumber())
            .cea(cea)
            .ceaDepartment(department)
            .position(employeeDTO.getPosition())
            .positionType(employeeDTO.getPositionType())
            .build();
    }

    private CEAEmployeeContextDTO getInitiatorAndCheckAuth(PrivilegeCode privilege) {
        log.debug("Getting initiator context and check privilege code {}", privilege);
        CEAEmployeeContextDTO initiatorContext = getCurrentContext();
        throwIfNoAuth(privilege, initiatorContext);
        return initiatorContext;
    }

    private void throwIfNoAuth(PrivilegeCode privilege, CEAEmployeeContextDTO employeeToCheck) {
//        log.debug("Checking privilege with code={} for user {}...", privilege, employeeToCheck.getUserId());
//        if (!employeeToCheck.getPrivilegeCodes().contains(privilege))
//            throw new AuthException("error.user.no.privilege", employeeToCheck.getUserId().toString(), privilege.name());
//        log.debug("User {} has privilege {}", employeeToCheck.getUserId(), privilege);
    }
}
