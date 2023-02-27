package com.idevhub.tapas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idevhub.tapas.domain.enumeration.ActiveStatus;
import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.validation.ValidationException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Entity
@Data
@EqualsAndHashCode(of = "code", callSuper = false)
@Table(name = "_privilege_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@ToString(of = "code")
@Accessors(chain = true)
public class PrivilegeGroup extends AbstractAuditingEntity {
    private static final Logger log = LoggerFactory.getLogger(PrivilegeGroup.class);
    private static final long serialVersionUID = 1L;

    @Id
    private String code;

    @Enumerated(EnumType.STRING)
    private ActiveStatus status = ActiveStatus.ACTIVE;

    @Column(name = "is_global")
    private boolean global = true;

    private String fullNameUkr;

    private String fullNameEng;

    private String nameInDirectoryService;//DMSU

    @Enumerated(EnumType.STRING)
    private PrivilegeType privilegeGroupType;

    private String deletedBy;

    private Instant deletedAt;

    @ManyToOne
    @JsonIgnoreProperties("")
    private StatehoodSubject statehoodSubject;//LEGAL_ENTITY

    @ManyToOne
    @JsonIgnoreProperties("")
    private CentralExecutiveAuthority centralExecutiveAuthority;//CEA

    @ManyToMany
    @JoinTable(
        name = "_privilege_group_to_privilege",
        joinColumns = {@JoinColumn(name = "group_code", referencedColumnName = "code")},
        inverseJoinColumns = {@JoinColumn(name = "privilege_code", referencedColumnName = "code")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Privilege> privileges = new HashSet<>();

    public PrivilegeGroup() {
    }

    public PrivilegeGroup(String code, Set<Privilege> privileges) {
        this.code = code;
        this.privileges = privileges;
    }

    public Set<String> loadAndGetPrivilegeCodes() {
        log.debug("Lazy loading. PrivilegeGroup {} has {} privileges", code, privileges.size());

        return privileges.stream()
            .map(Privilege::getCode)
            .collect(toSet());
    }

    public void checkIsNotGlobal() {
        if (isGlobal()) throw new ValidationException("error.privilegeGroup.wrongType");
    }

    public void checkType(PrivilegeType expectedType) {
        if (privilegeGroupType != expectedType)
            throw new ValidationException(format("There is no such group with code=%s and type=%s", code, expectedType));
    }

    public void checkIsNotDeleted() {
        if (status == ActiveStatus.DELETED)
            throw new ValidationException("error.privilegeGroup.wrongStatus");
    }
}
