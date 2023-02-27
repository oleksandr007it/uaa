package com.idevhub.tapas.service.criteria;

import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.User_;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.StringFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.idevhub.tapas.domain.constants.UserType.BACK_OFFICE;

@Service
public class BackOfficeUserHelper extends QueryService<User> {
    public Specification<User> createSpecification(BackOfficeUserCriteria c) {

        StringFilter userTypeFilter = new StringFilter();
        userTypeFilter.setEquals(BACK_OFFICE);

        Specification<User> specification = Specification.where(buildStringSpecification(userTypeFilter, User_.userType));

        if (c != null) {
            if (c.getFirstName() != null)
                specification = specification.and(buildStringSpecification(c.getFirstName(), User_.firstName));
            if (c.getLastName() != null)
                specification = specification.and(buildStringSpecification(c.getLastName(), User_.lastName));
            if (c.getFullName() != null)
                specification = specification.and(buildStringSpecification(c.getFullName(), User_.fullName));
            if (c.getOrg() != null)
                specification = specification.and(buildStringSpecification(c.getOrg(), User_.org));
            if (c.getOrgCode() != null)
                specification = specification.and(buildStringSpecification(c.getOrgCode(), User_.orgCode));
            if (c.getOrgUnit() != null)
                specification = specification.and(buildStringSpecification(c.getOrgUnit(), User_.orgUnit));
            if (c.getPosition() != null)
                specification = specification.and(buildStringSpecification(c.getPosition(), User_.position));
            if (c.getEdrpouCode() != null)
                specification = specification.and(buildStringSpecification(c.getEdrpouCode(), User_.edrpouCode));
            if (c.getStatus() != null)
                specification = specification.and(buildStringSpecification(c.getStatus(), User_.status));
        }
        return specification;
    }

}
