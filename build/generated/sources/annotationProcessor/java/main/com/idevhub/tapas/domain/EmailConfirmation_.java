package com.idevhub.tapas.domain;

import com.idevhub.tapas.domain.enumeration.EmailConfirmationStatus;
import com.idevhub.tapas.domain.enumeration.EmailRejectionReason;
import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EmailConfirmation.class)
public abstract class EmailConfirmation_ {

	public static volatile SingularAttribute<EmailConfirmation, EmailConfirmationStatus> confirmationStatus;
	public static volatile SingularAttribute<EmailConfirmation, EmailRejectionReason> rejectDescription;
	public static volatile SingularAttribute<EmailConfirmation, String> ipAddress;
	public static volatile SingularAttribute<EmailConfirmation, StatehoodSubject> statehoodSubject;
	public static volatile SingularAttribute<EmailConfirmation, Instant> approvedAt;
	public static volatile SingularAttribute<EmailConfirmation, String> htmlTemplateName;
	public static volatile SingularAttribute<EmailConfirmation, Instant> createdAt;
	public static volatile SingularAttribute<EmailConfirmation, String> langKey;
	public static volatile SingularAttribute<EmailConfirmation, User> createdBy;
	public static volatile SingularAttribute<EmailConfirmation, Instant> rejectedAt;
	public static volatile SingularAttribute<EmailConfirmation, Instant> validUntil;
	public static volatile SingularAttribute<EmailConfirmation, Long> id;
	public static volatile SingularAttribute<EmailConfirmation, String> email;
	public static volatile SingularAttribute<EmailConfirmation, User> declarant;

	public static final String CONFIRMATION_STATUS = "confirmationStatus";
	public static final String REJECT_DESCRIPTION = "rejectDescription";
	public static final String IP_ADDRESS = "ipAddress";
	public static final String STATEHOOD_SUBJECT = "statehoodSubject";
	public static final String APPROVED_AT = "approvedAt";
	public static final String HTML_TEMPLATE_NAME = "htmlTemplateName";
	public static final String CREATED_AT = "createdAt";
	public static final String LANG_KEY = "langKey";
	public static final String CREATED_BY = "createdBy";
	public static final String REJECTED_AT = "rejectedAt";
	public static final String VALID_UNTIL = "validUntil";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String DECLARANT = "declarant";

}

