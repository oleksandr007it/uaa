package com.idevhub.tapas.service.criteria;

import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent_;
import com.idevhub.tapas.domain.StatehoodSubject_;
import com.idevhub.tapas.domain.User_;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;

@Service
@Slf4j
public class RepresentSpecificationHelper extends QueryService<StatehoodSubjectRepresent> {


    public Specification<StatehoodSubjectRepresent> createSpecification(RepresentCriteria c) {
        Specification<StatehoodSubjectRepresent> specification = Specification.where(null);
        if (c != null) {

            if (c.getSubjectId() != null)
                specification = specification.and(buildSpecification(c.getSubjectId(),
                    root -> root.get(StatehoodSubjectRepresent_.statehoodSubject).get(StatehoodSubject_.id)));
            if (c.getType() != null)
                specification = specification.and(buildSpecification(c.getType(), StatehoodSubjectRepresent_.subjectRepresentType));
            if (c.getStatus() != null)
                specification = specification.and(buildSpecification(c.getStatus(), StatehoodSubjectRepresent_.subjectRepresentStatus));
            if (c.getFullName() != null)
                specification = specification.and(buildSpecification(c.getFullName(),
                    root -> root.join(StatehoodSubjectRepresent_.declarant, JoinType.INNER).get(User_.fullName)));
        }

        var statusFilter = new Filter<StatehoodSubjectRepresentStatus>();
        statusFilter.setNotEquals(StatehoodSubjectRepresentStatus.DELETED);
        specification = specification.and(buildSpecification(statusFilter, StatehoodSubjectRepresent_.subjectRepresentStatus));


        return specification;
    }
}
