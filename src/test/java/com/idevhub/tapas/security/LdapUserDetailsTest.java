package com.idevhub.tapas.security;

import com.idevhub.tapas.service.dto.UserDTOfromIdGovUa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import static org.assertj.core.api.Assertions.*;

public class LdapUserDetailsTest {
    private static final String DEFAULT_FIRSTNAME = "Адміністратор";
    private static final String DEFAULT_LASTNAME = "Міністренко";
    private static final String STUBBED_MIDDLE_NAME = "Дміністрович";
    private static final String STUBBED_RNOKPP = "3791012344";
    private static final String DEFAULT_LOGIN = "johndoe";
    private static final String DEFAULT_EMAIL = "abc@gmail.com";
    private static final String DEFAULT_LANGKEY = "en";

    private LdapUserDetails ldapUserDetails;

    @BeforeEach
    public void setUp() {
        final UserDTOfromIdGovUa userFromIdGovUaDTO = new UserDTOfromIdGovUa();
        this.ldapUserDetails = new LdapUserDetails("user", "test", userFromIdGovUaDTO, "organization", "customsBodyCode", "employeeIdCardNumber", "ldapUserDirectoryId");
    }

    @Test
    public void testSettersGetters() {
        ldapUserDetails.setNonce("nonce");

        assertThat(ldapUserDetails.getUsername()).isNotEmpty();
        assertThat(ldapUserDetails.getUser()).isNotNull();
        assertThat(ldapUserDetails.getAuthorities()).isNotNull();
        assertThat(ldapUserDetails.getLdapMemberOf()).isNotEmpty();
        assertThat(ldapUserDetails.getNonce()).isNotEmpty();
        assertThat(ldapUserDetails.getPassword()).isNotEmpty();
        assertThat(ldapUserDetails.isEnabled()).isTrue();
        assertThat(ldapUserDetails.isAccountNonExpired()).isTrue();
        assertThat(ldapUserDetails.isAccountNonLocked()).isTrue();
        assertThat(ldapUserDetails.isAccountNonExpired()).isTrue();
    }

    @Test
    public void testEquals() {
        assertThat(ldapUserDetails).isNotEqualTo(ldapUserDetails);
        assertThat(ldapUserDetails).isNotInstanceOf(User.class);

        assertThat(ldapUserDetails.getUsername()).isNotNull();
        assertThat(ldapUserDetails.getUsername()).isEqualTo(ldapUserDetails.getUsername());
    }

    @Test
    public void testHashCode() {
        final int result = ldapUserDetails.hashCode();

        assertThat(result).isEqualTo(ldapUserDetails.getUsername().hashCode());
    }

    @Test
    public void testToString() {
        final String result = ldapUserDetails.toString();

        assertThat(result).contains("LdapUserDetails");
        assertThat(result).isEqualTo(result);
    }
}
