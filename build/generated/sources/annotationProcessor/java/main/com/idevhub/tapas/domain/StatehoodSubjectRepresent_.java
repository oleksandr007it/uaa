package com.idevhub.tapas.domain;

import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StatehoodSubjectRepresent.class)
public abstract class StatehoodSubjectRepresent_ {

	public static volatile SingularAttribute<StatehoodSubjectRepresent, String> updatedBy;
	public static volatile SingularAttribute<StatehoodSubjectRepresent, StatehoodSubjectRepresentType> subjectRepresentType;
	public static volatile SingularAttribute<StatehoodSubjectRepresent, Boolean> currentContext;
	public static volatile SingularAttribute<StatehoodSubjectRepresent, StatehoodSubjectRepresentStatus> subjectRepresentStatus;
	public static volatile SingularAttribute<StatehoodSubjectRepresent, String> approveSignBase64;
	public static volatile SingularAttribute<StatehoodSubjectRepresent, StatehoodSubject> statehoodSubject;
	public static volatile SingularAttribute<StatehoodSubjectRepresent, String> deletedBy;
	public static volatile SingularAttribute<StatehoodSubjectRepresent, Instant> createdAt;
	public static volatile SingularAttribute<StatehoodSubjectRepresent, Instant> deletedAt;
	public static volatile SingularAttribute<StatehoodSubjectRepresent, String> createdBy;
	public static volatile SetAttribute<StatehoodSubjectRepresent, PrivilegeGroup> privilegeGroups;
	public static volatile SingularAttribute<StatehoodSubjectRepresent, Long> id;
	public static volatile SingularAttribute<StatehoodSubjectRepresent, User> declarant;
	public static volatile SingularAttribute<StatehoodSubjectRepresent, Instant> updatedAt;

	public static final String UPDATED_BY = "updatedBy";
	public static final String SUBJECT_REPRESENT_TYPE = "subjectRepresentType";
	public static final String CURRENT_CONTEXT = "currentContext";
	public static final String SUBJECT_REPRESENT_STATUS = "subjectRepresentStatus";
	public static final String APPROVE_SIGN_BASE64 = "approveSignBase64";
	public static final String STATEHOOD_SUBJECT = "statehoodSubject";
	public static final String DELETED_BY = "deletedBy";
	public static final String CREATED_AT = "createdAt";
	public static final String DELETED_AT = "deletedAt";
	public static final String CREATED_BY = "createdBy";
	public static final String PRIVILEGE_GROUPS = "privilegeGroups";
	public static final String ID = "id";
	public static final String DECLARANT = "declarant";
	public static final String UPDATED_AT = "updatedAt";

}

