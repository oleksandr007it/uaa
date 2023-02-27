package com.idevhub.tapas.security;

import com.idevhub.tapas.service.dto.UserDTOfromIdGovUa;
import lombok.val;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class LdapTokenEnhancer implements TokenEnhancer {


    public final String FAMILY_NAME = "family_name";  //family_name	REQUIRED. Surname(s) or last name(s)
    private final String NONCE = "nonce";
    private final String SUB = "sub";
    private final String ISS = "iss";
    private final String AZP = "azp";
    private final String AUD = "aud";
    private final String NAME = "name";  //name	REQUIRED. End-User's full name
    private final String GIVEN_NAME = "given_name"; //    given_name	REQUIRED. Given name(s) or first name(s)
    private final String MIDDLE_NAME = "middle_name";
    private final String EMAIL = "email";
    private final String PHONE_NUMBER = "phone_number";
    private final String MEMBER_OF = "ldap_member_of";      //Перелік груп в LDAP, у яких присутній користувач.
    private final String ORGANIZATION = "organization"; //Назва суб'єкта господарювання до якого відноситься користувач.
    private final String CUSTOMS_BODY_CODE = "customs_body_code"; //Код митного органу в межах Державної митної служби України.
    private final String EMPLOYEE_ID_CARD_NUMBER = "employee_id_card_number"; //Номер службового посвідчення користувача в Державній митній службі України.
    private final String LDAP_USER_DIRECTORY_ID = "ldap_user_directory_id";  //Ідентифікатор об'єкта в службі каталогів (каталог, контейнер), в якому розміщений користувач.
    private final String RNOKPP = "rnokpp"; // REQUIRED. Реєстраційний номер облікової картки платника податків.
    private final String EDROPU = "edrpou";
    private final String DEPARTAMENT = "department";
    private final String POSITION = "position";


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        addClaims((DefaultOAuth2AccessToken) accessToken, authentication);
        return accessToken;
    }

    private void addClaims(DefaultOAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation() != null && !accessToken.getAdditionalInformation().isEmpty()
            ? accessToken.getAdditionalInformation()
            : new LinkedHashMap<>();
        addUserIdClaim(additionalInformation, authentication);

        accessToken.setAdditionalInformation(additionalInformation);
    }

    private void addUserIdClaim(Map<String, Object> additionalInformation, OAuth2Authentication authentication) {
        val principal = authentication.getPrincipal();
        if (!(principal instanceof LdapUserDetails)) return;
        val ldapUserDetails = ((LdapUserDetails) principal);
        UserDTOfromIdGovUa user = ldapUserDetails.getUser();
        additionalInformation.put(NONCE, ldapUserDetails.getNonce());
        additionalInformation.put(NAME, user.getFullName());
        additionalInformation.put(SUB, Sha512DigestUtils.shaHex(user.getRnokpp() + user.getFullName()));
        additionalInformation.put(GIVEN_NAME, user.getFirstName());
        additionalInformation.put(FAMILY_NAME, user.getLastName());
        additionalInformation.put(MIDDLE_NAME, user.getMiddleName());
        additionalInformation.put(EMAIL, user.getEmail());
        additionalInformation.put(PHONE_NUMBER, user.getPhone());
        additionalInformation.put(MEMBER_OF, ldapUserDetails.getLdapMemberOf());
        additionalInformation.put(ORGANIZATION, ldapUserDetails.getOrganization());
        additionalInformation.put(CUSTOMS_BODY_CODE, ldapUserDetails.getCustomsBodyCode());
        additionalInformation.put(EMPLOYEE_ID_CARD_NUMBER, ldapUserDetails.getEmployeeIdCardNumber());
        additionalInformation.put(LDAP_USER_DIRECTORY_ID, ldapUserDetails.getLdapUserDirectoryId());
        additionalInformation.put(RNOKPP, user.getRnokpp());
        additionalInformation.put(EDROPU, user.getEdrpouCode());
        additionalInformation.put(DEPARTAMENT, user.getOrgUnit());
        additionalInformation.put(POSITION, user.getPosition());
        additionalInformation.put(ISS, "https://customs.gov.ua/openid");
        additionalInformation.put(AUD, authentication.getOAuth2Request().getClientId());
        additionalInformation.put(AZP, authentication.getOAuth2Request().getClientId());

    }
}

