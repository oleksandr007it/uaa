package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.domain.address.NaisAtsDenormalizedObject;
import com.idevhub.tapas.service.NaisAtsDictionaryService;
import com.idevhub.tapas.service.NaisAtsObjectDenormalizationService;
import com.idevhub.tapas.service.criteria.NaisAtsDenormalizedObjectCriteria;
import com.idevhub.tapas.service.dto.NaisAtsObjectNameDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NaisAtsDictionaryResource {

    private final NaisAtsDictionaryService atsDictionaryService;
    private final NaisAtsObjectDenormalizationService atsObjectDenormService;

    @GetMapping("/nais/ats/localities")
    public ResponseEntity<List<NaisAtsObjectNameDTO>> searchLocalitiesByName(@RequestParam @Size(min = 2) String searchName) {
        log.debug("REST request to search ats localities by name like: {}", searchName);
        return ResponseEntity.ok().body(atsDictionaryService.searchActiveLocalitiesContainsName(searchName));
    }

    @GetMapping("/nais/ats/localities/{id}/objects")
    public ResponseEntity<List<NaisAtsObjectNameDTO>> searchLocalityObjectsByName(@PathVariable @NotNull Long id,
                                                                                  @RequestParam @Size(min = 2) String searchName) {
        log.debug("REST request to search ats locality (id = {}) objects by name like: {}", id, searchName);
        return ResponseEntity.ok().body(atsDictionaryService.searchActiveLocalityObjectsContainsName(id, searchName));
    }

    @GetMapping("/nais/ats/objects/{id}")
    public ResponseEntity<NaisAtsDenormalizedObject> getAtsObjectById(@PathVariable Long id) {
        log.debug("REST request to get ats object by id={}", id);
        return ResponseEntity.ok().body(atsDictionaryService.getById(id));
    }

    @GetMapping("/nais/ats/objects")
    public ResponseEntity<List<NaisAtsDenormalizedObject>> getAtsObjectsByCriteria(NaisAtsDenormalizedObjectCriteria criteria) {
        log.debug("REST request to get ats objects by criteria={}", criteria);
        return ResponseEntity.ok().body(atsDictionaryService.getAllAtsObjectsByCriteria(criteria));
    }

    @PutMapping("/nais/ats/objects/denormalize")
    public ResponseEntity<Void> denormalizeAtsDictionary() {
        log.debug("REST request to denormalize ats dictionary");
        atsObjectDenormService.denormalizeAtsObjectDictionary();
        return ResponseEntity.ok().build();
    }
}
