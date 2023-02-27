package com.idevhub.tapas.domain.address;

import com.idevhub.tapas.domain.address.enumeration.PostalCodeAvailability;
import com.idevhub.tapas.domain.address.enumeration.PostalCodeParamsStatus;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PostalCodeParams.class)
public abstract class PostalCodeParams_ extends com.idevhub.tapas.domain.AbstractAuditingEntity_ {

	public static volatile SingularAttribute<PostalCodeParams, String> promptEn;
	public static volatile SingularAttribute<PostalCodeParams, String> prefix;
	public static volatile SingularAttribute<PostalCodeParams, Integer> minLength;
	public static volatile SingularAttribute<PostalCodeParams, String> format;
	public static volatile SingularAttribute<PostalCodeParams, PostalCodeAvailability> availability;
	public static volatile SingularAttribute<PostalCodeParams, String> deletedBy;
	public static volatile SingularAttribute<PostalCodeParams, String> infoUrlEn;
	public static volatile SingularAttribute<PostalCodeParams, String> constValue;
	public static volatile SingularAttribute<PostalCodeParams, Instant> deletedDate;
	public static volatile SingularAttribute<PostalCodeParams, String> countryCode;
	public static volatile SingularAttribute<PostalCodeParams, String> infoUrlUa;
	public static volatile SingularAttribute<PostalCodeParams, String> promptUa;
	public static volatile SingularAttribute<PostalCodeParams, Long> id;
	public static volatile SingularAttribute<PostalCodeParams, Integer> maxLength;
	public static volatile SingularAttribute<PostalCodeParams, PostalCodeParamsStatus> status;

	public static final String PROMPT_EN = "promptEn";
	public static final String PREFIX = "prefix";
	public static final String MIN_LENGTH = "minLength";
	public static final String FORMAT = "format";
	public static final String AVAILABILITY = "availability";
	public static final String DELETED_BY = "deletedBy";
	public static final String INFO_URL_EN = "infoUrlEn";
	public static final String CONST_VALUE = "constValue";
	public static final String DELETED_DATE = "deletedDate";
	public static final String COUNTRY_CODE = "countryCode";
	public static final String INFO_URL_UA = "infoUrlUa";
	public static final String PROMPT_UA = "promptUa";
	public static final String ID = "id";
	public static final String MAX_LENGTH = "maxLength";
	public static final String STATUS = "status";

}

