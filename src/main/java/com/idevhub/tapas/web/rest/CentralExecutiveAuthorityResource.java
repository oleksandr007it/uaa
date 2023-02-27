package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.service.CentralExecutiveAuthorityService;
import com.idevhub.tapas.service.dto.CEADepartmentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CentralExecutiveAuthorityResource {

    private final CentralExecutiveAuthorityService service;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @GetMapping("/cea/departments/active")
    public ResponseEntity<List<CEADepartmentDTO>> getAllActiveDepartments(@NotBlank @RequestParam String edrpou) {
        log.debug("REST request to get department list of cea (code = {}).", edrpou);
        return ResponseEntity.ok().body(service.getAllActiveDepartments(edrpou));
    }
}
