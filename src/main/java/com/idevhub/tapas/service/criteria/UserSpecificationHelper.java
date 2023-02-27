package com.idevhub.tapas.service.criteria;

import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.User_;
import io.github.jhipster.service.QueryService;
import io.github.jhipster.service.filter.StringFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class UserSpecificationHelper extends QueryService<User> {
    public Specification<User> createRepresentSpecification(@NotNull String status, @NotNull String rnokpp, @NotNull String firstName, @NotNull String lastName, String middleName) {
        Specification<User> specification = Specification.where(null);

        StringFilter statusFilter = new StringFilter();
        statusFilter.setEquals(status);

        StringFilter rnokppFilter = new StringFilter();
        rnokppFilter.setEquals(rnokpp);

        StringFilter firstNameFilter = new StringFilter();
        firstNameFilter.setEquals(firstName);

        StringFilter lastNameFilter = new StringFilter();
        lastNameFilter.setEquals(lastName);

        specification = specification.and(buildStringSpecification(statusFilter, User_.status));
        specification = specification.and(buildStringSpecification(rnokppFilter, User_.rnokpp));
        specification = specification.and(buildStringSpecification(firstNameFilter, User_.firstName));
        specification = specification.and(buildStringSpecification(lastNameFilter, User_.lastName));

        if (middleName != null && !"".equals(middleName)) {
            StringFilter middleNameFilter = new StringFilter();
            middleNameFilter.setEquals(middleName);

            specification = specification.and(buildStringSpecification(middleNameFilter, User_.middleName));
        }

        return specification;
    }

}
