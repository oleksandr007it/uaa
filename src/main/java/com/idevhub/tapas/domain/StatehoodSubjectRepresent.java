package com.idevhub.tapas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * A StatehoodSubjectRepresent.
 */
@Entity
@Table(name = "statehood_subject_represent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
@ToString(of={"id", "declarant", "currentContext"})
public class StatehoodSubjectRepresent  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "subject_represent_status", nullable = false)
    private StatehoodSubjectRepresentStatus subjectRepresentStatus;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    private Instant deletedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "subject_represent_type", nullable = false)
    private StatehoodSubjectRepresentType subjectRepresentType;

    @CreatedBy
    @JsonIgnore
    private String createdBy;

    @LastModifiedBy
    @JsonIgnore
    private String updatedBy;

    @JsonIgnore
    private String deletedBy;

    @JsonIgnore
    @Column(name = "approve_sign")
    private String approveSignBase64;


    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnore
    private StatehoodSubject statehoodSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JsonIgnore
    private User declarant;

    @NotNull
    @Column(name = "is_current_context", nullable = false)
    private boolean currentContext;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "_privilege_group_to_represent",
        joinColumns = {@JoinColumn(name = "represent_id", referencedColumnName = "id")},
        inverseJoinColumns = {@JoinColumn(name = "group_code", referencedColumnName = "code")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PrivilegeGroup> privilegeGroups = new HashSet<>();

    public Set<String> loadAndGetPrivilegeCodes() {
        privilegeGroups.size();

        return privilegeGroups.stream()
            .map(PrivilegeGroup::loadAndGetPrivilegeCodes)
            .flatMap(Collection::stream)
            .collect(toSet());
    }
}
