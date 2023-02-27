package com.idevhub.tapas.domain.address;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NaisAtsObject.class)
public abstract class NaisAtsObject_ {

	public static volatile SingularAttribute<NaisAtsObject, NaisAtsObject> parent;
	public static volatile SingularAttribute<NaisAtsObject, String> name;
	public static volatile SingularAttribute<NaisAtsObject, Long> id;
	public static volatile SingularAttribute<NaisAtsObject, NaisAtsObjectType> type;
	public static volatile SingularAttribute<NaisAtsObject, NaisAtsObjectStatus> status;
	public static volatile SingularAttribute<NaisAtsObject, String> koatuuCode;

	public static final String PARENT = "parent";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String STATUS = "status";
	public static final String KOATUU_CODE = "koatuuCode";

}

