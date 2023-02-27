package com.idevhub.tapas.service.criteria;

import com.idevhub.tapas.domain.enumeration.PositionType;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.StringFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CEAEmployeeCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    private StringFilter status;
    private StringFilter position;
    private StringFilter fullName;
    private StringFilter ceaCode;
    private StringFilter department;
    private PositionTypeFilter positionType;

    public static class PositionTypeFilter extends Filter<PositionType> {
    }
}
