package com.idevhub.tapas.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Kopfg.class)
public abstract class Kopfg_ {

	public static volatile SingularAttribute<Kopfg, Integer> code;
	public static volatile SingularAttribute<Kopfg, String> name;
	public static volatile SingularAttribute<Kopfg, Instant> validUntil;
	public static volatile SingularAttribute<Kopfg, Long> id;
	public static volatile SingularAttribute<Kopfg, String> previousCodes;

	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String VALID_UNTIL = "validUntil";
	public static final String ID = "id";
	public static final String PREVIOUS_CODES = "previousCodes";

}

