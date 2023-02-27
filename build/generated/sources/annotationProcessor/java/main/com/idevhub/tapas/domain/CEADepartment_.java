package com.idevhub.tapas.domain;

import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.enumeration.CEADepartmentStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CEADepartment.class)
public abstract class CEADepartment_ extends com.idevhub.tapas.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<CEADepartment, CentralExecutiveAuthority> centralExecutiveAuthority;
	public static volatile SingularAttribute<CEADepartment, Instant> deletedAt;
	public static volatile SingularAttribute<CEADepartment, String> telNumber;
	public static volatile SingularAttribute<CEADepartment, Address> address;
	public static volatile SingularAttribute<CEADepartment, CEADepartmentStatus> ceaDepartmentStatus;
	public static volatile SingularAttribute<CEADepartment, String> description;
	public static volatile SingularAttribute<CEADepartment, Long> id;
	public static volatile SingularAttribute<CEADepartment, String> fullNameUkr;
	public static volatile SingularAttribute<CEADepartment, String> fullNameEng;
	public static volatile SingularAttribute<CEADepartment, String> deletedBy;
	public static volatile SingularAttribute<CEADepartment, String> email;

	public static final String CENTRAL_EXECUTIVE_AUTHORITY = "centralExecutiveAuthority";
	public static final String DELETED_AT = "deletedAt";
	public static final String TEL_NUMBER = "telNumber";
	public static final String ADDRESS = "address";
	public static final String CEA_DEPARTMENT_STATUS = "ceaDepartmentStatus";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String FULL_NAME_UKR = "fullNameUkr";
	public static final String FULL_NAME_ENG = "fullNameEng";
	public static final String DELETED_BY = "deletedBy";
	public static final String EMAIL = "email";

}

