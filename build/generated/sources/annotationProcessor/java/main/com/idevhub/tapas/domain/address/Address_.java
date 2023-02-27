package com.idevhub.tapas.domain.address;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, String> pavilionNumber;
	public static volatile SingularAttribute<Address, String> countryCode;
	public static volatile SingularAttribute<Address, Long> atsObjectId;
	public static volatile SingularAttribute<Address, String> street;
	public static volatile SingularAttribute<Address, String> postalCode;
	public static volatile SingularAttribute<Address, String> locality;
	public static volatile SingularAttribute<Address, String> houseNumber;
	public static volatile SingularAttribute<Address, String> id;
	public static volatile SingularAttribute<Address, String> region;
	public static volatile SingularAttribute<Address, String> regionalDistrict;
	public static volatile SingularAttribute<Address, String> apartmentNumber;

	public static final String PAVILION_NUMBER = "pavilionNumber";
	public static final String COUNTRY_CODE = "countryCode";
	public static final String ATS_OBJECT_ID = "atsObjectId";
	public static final String STREET = "street";
	public static final String POSTAL_CODE = "postalCode";
	public static final String LOCALITY = "locality";
	public static final String HOUSE_NUMBER = "houseNumber";
	public static final String ID = "id";
	public static final String REGION = "region";
	public static final String REGIONAL_DISTRICT = "regionalDistrict";
	public static final String APARTMENT_NUMBER = "apartmentNumber";

}

