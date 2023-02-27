package com.idevhub.tapas.domain;

import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Privilege.class)
public abstract class Privilege_ {

	public static volatile SingularAttribute<Privilege, String> code;
	public static volatile SingularAttribute<Privilege, String> fullNameUkr;
	public static volatile SingularAttribute<Privilege, String> fullNameEng;
	public static volatile SingularAttribute<Privilege, PrivilegeType> privilegeType;
	public static volatile SingularAttribute<Privilege, PrivilegeCategory> privilegeCategory;

	public static final String CODE = "code";
	public static final String FULL_NAME_UKR = "fullNameUkr";
	public static final String FULL_NAME_ENG = "fullNameEng";
	public static final String PRIVILEGE_TYPE = "privilegeType";
	public static final String PRIVILEGE_CATEGORY = "privilegeCategory";

}

