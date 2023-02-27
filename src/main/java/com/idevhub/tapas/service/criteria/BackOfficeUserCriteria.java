package com.idevhub.tapas.service.criteria;

import io.github.jhipster.service.filter.StringFilter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BackOfficeUserCriteria {
    private StringFilter firstName;
    private StringFilter lastName;
    private StringFilter fullName;
    private StringFilter org;
    private StringFilter orgCode;
    private StringFilter orgUnit;
    private StringFilter position;
    private StringFilter edrpouCode;
    private StringFilter status;

}
