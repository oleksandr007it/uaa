package com.idevhub.tapas.service.criteria;

import com.idevhub.tapas.domain.CEADepartment_;
import com.idevhub.tapas.domain.CentralExecutiveAuthority_;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.User_;
import io.github.jhipster.service.QueryService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CEAEmployeeSpecificationHelper extends QueryService<User> {
    public Specification<User> createSpecification(CEAEmployeeCriteria c) {
        Specification<User> specification = Specification.where(null);
        if (c != null) {
            if (c.getStatus() != null)
                specification = specification.and(buildStringSpecification(c.getStatus(), User_.status));
            if (c.getFullName() != null)
                specification = specification.and(buildStringSpecification(c.getFullName(), User_.fullName));
            if (c.getPosition() != null)
                specification = specification.and(buildStringSpecification(c.getPosition(), User_.position));
            if (c.getDepartment() != null)
                specification = specification.and(buildSpecification(c.getDepartment(), root -> root.join(User_.ceaDepartment).get(CEADepartment_.fullNameUkr)));
            if (c.getCeaCode() != null)
                specification = specification.and(buildReferringEntitySpecification(c.getCeaCode(), User_.centralExecutiveAuthority, CentralExecutiveAuthority_.code));
            if (c.getPositionType() != null)
                specification = specification.and(buildSpecification(c.getPositionType(), User_.positionType));
        }
        return specification;
    }

}
