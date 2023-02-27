package com.idevhub.tapas.service.criteria;

import com.idevhub.tapas.domain.address.NaisAtsDenormalizedObject;
import com.idevhub.tapas.domain.address.NaisAtsDenormalizedObject_;
import io.github.jhipster.service.QueryService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class NaisAtsDenormalizedObjectSpecificationHelper extends QueryService<NaisAtsDenormalizedObject> {

    public Specification<NaisAtsDenormalizedObject> createSpecification(NaisAtsDenormalizedObjectCriteria criteria) {

        Specification<NaisAtsDenormalizedObject> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getType() != null)
                specification = specification.and(buildSpecification(criteria.getType(), NaisAtsDenormalizedObject_.type));
            if (criteria.getStatus() != null)
                specification = specification.and(buildSpecification(criteria.getStatus(), NaisAtsDenormalizedObject_.status));
            if (criteria.getSearchName() != null)
                specification = specification.and(buildStringSpecification(criteria.getSearchName(), NaisAtsDenormalizedObject_.searchName));
           if (criteria.getRegionName() != null)
                specification = specification.and(buildStringSpecification(criteria.getRegionName(), NaisAtsDenormalizedObject_.regionName));
           if (criteria.getDistrictName() != null)
                specification = specification.and(buildStringSpecification(criteria.getDistrictName(), NaisAtsDenormalizedObject_.districtName));
           if (criteria.getLocalityName() != null)
                specification = specification.and(buildStringSpecification(criteria.getLocalityName(), NaisAtsDenormalizedObject_.localityName));
           if (criteria.getLocalityObjectName() != null)
                specification = specification.and(buildStringSpecification(criteria.getLocalityObjectName(), NaisAtsDenormalizedObject_.localityObjectName));
            if (criteria.getParentId() != null)
                specification = specification.and(buildReferringEntitySpecification(criteria.getParentId(), NaisAtsDenormalizedObject_.parent, NaisAtsDenormalizedObject_.id));
        }
        return specification;
    }
}
