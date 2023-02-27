package com.idevhub.tapas.domain;

import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.enumeration.PositionType;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.idevhub.tapas.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<User, String> lastName;
	public static volatile SingularAttribute<User, String> pasportSn;
	public static volatile SingularAttribute<User, PositionType> positionType;
	public static volatile SingularAttribute<User, Instant> resetDate;
	public static volatile SingularAttribute<User, String> login;
	public static volatile SingularAttribute<User, String> resetKey;
	public static volatile SingularAttribute<User, String> deletedBy;
	public static volatile SingularAttribute<User, Address> legalAddress;
	public static volatile SingularAttribute<User, CentralExecutiveAuthority> centralExecutiveAuthority;
	public static volatile SingularAttribute<User, String> propertyStatus;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> rnokpp;
	public static volatile SingularAttribute<User, String> pasportIssuedBy;
	public static volatile SetAttribute<User, PrivilegeGroup> privilegeGroups;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Boolean> emailApprove;
	public static volatile SingularAttribute<User, String> org;
	public static volatile SingularAttribute<User, String> orgUnit;
	public static volatile SingularAttribute<User, String> fullName;
	public static volatile SingularAttribute<User, String> activationKey;
	public static volatile SetAttribute<User, Authority> authorities;
	public static volatile SingularAttribute<User, String> firstName;
	public static volatile SingularAttribute<User, String> phoneNumber;
	public static volatile SingularAttribute<User, Instant> pasportDate;
	public static volatile SingularAttribute<User, String> langKey;
	public static volatile SingularAttribute<User, Instant> deletedDate;
	public static volatile SingularAttribute<User, CEADepartment> ceaDepartment;
	public static volatile SingularAttribute<User, String> middleName;
	public static volatile SingularAttribute<User, String> userType;
	public static volatile SingularAttribute<User, String> position;
	public static volatile SingularAttribute<User, String> status;
	public static volatile SingularAttribute<User, String> edrpouCode;
	public static volatile SingularAttribute<User, Boolean> activated;

	public static final String LAST_NAME = "lastName";
	public static final String PASPORT_SN = "pasportSn";
	public static final String POSITION_TYPE = "positionType";
	public static final String RESET_DATE = "resetDate";
	public static final String LOGIN = "login";
	public static final String RESET_KEY = "resetKey";
	public static final String DELETED_BY = "deletedBy";
	public static final String LEGAL_ADDRESS = "legalAddress";
	public static final String CENTRAL_EXECUTIVE_AUTHORITY = "centralExecutiveAuthority";
	public static final String PROPERTY_STATUS = "propertyStatus";
	public static final String PASSWORD = "password";
	public static final String RNOKPP = "rnokpp";
	public static final String PASPORT_ISSUED_BY = "pasportIssuedBy";
	public static final String PRIVILEGE_GROUPS = "privilegeGroups";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String EMAIL_APPROVE = "emailApprove";
	public static final String ORG = "org";
	public static final String ORG_UNIT = "orgUnit";
	public static final String FULL_NAME = "fullName";
	public static final String ACTIVATION_KEY = "activationKey";
	public static final String AUTHORITIES = "authorities";
	public static final String FIRST_NAME = "firstName";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String PASPORT_DATE = "pasportDate";
	public static final String LANG_KEY = "langKey";
	public static final String DELETED_DATE = "deletedDate";
	public static final String CEA_DEPARTMENT = "ceaDepartment";
	public static final String MIDDLE_NAME = "middleName";
	public static final String USER_TYPE = "userType";
	public static final String POSITION = "position";
	public static final String STATUS = "status";
	public static final String EDRPOU_CODE = "edrpouCode";
	public static final String ACTIVATED = "activated";

}

