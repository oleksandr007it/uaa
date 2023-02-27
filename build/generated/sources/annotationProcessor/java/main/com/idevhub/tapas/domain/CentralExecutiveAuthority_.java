package com.idevhub.tapas.domain;

import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.enumeration.CentralExecutiveAuthorityStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CentralExecutiveAuthority.class)
public abstract class CentralExecutiveAuthority_ extends com.idevhub.tapas.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<CentralExecutiveAuthority, Instant> deletedAt;
	public static volatile SingularAttribute<CentralExecutiveAuthority, String> code;
	public static volatile SingularAttribute<CentralExecutiveAuthority, String> telNumber;
	public static volatile SingularAttribute<CentralExecutiveAuthority, Address> address;
	public static volatile SingularAttribute<CentralExecutiveAuthority, CentralExecutiveAuthorityStatus> centralExecutiveAuthorityStatus;
	public static volatile SingularAttribute<CentralExecutiveAuthority, Long> id;
	public static volatile SingularAttribute<CentralExecutiveAuthority, String> fullNameUkr;
	public static volatile SingularAttribute<CentralExecutiveAuthority, String> fullNameEng;
	public static volatile SingularAttribute<CentralExecutiveAuthority, String> email;
	public static volatile SingularAttribute<CentralExecutiveAuthority, String> deletedBy;

	public static final String DELETED_AT = "deletedAt";
	public static final String CODE = "code";
	public static final String TEL_NUMBER = "telNumber";
	public static final String ADDRESS = "address";
	public static final String CENTRAL_EXECUTIVE_AUTHORITY_STATUS = "centralExecutiveAuthorityStatus";
	public static final String ID = "id";
	public static final String FULL_NAME_UKR = "fullNameUkr";
	public static final String FULL_NAME_ENG = "fullNameEng";
	public static final String EMAIL = "email";
	public static final String DELETED_BY = "deletedBy";

}

