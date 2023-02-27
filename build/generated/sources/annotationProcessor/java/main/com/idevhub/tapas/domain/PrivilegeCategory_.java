package com.idevhub.tapas.domain;

import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PrivilegeCategory.class)
public abstract class PrivilegeCategory_ {

	public static volatile SetAttribute<PrivilegeCategory, Privilege> privileges;
	public static volatile SingularAttribute<PrivilegeCategory, String> code;
	public static volatile SingularAttribute<PrivilegeCategory, String> customsHouse;
	public static volatile SingularAttribute<PrivilegeCategory, Boolean> isGlobal;
	public static volatile SingularAttribute<PrivilegeCategory, String> fullNameUkr;
	public static volatile SingularAttribute<PrivilegeCategory, String> fullNameEng;
	public static volatile SingularAttribute<PrivilegeCategory, PrivilegeType> privilegeCategoryType;

	public static final String PRIVILEGES = "privileges";
	public static final String CODE = "code";
	public static final String CUSTOMS_HOUSE = "customsHouse";
	public static final String IS_GLOBAL = "isGlobal";
	public static final String FULL_NAME_UKR = "fullNameUkr";
	public static final String FULL_NAME_ENG = "fullNameEng";
	public static final String PRIVILEGE_CATEGORY_TYPE = "privilegeCategoryType";

}

