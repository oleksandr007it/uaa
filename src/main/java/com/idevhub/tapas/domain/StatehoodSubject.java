package com.idevhub.tapas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A StatehoodSubject.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id", "subjectCode"})
@ToString(of = {"id", "subjectStatus", "subjectCode", "subjectName"})
@Table(name = "statehood_subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StatehoodSubject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    private String subjectStatus = UserStatus.ACTIVE;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountDetailsStatus accountDetailsStatus = AccountDetailsStatus.NOT_FULL;

    @NotNull
    private String subjectCode = "";

    @NotNull
    private String subjectName = "";

    @NotNull
    private String subjectShortName = "";

    private String headFullName;

    @NotNull
    private String telNumber = "";

    @NotNull
    private String email = "";

    @NotNull
    private boolean isEmailApproved = false;

    @NotNull
    private String eori = "";

    @NotNull
    private boolean isActualSameAsLegalAddress = false;

    @CreatedBy
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedBy
    @JsonIgnore
    private String updatedBy;

    private Instant updatedAt;

    @JsonIgnore
    private String deletedBy;

    private Instant deletedAt;

    @JsonIgnore
    @NotNull
    @ManyToOne(optional = false)
    private Kopfg kopfg;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "legal_address_id")
    private Address legalAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "actual_address_id")
    private Address actualAddress;

    public StatehoodSubject(Long subjectId) {
        this.id = subjectId;
    }

}
