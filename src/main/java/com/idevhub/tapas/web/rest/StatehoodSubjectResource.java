package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.HasPrivilege;
import com.idevhub.tapas.privilege.service.constant.PRIVILEGE;
import com.idevhub.tapas.service.StatehoodSubjectService;
import com.idevhub.tapas.service.criteria.StatehoodSubjectCriteria;
import com.idevhub.tapas.service.dto.*;
import com.idevhub.tapas.service.dto.StatehoodSubjectDTO;
import com.idevhub.tapas.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StatehoodSubjectResource {

    private static final String ENTITY_NAME = "statehoodSubject";
    private final Logger log = LoggerFactory.getLogger(StatehoodSubjectResource.class);
    private final StatehoodSubjectService statehoodSubjectService;
    @Value("${jhipster.clientApp.name}")
    private String APPLICATION_NAME;

    public StatehoodSubjectResource(StatehoodSubjectService statehoodSubjectService) {
        this.statehoodSubjectService = statehoodSubjectService;
    }

    @PostMapping("/statehood-subjects")
    @HasPrivilege(PRIVILEGE.LEGAL_ENTITY.V1_3_1)
    @Timed
    public ResponseEntity<StatehoodSubjectDTO> createStatehoodSubject(@Valid @RequestBody StatehoodSubjectCreateDTO inputDTO) throws URISyntaxException {
        log.debug("REST request to save StatehoodSubject : {}", inputDTO);

        StatehoodSubjectDTO result = statehoodSubjectService.save(inputDTO);

        return ResponseEntity.created(new URI("/api/statehood-subjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(APPLICATION_NAME, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/statehood-subjects/search/active/{code}")
    @Timed
    public ResponseEntity<SubjectInfoForAdd> searchAcctiveStatehoodSubject(@PathVariable String code) {
        log.debug("REST request to get all StatehoodSubjects");
        SubjectInfoForAdd subjectInfoForAdd = statehoodSubjectService.getActiveSubjectsByCodeOrPassport(code);
        return ResponseEntity.ok().body(subjectInfoForAdd);
    }

    @PostMapping("/statehood-subjects/create-with-represeter")
    @Timed
    public ResponseEntity<Void> createStatehoodSubjectWithRepreseter(@Valid @RequestBody SubjectInfoForAdd inputDTO) throws URISyntaxException {
        log.debug("REST request to createStatehoodSubjectWithRepreseter : {}", inputDTO);
        statehoodSubjectService.createStatehoodSubjectAndAddRepreseter(inputDTO);
        return ResponseEntity.created(new URI("/api/statehood-subjects/"))
            .headers(HeaderUtil.createEntityCreationAlert(APPLICATION_NAME, false, ENTITY_NAME, "")).build();
    }


    @HasPrivilege(PRIVILEGE.LEGAL_ENTITY.V1_1_1)
    @PutMapping("/statehood-subjects")
    @Timed
    public ResponseEntity<StatehoodSubjectDTO> updateStatehoodSubject(@Valid @RequestBody StatehoodSubjectDTO statehoodSubjectDTO) throws URISyntaxException {
        log.debug("REST request to update StatehoodSubject : {}", statehoodSubjectDTO);
        if (statehoodSubjectDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        StatehoodSubjectDTO result = statehoodSubjectService.update(statehoodSubjectDTO);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(APPLICATION_NAME, false, ENTITY_NAME, statehoodSubjectDTO.getId().toString()))
            .body(result);
    }

    @PutMapping("/statehood-subjects/confirm-email")
    public ResponseEntity<Void> sendConfirmationEmail(@RequestBody StatehoodSubjectSendConfirmEmailDTO inputData) {
        statehoodSubjectService.sendSubjectConfirmationEmail(inputData);

        return ResponseEntity.ok().build();
    }

    /**
     * GET  /statehood-subjects : get all the statehoodSubjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of statehoodSubjects in body
     */
    @GetMapping("/statehood-subjects")
    @HasPrivilege(PRIVILEGE.LEGAL_ENTITY.V1_3_1)
    @Timed
    public ResponseEntity<List<StatehoodSubjectDTO>> getAllStatehoodSubjects(Pageable pageable, StatehoodSubjectCriteria criteria) {
        log.debug("REST request to get all StatehoodSubjects");

        Page<StatehoodSubjectDTO> page = statehoodSubjectService.findAll(pageable, criteria);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /statehood-subjects/:id : get the "id" statehoodSubject.
     *
     * @param id the id of the statehoodSubjectDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the statehoodSubjectDTO, or with status 404 (Not Found)
     */
    @GetMapping("/statehood-subjects/{id}")
    @Timed
    @PreAuthorize("hasAuthority(T(com.idevhub.tapas.security.AuthoritiesConstants).DECLARANT)")
    public ResponseEntity<StatehoodSubjectDTO> getStatehoodSubject(@PathVariable Long id) {
        log.debug("REST request to get StatehoodSubject : {}", id);

        StatehoodSubjectDTO statehoodSubjectDTO = statehoodSubjectService.findOne(id);

        return ResponseEntity.ok().body(statehoodSubjectDTO);
    }

    /**
     * DELETE  /statehood-subjects/:id : delete the "id" statehoodSubject.
     *
     * @param id the id of the statehoodSubjectDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/statehood-subjects/{id}")
    @Timed
    public ResponseEntity<Void> deleteStatehoodSubject(@PathVariable Long id) {
        log.debug("REST request to delete StatehoodSubject : {}", id);
        statehoodSubjectService.delete(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(APPLICATION_NAME, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/statehood-subjects/current")
    public ResponseEntity<StatehoodSubjectWithPrivilegeGroupsDTO> getCurrentSubjectWithAuthGroups() {
        log.debug("REST request to get current subject by active context");
        return ResponseEntity.ok().body(statehoodSubjectService.getCurrentSubjectByRepresentativeWithPrivilegeGroups());
    }
}
