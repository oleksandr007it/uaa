package com.idevhub.tapas.domain.address;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NaisAtsObjectStatus.class)
public abstract class NaisAtsObjectStatus_ {

	public static volatile SingularAttribute<NaisAtsObjectStatus, Long> code;
	public static volatile SingularAttribute<NaisAtsObjectStatus, String> name;
	public static volatile SingularAttribute<NaisAtsObjectStatus, String> description;
	public static volatile SingularAttribute<NaisAtsObjectStatus, Boolean> isActive;

	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String IS_ACTIVE = "isActive";

}

