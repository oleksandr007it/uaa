package com.idevhub.tapas.service.criteria;

import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class RepresentCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private LongFilter subjectId;
    private StringFilter fullName;
    private TypeFilter type;
    private StatusFilter status;

    public static class TypeFilter extends Filter<StatehoodSubjectRepresentType> {
    }

    public static class StatusFilter extends Filter<StatehoodSubjectRepresentStatus> {
    }
}
