package com.idevhub.tapas.service.criteria;

import com.idevhub.tapas.domain.address.Address_;
import com.idevhub.tapas.domain.Kopfg_;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.StatehoodSubject_;
import io.github.jhipster.service.QueryService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class StatehoodSubjectSpecificationHelper extends QueryService<StatehoodSubject> {
    public Specification<StatehoodSubject> createSpecification(StatehoodSubjectCriteria c) {
        Specification<StatehoodSubject> specification = Specification.where(null);
        if (c != null) {
            if (c.getSubjectStatus() != null)
                specification = specification.and(buildStringSpecification(c.getSubjectStatus(), StatehoodSubject_.subjectStatus));
            if (c.getAccountDetailsStatus() != null)
                specification = specification.and(buildSpecification(c.getAccountDetailsStatus(), StatehoodSubject_.accountDetailsStatus));
            if (c.getCreatedAt() != null)
                specification = specification.and(buildSpecification(c.getCreatedAt(), StatehoodSubject_.createdAt));
            if (c.getUpdatedAt() != null)
                specification = specification.and(buildSpecification(c.getUpdatedAt(), StatehoodSubject_.updatedAt));
            if (c.getDeletedAt() != null)
                specification = specification.and(buildSpecification(c.getDeletedAt(), StatehoodSubject_.deletedAt));
            if (c.getSubjectCode() != null)
                specification = specification.and(buildStringSpecification(c.getSubjectCode(), StatehoodSubject_.subjectCode));
            if (c.getSubjectName() != null)
                specification = specification.and(buildStringSpecification(c.getSubjectName(), StatehoodSubject_.subjectName));
            if (c.getSubjectShortName() != null)
                specification = specification.and(buildStringSpecification(c.getSubjectShortName(), StatehoodSubject_.subjectShortName));
            if (c.getHeadFullName() != null)
                specification = specification.and(buildStringSpecification(c.getHeadFullName(), StatehoodSubject_.headFullName));
            if (c.getTelNumber() != null)
                specification = specification.and(buildStringSpecification(c.getTelNumber(), StatehoodSubject_.telNumber));
            if (c.getEmail() != null)
                specification = specification.and(buildStringSpecification(c.getEmail(), StatehoodSubject_.email));
            if (c.getIsEmailApproved() != null)
                specification = specification.and(buildSpecification(c.getIsEmailApproved(), StatehoodSubject_.isEmailApproved));
            if (c.getEori() != null)
                specification = specification.and(buildStringSpecification(c.getEori(), StatehoodSubject_.eori));
            if (c.getIsActualSameAsLegalAddress() != null)
                specification = specification.and(buildSpecification(c.getIsActualSameAsLegalAddress(), StatehoodSubject_.isActualSameAsLegalAddress));
            if (c.getCreatedBy() != null)
                specification = specification.and(buildStringSpecification(c.getCreatedBy(), StatehoodSubject_.createdBy));
            if (c.getUpdatedBy() != null)
                specification = specification.and(buildStringSpecification(c.getUpdatedBy(), StatehoodSubject_.updatedBy));
            if (c.getDeletedBy() != null)
                specification = specification.and(buildStringSpecification(c.getDeletedBy(), StatehoodSubject_.deletedBy));
            if (c.getKopfgId() != null)
                specification = specification.and(buildReferringEntitySpecification(c.getKopfgId(), StatehoodSubject_.kopfg, Kopfg_.id));
            if (c.getKopfgCode() != null)
                specification = specification.and(buildReferringEntitySpecification(c.getKopfgCode(), StatehoodSubject_.kopfg, Kopfg_.code));
            if (c.getLegalAddressId() != null)
                specification = specification.and(buildReferringEntitySpecification(c.getLegalAddressId(), StatehoodSubject_.legalAddress, Address_.id));
            if (c.getActualAddressId() != null)
                specification = specification.and(buildReferringEntitySpecification(c.getActualAddressId(), StatehoodSubject_.actualAddress, Address_.id));

        }
        return specification;
    }
}
