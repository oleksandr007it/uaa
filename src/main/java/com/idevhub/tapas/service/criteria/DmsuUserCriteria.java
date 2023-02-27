package com.idevhub.tapas.service.criteria;

import io.github.jhipster.service.filter.StringFilter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DmsuUserCriteria {
    private StringFilter lastName;
    private StringFilter org;
}
