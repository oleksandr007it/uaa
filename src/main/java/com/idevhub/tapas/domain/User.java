package com.idevhub.tapas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.constants.PropertyStatus;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.enumeration.PositionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * A user.
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@Accessors(chain = true)
@ToString(of = {"id", "login"})
@EqualsAndHashCode(callSuper=false,of = {"id", "login","rnokpp","edrpouCode","userType"})
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(User.class);

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;


    @Column(name = "user_type")
    private String userType;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "status")
    private String status = UserStatus.ACTIVE;

    @Column(name = "rnokpp")
    private String rnokpp;

    @Column(name = "edrpou_code")
    private String edrpouCode;

    @Column(name = "org")
    private String org;

    @Column(name = "org_code")
    private String orgCode;

    @Column(name = "org_unit")
    private String orgUnit;

    @Column(name = "position")
    private String position;

    @Enumerated(EnumType.STRING)
    @Column(name = "position_type")
    private PositionType positionType;

    @Column(name = "property_status")
    private String propertyStatus = PropertyStatus.NOT_COMPLETED;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "pasport_sn")
    private String pasportSn;

    @Column(name = "pasport_issued_by")
    private String pasportIssuedBy;

    @Column(name = "pasport_date")
    private Instant pasportDate;

    @Column(name = "email_approve")
    private Boolean emailApprove = false;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)

    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    @CreatedBy
    @Column(name = "deleted_by")
    @JsonIgnore
    private String deletedBy;

    @CreatedDate
    @Column(name = "deleted_date")
    @JsonIgnore
    private Instant deletedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "legal_address_id")
    private Address legalAddress;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "_privilege_group_to_user",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "group_code", referencedColumnName = "code")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PrivilegeGroup> privilegeGroups = new HashSet<>();

    @ManyToOne(optional = false)
    @JsonIgnoreProperties("")
    private CEADepartment ceaDepartment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "central_executive_authority_id")
    @JsonIgnoreProperties("")
    private CentralExecutiveAuthority centralExecutiveAuthority;

    public Set<String> loadAndGetPrivilegeCodes() {
        log.debug("Lazy loading. User {} has {} privilege groups", id, privilegeGroups.size());

        return privilegeGroups.stream()
            .map(PrivilegeGroup::loadAndGetPrivilegeCodes)
            .flatMap(Collection::stream)
            .collect(toSet());
    }


}
