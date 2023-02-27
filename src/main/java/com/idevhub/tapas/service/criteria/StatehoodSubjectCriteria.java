package com.idevhub.tapas.service.criteria;

import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import io.github.jhipster.service.filter.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class StatehoodSubjectCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    private StringFilter subjectStatus;
    private AccountDetailsStatusFilter accountDetailsStatus;
    private InstantFilter createdAt;
    private InstantFilter updatedAt;
    private InstantFilter deletedAt;
    private StringFilter subjectCode;
    private StringFilter subjectName;
    private StringFilter subjectShortName;
    private StringFilter headFullName;
    private StringFilter telNumber;
    private StringFilter email;
    private BooleanFilter isEmailApproved;
    private StringFilter eori;
    private BooleanFilter isActualSameAsLegalAddress;
    private StringFilter createdBy;
    private StringFilter updatedBy;
    private StringFilter deletedBy;
    private LongFilter kopfgId;
    private IntegerFilter kopfgCode;
    private StringFilter legalAddressId;
    private StringFilter actualAddressId;

    public static class AccountDetailsStatusFilter extends Filter<AccountDetailsStatus> {
    }
}
