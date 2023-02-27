package com.idevhub.tapas.service.criteria;

import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.User_;
import io.github.jhipster.service.QueryService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class DmsuUserHelper extends QueryService<User> {
    public Specification<User> createSpecification(DmsuUserCriteria c) {
        Specification<User> specification = Specification.where(null);

        if (c != null) {
            if (c.getLastName() != null)
                specification = specification.and(buildStringSpecification(c.getLastName(), User_.lastName));
            if (c.getOrg() != null)
                specification = specification.and(buildStringSpecification(c.getOrg(), User_.org));
        }

        return specification;
    }

}
