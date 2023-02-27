package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.service.KopfgService;
import com.idevhub.tapas.service.dto.KopfgDTO;
import com.idevhub.tapas.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Kopfg.
 */
@RestController
@RequestMapping("/api")
public class KopfgResource {

    private static final String ENTITY_NAME = "kopfg";
    private final Logger log = LoggerFactory.getLogger(KopfgResource.class);
    private final KopfgService kopfgService;
    @Value("${jhipster.clientApp.name}")
    private String APPLICATION_NAME;

    public KopfgResource(KopfgService kopfgService) {
        this.kopfgService = kopfgService;
    }

    /**
     * POST  /kopfgs : Create a new kopfg.
     *
     * @param kopfgDTO the kopfgDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new kopfgDTO, or with status 400 (Bad Request) if the kopfg has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/kopfgs")
    @Timed
    public ResponseEntity<KopfgDTO> createKopfg(@Valid @RequestBody KopfgDTO kopfgDTO) throws URISyntaxException {
        log.debug("REST request to save Kopfg : {}", kopfgDTO);
        if (kopfgDTO.getId() != null) {
            throw new BadRequestAlertException("A new kopfg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KopfgDTO result = kopfgService.save(kopfgDTO);
        return ResponseEntity.created(new URI("/api/kopfgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(APPLICATION_NAME, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /kopfgs : Updates an existing Kopfg.
     *
     * @param kopfgDTO the kopfgDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated kopfgDTO,
     * or with status 400 (Bad Request) if the kopfgDTO is not valid,
     * or with status 500 (Internal Server Error) if the kopfgDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/kopfgs")
    @Timed
    public ResponseEntity<KopfgDTO> updateKopfg(@Valid @RequestBody KopfgDTO kopfgDTO) throws URISyntaxException {
        log.debug("REST request to update Kopfg : {}", kopfgDTO);
        if (kopfgDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KopfgDTO result = kopfgService.save(kopfgDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(APPLICATION_NAME, false, ENTITY_NAME, kopfgDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /kopfgs : get all the KopfgS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of kopfgS in body
     */
    @GetMapping("/kopfgs")
    @Timed
    public List<KopfgDTO> getAllKopfgS() {
        log.debug("REST request to get all KopfgS");
        return kopfgService.findAll();
    }

    /**
     * GET  /kopfgs/:id : get the "id" kopfg.
     *
     * @param id the id of the kopfgDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the kopfgDTO, or with status 404 (Not Found)
     */
    @GetMapping("/kopfgs/{id}")
    @Timed
    public ResponseEntity<KopfgDTO> getKopfg(@PathVariable Long id) {
        log.debug("REST request to get kopfg : {}", id);
        Optional<KopfgDTO> kopfgDTO = kopfgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kopfgDTO);
    }

    /**
     * DELETE  /kopfgs/:id : delete the "id" kopfg.
     *
     * @param id the id of the kopfgDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/kopfgs/{id}")
    @Timed
    public ResponseEntity<Void> deleteKopfg(@PathVariable Long id) {
        log.debug("REST request to delete kopfg : {}", id);
        kopfgService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(APPLICATION_NAME, false, ENTITY_NAME, id.toString())).build();
    }
}
