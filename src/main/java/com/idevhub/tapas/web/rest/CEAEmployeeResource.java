package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.HasPrivilege;
import com.idevhub.tapas.domain.enumeration.PrivilegeCode;
import com.idevhub.tapas.privilege.service.constant.PRIVILEGE;
import com.idevhub.tapas.service.CEAEmployeeService;
import com.idevhub.tapas.service.criteria.CEAEmployeeCriteria;
import com.idevhub.tapas.service.dto.CEAEmployeeContextDTO;
import com.idevhub.tapas.service.dto.CEAEmployeeDTO;
import com.idevhub.tapas.service.dto.CeaEmployeeMainInfoDTO;
import com.idevhub.tapas.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class CEAEmployeeResource {

    private final CEAEmployeeService service;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public CEAEmployeeResource(CEAEmployeeService service) {
        this.service = service;
    }

    @HasPrivilege(PRIVILEGE.CEA.V2_1_5)
    @PostMapping("/cea/employees")
    public ResponseEntity<CEAEmployeeDTO> createCeaEmployee(@Valid @RequestBody CEAEmployeeDTO employeeDTO) throws URISyntaxException {
        log.debug("REST request to create employee for cea with code={}", employeeDTO.getEdrpouCode());
        if (employeeDTO.getId() != null)
            throw new BadRequestAlertException("A new user cannot already have an ID", "ceaEmployee", "id.exists");
        CEAEmployeeDTO employee = service.saveEmployee(employeeDTO);
        return ResponseEntity.created(new URI("/api/employees/" + employee.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, "ceaEmployee", employee.getId().toString()))
            .body(employee);
    }

    @HasPrivilege(PRIVILEGE.CEA.V2_1_3)
    @PutMapping("/cea/employees")
    public ResponseEntity<CEAEmployeeDTO> updateCeaEmployee(@Valid @RequestBody CEAEmployeeDTO employeeDTO) {
        log.debug("REST request to update employee of cea with code={}", employeeDTO.getEdrpouCode());
        if (employeeDTO.getId() == null)
            throw new BadRequestAlertException("User with null ID cannot be updated", "ceaEmployee", "id.null");
        CEAEmployeeDTO updated = service.saveEmployee(employeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, "ceaEmployee", updated.getId().toString()))
            .body(updated);
    }

    @HasPrivilege(PRIVILEGE.CEA.V2_1_4)
    @DeleteMapping("/cea/employees/{id}")
    public ResponseEntity<Void> deleteCeaEmployee(@NotNull @PathVariable Long id) {
        log.debug("REST request to delete employee with id={}", id);
        service.deleteEmployee(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, "ceaEmployee", id.toString()))
            .build();

    }

    @HasPrivilege(PRIVILEGE.CEA.V2_1_1)
    @GetMapping("/cea/employees")
    @Timed
    public ResponseEntity<List<CEAEmployeeDTO>> findAll(Pageable pageable, CEAEmployeeCriteria criteria) {
        log.debug("Request to find all CEAEmployees");

        Page<CEAEmployeeDTO> page = service.findAll(pageable, criteria);

        HttpHeaders headers =
            PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @HasPrivilege(PRIVILEGE.CEA.V2_1_2)
    @GetMapping("/cea/employees/{id}")
    public ResponseEntity<CEAEmployeeDTO> getCeaEmployeeById(@NotNull @PathVariable Long id) {
        log.debug("REST request to get employee by id={}", id);
        CEAEmployeeDTO target = service.getById(id);
        return ResponseEntity.ok(target);
    }

    @GetMapping("/cea/employees/head/{departmentId}")
    public CEAEmployeeContextDTO getCeaDepartmentHead(@PathVariable Long departmentId) {
        log.debug("Request to get head of cea department with id={}", departmentId);
        return service.getDepartmentHead(departmentId);
    }

    @GetMapping("/cea/employees/current-context")
    public CEAEmployeeContextDTO getCeaEmployeeCurrentContext() {
        log.debug("Request to get current CEAEmployee context");
        return service.getCurrentContext();
    }

    @GetMapping("/cea/employees/context/{userId}")
    public CEAEmployeeContextDTO getCeaEmployee(@PathVariable Long userId, @RequestParam String ceaCode) {
        log.debug("Request to get employee by user id={} and cea code={}", userId, ceaCode);
        return service.getCeaEmployeeContext(userId, ceaCode);
    }

    @GetMapping("/cea/employees/has-auth")
    public List<CEAEmployeeContextDTO> getAllCeaEmployeeHasAuth(@RequestParam(required = false) String fullNameLike, @RequestParam PrivilegeCode privilegeCode) {
        log.debug("Request to get active cea employees that has privilegeCode={}", privilegeCode);
        return service.getAllActiveCeaEmployeeHasAuth(fullNameLike, privilegeCode);
    }

    @GetMapping("/cea/user")
    public ResponseEntity<CeaEmployeeMainInfoDTO> getCeaUser() {
        log.debug("REST request to get logged in Cea User info");

        CeaEmployeeMainInfoDTO target = service.getCeaUserMainInfo();

        return ResponseEntity.ok(target);
    }
}
