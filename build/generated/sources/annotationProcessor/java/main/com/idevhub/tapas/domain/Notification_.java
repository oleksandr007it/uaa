package com.idevhub.tapas.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Notification.class)
public abstract class Notification_ {

	public static volatile SingularAttribute<Notification, String> address;
	public static volatile SingularAttribute<Notification, String> subject;
	public static volatile SingularAttribute<Notification, Long> id;
	public static volatile SingularAttribute<Notification, String> type;
	public static volatile SingularAttribute<Notification, Instant> creationDate;
	public static volatile SingularAttribute<Notification, String> content;

	public static final String ADDRESS = "address";
	public static final String SUBJECT = "subject";
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String CREATION_DATE = "creationDate";
	public static final String CONTENT = "content";

}

