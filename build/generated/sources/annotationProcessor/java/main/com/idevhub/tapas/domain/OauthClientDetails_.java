package com.idevhub.tapas.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OauthClientDetails.class)
public abstract class OauthClientDetails_ {

	public static volatile SingularAttribute<OauthClientDetails, String> authorizedGrantTypes;
	public static volatile SingularAttribute<OauthClientDetails, String> additionalInformation;
	public static volatile SingularAttribute<OauthClientDetails, String> clientId;
	public static volatile SingularAttribute<OauthClientDetails, String> clientUrl;
	public static volatile SingularAttribute<OauthClientDetails, String> autoapprove;
	public static volatile SingularAttribute<OauthClientDetails, String> authorities;
	public static volatile SingularAttribute<OauthClientDetails, Integer> accessTokenValidity;
	public static volatile SingularAttribute<OauthClientDetails, Integer> refreshTokenValidity;
	public static volatile SingularAttribute<OauthClientDetails, String> scope;
	public static volatile SingularAttribute<OauthClientDetails, String> clientSecret;
	public static volatile SingularAttribute<OauthClientDetails, String> webServerRedirectUri;
	public static volatile SingularAttribute<OauthClientDetails, Long> id;
	public static volatile SingularAttribute<OauthClientDetails, String> resourceIds;

	public static final String AUTHORIZED_GRANT_TYPES = "authorizedGrantTypes";
	public static final String ADDITIONAL_INFORMATION = "additionalInformation";
	public static final String CLIENT_ID = "clientId";
	public static final String CLIENT_URL = "clientUrl";
	public static final String AUTOAPPROVE = "autoapprove";
	public static final String AUTHORITIES = "authorities";
	public static final String ACCESS_TOKEN_VALIDITY = "accessTokenValidity";
	public static final String REFRESH_TOKEN_VALIDITY = "refreshTokenValidity";
	public static final String SCOPE = "scope";
	public static final String CLIENT_SECRET = "clientSecret";
	public static final String WEB_SERVER_REDIRECT_URI = "webServerRedirectUri";
	public static final String ID = "id";
	public static final String RESOURCE_IDS = "resourceIds";

}

