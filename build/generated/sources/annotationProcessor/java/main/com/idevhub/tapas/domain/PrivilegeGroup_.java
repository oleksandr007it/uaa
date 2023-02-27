package com.idevhub.tapas.domain;

import com.idevhub.tapas.domain.enumeration.ActiveStatus;
import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PrivilegeGroup.class)
public abstract class PrivilegeGroup_ extends com.idevhub.tapas.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<PrivilegeGroup, CentralExecutiveAuthority> centralExecutiveAuthority;
	public static volatile SetAttribute<PrivilegeGroup, Privilege> privileges;
	public static volatile SingularAttribute<PrivilegeGroup, Instant> deletedAt;
	public static volatile SingularAttribute<PrivilegeGroup, String> code;
	public static volatile SingularAttribute<PrivilegeGroup, PrivilegeType> privilegeGroupType;
	public static volatile SingularAttribute<PrivilegeGroup, StatehoodSubject> statehoodSubject;
	public static volatile SingularAttribute<PrivilegeGroup, Boolean> global;
	public static volatile SingularAttribute<PrivilegeGroup, String> fullNameUkr;
	public static volatile SingularAttribute<PrivilegeGroup, String> fullNameEng;
	public static volatile SingularAttribute<PrivilegeGroup, String> nameInDirectoryService;
	public static volatile SingularAttribute<PrivilegeGroup, String> deletedBy;
	public static volatile SingularAttribute<PrivilegeGroup, ActiveStatus> status;

	public static final String CENTRAL_EXECUTIVE_AUTHORITY = "centralExecutiveAuthority";
	public static final String PRIVILEGES = "privileges";
	public static final String DELETED_AT = "deletedAt";
	public static final String CODE = "code";
	public static final String PRIVILEGE_GROUP_TYPE = "privilegeGroupType";
	public static final String STATEHOOD_SUBJECT = "statehoodSubject";
	public static final String GLOBAL = "global";
	public static final String FULL_NAME_UKR = "fullNameUkr";
	public static final String FULL_NAME_ENG = "fullNameEng";
	public static final String NAME_IN_DIRECTORY_SERVICE = "nameInDirectoryService";
	public static final String DELETED_BY = "deletedBy";
	public static final String STATUS = "status";

}

