package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.address.NaisAtsDenormalizedObject;
import com.idevhub.tapas.repository.NaisAtsDenormalizedObjectRepository;
import com.idevhub.tapas.service.criteria.NaisAtsDenormalizedObjectCriteria;
import com.idevhub.tapas.service.criteria.NaisAtsDenormalizedObjectSpecificationHelper;
import com.idevhub.tapas.service.dto.NaisAtsObjectNameDTO;
import com.idevhub.tapas.service.mapper.NaisAtsDenormalizedObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectStatus.ACTIVE;
import static com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectType.LOCALITY;
import static com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectType.LOCALITY_OBJECT;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NaisAtsDictionaryService {

    private final NaisAtsDenormalizedObjectRepository atsDenormObjectRepository;
    private final NaisAtsDenormalizedObjectSpecificationHelper atsDenormObjectSpecificationHelper;
    private final NaisAtsDenormalizedObjectMapper atsDenormObjectMapper;

    public List<NaisAtsObjectNameDTO> searchActiveLocalitiesContainsName(String searchName) {
        log.debug("Searching active localities, contains name - {}", searchName);
        return atsDenormObjectMapper.toDto(getAllAtsObjectsByCriteria(NaisAtsDenormalizedObjectCriteria.of(LOCALITY, ACTIVE, searchName)));
    }

    public List<NaisAtsObjectNameDTO> searchActiveLocalityObjectsContainsName(Long parentId, String searchName) {
        log.debug("Searching active locality (id = {}) objects, contains name - {}", parentId, searchName);
        return atsDenormObjectMapper.toDto(getAllAtsObjectsByCriteria(NaisAtsDenormalizedObjectCriteria.of(LOCALITY_OBJECT, ACTIVE, searchName, parentId)));
    }

    public List<NaisAtsDenormalizedObject> getAllAtsObjectsByCriteria(NaisAtsDenormalizedObjectCriteria criteria) {
        log.debug("Trying to get all ats object by criteria: {}", criteria);
        var specification = atsDenormObjectSpecificationHelper.createSpecification(criteria);
        return atsDenormObjectRepository.findAll(specification);
    }

    public NaisAtsDenormalizedObject getById(Long id) {
        log.debug("Trying to get ats object by id = {}", id);
        return atsDenormObjectRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(NaisAtsDenormalizedObject.class, id));
    }
}
