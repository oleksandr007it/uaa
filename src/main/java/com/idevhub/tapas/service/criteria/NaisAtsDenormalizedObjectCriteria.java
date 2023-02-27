package com.idevhub.tapas.service.criteria;

import com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectStatus;
import com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectType;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import lombok.Data;

import java.io.Serializable;

@Data
public class NaisAtsDenormalizedObjectCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter parentId;
    private StringFilter searchName;
    private StringFilter regionName;
    private StringFilter districtName;
    private StringFilter localityName;
    private StringFilter localityObjectName;
    private NaisAtsObjectTypeFilter type;
    private NaisAtsObjectStatusFilter status;

    public static NaisAtsDenormalizedObjectCriteria of(NaisAtsDenormalizedObjectType type, NaisAtsDenormalizedObjectStatus status, String searchName, Long parentId) {
        var criteria = new NaisAtsDenormalizedObjectCriteria();
        criteria.setType((NaisAtsObjectTypeFilter) new NaisAtsObjectTypeFilter().setEquals(type));
        criteria.setStatus((NaisAtsObjectStatusFilter) new NaisAtsObjectStatusFilter().setEquals(status));
        criteria.setSearchName(new StringFilter().setContains(searchName));
        criteria.setParentId((LongFilter) new LongFilter().setEquals(parentId));
        return criteria;
    }

    public static NaisAtsDenormalizedObjectCriteria of(NaisAtsDenormalizedObjectType type, NaisAtsDenormalizedObjectStatus status, String searchName) {
        return of(type, status, searchName, null);
    }

    public static class NaisAtsObjectTypeFilter extends Filter<NaisAtsDenormalizedObjectType> {
    }

    public static class NaisAtsObjectStatusFilter extends Filter<NaisAtsDenormalizedObjectStatus> {
    }
}
