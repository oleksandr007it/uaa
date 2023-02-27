package com.idevhub.tapas.domain.address;

import com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectStatus;
import com.idevhub.tapas.domain.address.enumeration.NaisAtsDenormalizedObjectType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NaisAtsDenormalizedObject.class)
public abstract class NaisAtsDenormalizedObject_ {

	public static volatile SingularAttribute<NaisAtsDenormalizedObject, NaisAtsDenormalizedObject> parent;
	public static volatile SingularAttribute<NaisAtsDenormalizedObject, String> localityName;
	public static volatile SingularAttribute<NaisAtsDenormalizedObject, String> districtName;
	public static volatile SingularAttribute<NaisAtsDenormalizedObject, String> searchName;
	public static volatile SingularAttribute<NaisAtsDenormalizedObject, String> regionName;
	public static volatile SingularAttribute<NaisAtsDenormalizedObject, String> localityObjectName;
	public static volatile SingularAttribute<NaisAtsDenormalizedObject, Long> id;
	public static volatile SingularAttribute<NaisAtsDenormalizedObject, NaisAtsDenormalizedObjectType> type;
	public static volatile SingularAttribute<NaisAtsDenormalizedObject, NaisAtsDenormalizedObjectStatus> status;
	public static volatile SingularAttribute<NaisAtsDenormalizedObject, String> koatuuCode;

	public static final String PARENT = "parent";
	public static final String LOCALITY_NAME = "localityName";
	public static final String DISTRICT_NAME = "districtName";
	public static final String SEARCH_NAME = "searchName";
	public static final String REGION_NAME = "regionName";
	public static final String LOCALITY_OBJECT_NAME = "localityObjectName";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String STATUS = "status";
	public static final String KOATUU_CODE = "koatuuCode";

}

