package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.HasPrivilege;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.privilege.service.constant.PRIVILEGE;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.service.StatehoodSubjectRepresentService;
import com.idevhub.tapas.service.criteria.RepresentCriteria;
import com.idevhub.tapas.service.dto.*;
import com.idevhub.tapas.service.impl.StatehoodSubjectRepresentOperationService;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing StatehoodSubjectRepresent.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StatehoodSubjectRepresentResource {

    private static final String ENTITY_NAME = "statehoodSubjectRepresent";
    private final Logger log = LoggerFactory.getLogger(StatehoodSubjectRepresentResource.class);
    private final StatehoodSubjectRepresentService representService;
    private final StatehoodSubjectRepresentOperationService subjectRepresentOperationService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    /**
     * POST  /statehood-subject-represents : Create a new statehoodSubjectRepresent.
     *
     * @param input the statehoodSubjectRepresentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new statehoodSubjectRepresentDTO, or with status 400 (Bad Request) if the statehoodSubjectRepresent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/statehood-subject-represents")
    @Timed
    public ResponseEntity<StatehoodSubjectRepresentDTO> createStatehoodSubjectRepresent(@Valid @RequestBody StatehoodSubjectRepresentCreateDTO input) throws URISyntaxException {
        log.debug("REST request to save StatehoodSubjectRepresent : {}", input);

        StatehoodSubjectRepresentDTO result = representService.create(input);

        return ResponseEntity.created(new URI("/api/statehood-subject-represents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /statehood-subject-represents : Updates an existing statehoodSubjectRepresent.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the updated statehoodSubjectRepresentDTO,
     * or with status 400 (Bad Request) if the statehoodSubjectRepresentDTO is not valid,
     * or with status 500 (Internal Server Error) if the statehoodSubjectRepresentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @HasPrivilege(PRIVILEGE.LEGAL_ENTITY.V1_3_3)
    @PutMapping("/statehood-subject-represents")
    @Timed
    public ResponseEntity<StatehoodSubjectRepresentDTO> updateStatehoodSubjectRepresent(@Valid @RequestBody StatehoodSubjectRepresentUpdateDTO inputDto) {
        log.debug("REST request to update StatehoodSubjectRepresent : {}", inputDto);

        StatehoodSubjectRepresentDTO result = representService.update(inputDto);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @GetMapping("/statehood-subject-represents/all")
    @Timed
    public ResponseEntity<List<StatehoodSubjectRepresentMainInfoDTO>> findAllRepresentersBySubjectId(Pageable pageable, RepresentCriteria criteria) {
        log.debug("REST request to get all StatehoodSubjectRepresents by subject with id: {}", criteria.getSubjectId());

        Page<StatehoodSubjectRepresentMainInfoDTO> page =
            representService.findAllRepresentersBySubjectId(pageable, criteria);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @Generated(value = "This is generate a sample java function StatehoodSubject")
    @GetMapping("/statehood-subject-represents")
    @Timed
    @Secured(AuthoritiesConstants.DECLARANT)
    public List<StatehoodSubjectRepresentMainInfoDTO> getAllActiveByCurrentDeclarant() {
        log.debug("REST request to get all StatehoodSubjectRepresents by current Declarant");
        return representService.getAllActiveByCurrentDeclarant();
    }


    @GetMapping("/statehood-subject-represents/{declarantId}/{subjectId}")
    @Timed
    public ResponseEntity<Boolean> hasAccessToSubj(@PathVariable Long declarantId,
                                                   @PathVariable Long subjectId) {
        log.debug("REST request to check if declarant with ID {} has access to statehood subject with id {}", declarantId, subjectId);

        Boolean hasAccess = representService.hasAccessByIds(declarantId, subjectId);

        return ResponseEntity.ok(hasAccess);
    }


    @GetMapping("/statehood-subject-represents/dataforsign/{subjectId}")
    @Timed
    public ResponseEntity<String> chekRepresenterAndReturnDataForSign(@PathVariable Long subjectId) {
        log.debug("REST request to chek Representer And Return Data For Sign  subject with id {}", subjectId);
        String base64ForSign = subjectRepresentOperationService.chekRepresenterAndReturnDataForSign(subjectId);
        return ResponseEntity.ok(base64ForSign);
    }


    @PutMapping("/statehood-subject-represents/update-status")
    @Timed
    public ResponseEntity<StatehoodSubjectRepresent> updateRepresenterStatus(@Valid @RequestBody StatehoodSubjectRepresentUpdateStatusDTO input) {
        log.debug("REST request to update representer Status : {}", input);

        StatehoodSubjectRepresent statehoodSubjectRepresent = subjectRepresentOperationService.updateRepresenterStatus(input);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statehoodSubjectRepresent.getId().toString()))
            .body(statehoodSubjectRepresent);
    }


    @GetMapping("/statehood-subject-represents/current")
    @Timed
    public ResponseEntity<StatehoodSubjectRepresentContextDTO> getCurrentContext() {
        log.debug("REST request to get current user active context");

        StatehoodSubjectRepresentContextDTO targetDto =
            representService.getCurrentContextMainDTO();

        return ResponseEntity.ok().body(targetDto);
    }

    @GetMapping("/statehood-subject-represents/current-subject")
    public ResponseEntity<StatehoodSubjectRepresentDTO> getCurrentSubjectWithAuthGroups() {
        log.debug("REST request to get current subject by active context");
        return ResponseEntity.ok().body(representService.getCurrentRepresentDto());
    }

    /**
     * GET  /statehood-subject-represents/:id : get the "id" statehoodSubjectRepresent.
     *
     * @param id the id of the statehoodSubjectRepresentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the statehoodSubjectRepresentDTO, or with status 404 (Not Found)
     */
    @Generated(value = "This is generate a sample java function StatehoodSubject")
    @GetMapping("/statehood-subject-represents/{id}")
    @Timed
    @Secured(AuthoritiesConstants.DECLARANT)
    public ResponseEntity<StatehoodSubjectRepresentWithNameDTO> getStatehoodSubjectRepresent(@PathVariable Long id) {
        log.debug("REST request to get StatehoodSubjectRepresent : {}", id);

        StatehoodSubjectRepresentWithNameDTO statehoodSubjectRepresentDTO =
            representService.findOne(id);

        return ResponseEntity.ok(statehoodSubjectRepresentDTO);
    }

    @Generated(value = "This is generate a sample java function StatehoodSubject")
    @GetMapping("/statehood-subject-represents/declarant/{id}")
    @Timed
    @Secured(AuthoritiesConstants.DECLARANT)
    @Deprecated
    public List<StatehoodSubjectRepresentMainInfoDTO> getAllActiveByDeclarantIdMainInfo(@PathVariable Long id) {
        log.debug("REST request to get all StatehoodSubjectRepresents by Declarant Id = {}", id);
        return representService.getAllActiveByDeclarantId(id);
    }


    @DeleteMapping("/statehood-subject-represents/{declarantId}/{subjectId}")
    @Timed
    public ResponseEntity<StatehoodSubjectRepresentDTO> deleteStatehoodSubjectRepresent(@PathVariable Long declarantId,
                                                                                        @PathVariable Long subjectId) {
        log.debug("REST request to delete Represent where declarantId: {} and subjectId: {}", declarantId, subjectId);

        StatehoodSubjectRepresentDTO deleted = representService.removeRepresent(declarantId, subjectId);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, deleted.getId().toString()))
            .body(deleted);
    }

    @PutMapping("/statehood-subject-represent/change-context")
    @Timed
    public ResponseEntity<Void> changeContextTo(@RequestParam(value = "statehoodSubjectId", required = false) Long statehoodSubjectId) {
        log.debug("REST to change StatehoodSubjectRepresent context to subject with id: {}", statehoodSubjectId);

        representService.changeContextTo(statehoodSubjectId);

        return ResponseEntity.ok().build();
    }
}
