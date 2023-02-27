package com.idevhub.tapas.security;


import com.idevhub.tapas.service.dto.UserDTOfromIdGovUa;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class LdapUserDetails implements UserDetails {
    private static final long serialVersionUID = 77006303808759963L;
    private final String username;
    private final UserDTOfromIdGovUa user;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;
    private final String ldapMemberOf; //Перелік груп в LDAP, у яких присутній користувач.
    private final String organization; //Назва суб'єкта господарювання до якого відноситься користувач.
    private final String customsBodyCode; //Код митного органу в межах Державної митної служби України.
    private final String employeeIdCardNumber; //Номер службового посвідчення користувача в Державній митній службі України.
    private final String ldapUserDirectoryId;  //Ідентифікатор об'єкта в службі каталогів (каталог, контейнер), в якому розміщений користувач.

    private String nonce;
    private final int MAX_MEMBER_OF_SIZE = 7168;

    public LdapUserDetails(String username, String ldapMemberOf, UserDTOfromIdGovUa user, String organization, String customsBodyCode, String employeeIdCardNumber, String ldapUserDirectoryId) {
        this(username, user, true, true, true, true, ldapMemberOf, organization,
            customsBodyCode, employeeIdCardNumber, ldapUserDirectoryId);
    }


    public LdapUserDetails(String username, UserDTOfromIdGovUa user, boolean enabled,
                           boolean accountNonExpired, boolean credentialsNonExpired,
                           boolean accountNonLocked, String ldapMemberOf, String organization, String customsBodyCode,
                           String employeeIdCardNumber, String ldapUserDirectoryId) {
        this.user = user;
        this.organization = organization;
        this.customsBodyCode = customsBodyCode;
        this.employeeIdCardNumber = employeeIdCardNumber;
        this.ldapUserDirectoryId = ldapUserDirectoryId;


        if (((username == null) || "".equals(username))) {
            throw new IllegalArgumentException(
                "Cannot pass null or empty values to constructor");
        }


        if (ldapMemberOf.getBytes().length < MAX_MEMBER_OF_SIZE) {
            this.ldapMemberOf = ldapMemberOf;
        } else {
            this.ldapMemberOf = "truncate";
        }

        this.username = username;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;

    }

    public String getOrganization() {
        return organization;
    }

    public String getCustomsBodyCode() {
        return customsBodyCode;
    }

    public String getEmployeeIdCardNumber() {
        return employeeIdCardNumber;
    }

    public String getLdapUserDirectoryId() {
        return ldapUserDirectoryId;
    }

    public String getLdapMemberOf() {
        return ldapMemberOf;
    }

    public UserDTOfromIdGovUa getUser() {
        return user;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new LinkedList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(AuthoritiesConstants.THIRD_APP);
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return "password";
    }


    public String getUsername() {
        return username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }


    /**
     * Returns {@code true} if the supplied object is a {@code User} instance with the
     * same {@code username} value.
     * <p>
     * In other words, the objects are equal if they have the same username, representing
     * the same principal.
     */
    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof org.springframework.security.core.userdetails.User) {
            return username.equals(((LdapUserDetails) rhs).username);
        }
        return false;
    }

    /**
     * Returns the hashcode of the {@code username}.
     */
    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired)
            .append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");
        sb.append("Not granted any authorities");


        return sb.toString();
    }


}
