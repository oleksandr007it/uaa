package com.idevhub.tapas.domain;

import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StatehoodSubject.class)
public abstract class StatehoodSubject_ {

	public static volatile SingularAttribute<StatehoodSubject, Address> actualAddress;
	public static volatile SingularAttribute<StatehoodSubject, String> updatedBy;
	public static volatile SingularAttribute<StatehoodSubject, String> subjectStatus;
	public static volatile SingularAttribute<StatehoodSubject, AccountDetailsStatus> accountDetailsStatus;
	public static volatile SingularAttribute<StatehoodSubject, String> subjectShortName;
	public static volatile SingularAttribute<StatehoodSubject, Boolean> isActualSameAsLegalAddress;
	public static volatile SingularAttribute<StatehoodSubject, String> eori;
	public static volatile SingularAttribute<StatehoodSubject, String> deletedBy;
	public static volatile SingularAttribute<StatehoodSubject, Address> legalAddress;
	public static volatile SingularAttribute<StatehoodSubject, Instant> createdAt;
	public static volatile SingularAttribute<StatehoodSubject, Instant> deletedAt;
	public static volatile SingularAttribute<StatehoodSubject, String> telNumber;
	public static volatile SingularAttribute<StatehoodSubject, Boolean> isEmailApproved;
	public static volatile SingularAttribute<StatehoodSubject, String> createdBy;
	public static volatile SingularAttribute<StatehoodSubject, String> headFullName;
	public static volatile SingularAttribute<StatehoodSubject, Kopfg> kopfg;
	public static volatile SingularAttribute<StatehoodSubject, Long> id;
	public static volatile SingularAttribute<StatehoodSubject, String> subjectCode;
	public static volatile SingularAttribute<StatehoodSubject, String> email;
	public static volatile SingularAttribute<StatehoodSubject, String> subjectName;
	public static volatile SingularAttribute<StatehoodSubject, Instant> updatedAt;

	public static final String ACTUAL_ADDRESS = "actualAddress";
	public static final String UPDATED_BY = "updatedBy";
	public static final String SUBJECT_STATUS = "subjectStatus";
	public static final String ACCOUNT_DETAILS_STATUS = "accountDetailsStatus";
	public static final String SUBJECT_SHORT_NAME = "subjectShortName";
	public static final String IS_ACTUAL_SAME_AS_LEGAL_ADDRESS = "isActualSameAsLegalAddress";
	public static final String EORI = "eori";
	public static final String DELETED_BY = "deletedBy";
	public static final String LEGAL_ADDRESS = "legalAddress";
	public static final String CREATED_AT = "createdAt";
	public static final String DELETED_AT = "deletedAt";
	public static final String TEL_NUMBER = "telNumber";
	public static final String IS_EMAIL_APPROVED = "isEmailApproved";
	public static final String CREATED_BY = "createdBy";
	public static final String HEAD_FULL_NAME = "headFullName";
	public static final String KOPFG = "kopfg";
	public static final String ID = "id";
	public static final String SUBJECT_CODE = "subjectCode";
	public static final String EMAIL = "email";
	public static final String SUBJECT_NAME = "subjectName";
	public static final String UPDATED_AT = "updatedAt";

}

