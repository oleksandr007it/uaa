package com.idevhub.tapas.security;

import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.service.dto.UserDTOfromIdGovUa;
import com.idevhub.tapas.service.dto.UserFromLdapDTO;
import com.idevhub.tapas.service.feign.LdapIntegrationClient;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.idevhub.tapas.service.utils.StringTransformUtils.hideStr;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;
    private final LdapIntegrationClient ldapIntegrationClient;

    public DomainUserDetailsService(UserRepository userRepository, LdapIntegrationClient ldapIntegrationClient) {
        this.userRepository = userRepository;
        this.ldapIntegrationClient = ldapIntegrationClient;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        if (new EmailValidator().isValid(login, null)) {
            return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(login)
                .map(user -> createSpringSecurityUser(login, user))
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + login + " was not found in the database"));
        }

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin)
            .map(user -> createSpringSecurityUser(lowercaseLogin, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));

    }

    @Transactional
    public UserDetails loadUserByRnokppAndUserType(final String rnokpp, final String userType) {
        log.debug("Try to find user by rnokpp={} and type={} and create UserDetails", hideStr(rnokpp), userType);

        return userRepository.findOneByRnokppAndUserType(rnokpp, userType)
            .map(user -> createSpringSecurityUser(rnokpp, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + rnokpp + " was not found in the database"));

    }


    public UserDetails loadUserByLdap(UserDTOfromIdGovUa user) {


        return createSpringSecurityUserFromLdap(user);
    }

    private UserDetails createSpringSecurityUserFromLdap(UserDTOfromIdGovUa user) {

        String rnokpp = user.getRnokpp();
        List<UserFromLdapDTO> ldapDTOList = ldapIntegrationClient.getUserFromLdap(rnokpp);

        if ((ldapDTOList.size() == 0) || (ldapDTOList.size() > 1)) {
            throw new UsernameNotFoundException("error find user from Ldap");
        }
        UserFromLdapDTO ldapDTO = ldapDTOList.get(0);
        String memberOf = StringUtils.join(ldapDTO.getMemberOf(), ",");
        String customsBodyCode = ldapDTO.getCustomsCode();
        String employeeIdCardNumber = ldapDTO.getIdCard();
        String ldapUserDirectoryId = ldapDTO.getGuid();
        String customsName = ldapDTO.getCustomsName();

        return new LdapUserDetails(rnokpp, memberOf, user, customsName, customsBodyCode, employeeIdCardNumber, ldapUserDirectoryId);
    }


    @Transactional
    public UserDetails loadUserByRnokppAndEdrpouCodeAndUserType(final String rnokpp, final String edrpouCode, final String userType) {
        return userRepository.findOneByRnokppAndEdrpouCodeAndUserType(rnokpp, edrpouCode, userType)
            .map(user -> createSpringSecurityUser(rnokpp, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + rnokpp + " was not found in the database"));
    }


    private UserDetails createSpringSecurityUser(String lowercaseLogin, User user) {

        if (!user.isActivated()) {
            log.error("User " + lowercaseLogin + " was not activated");
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }

        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            log.error("User " + lowercaseLogin + "have not status ACTIVE");
            throw new UserNotActivatedException("User " + lowercaseLogin + " have not status ACTIVE");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
        return new CustomUserDetails(user.getLogin(),
            user.getPassword(),
            grantedAuthorities, user.getId());
    }
}
