package com.idevhub.tapas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.enumeration.CentralExecutiveAuthorityStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;

/**
 * A CentralExecutiveAuthority.
 */
@Entity
@Table(name = "central_executive_authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CentralExecutiveAuthority extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "central_executive_authority_status", nullable = false)
    private CentralExecutiveAuthorityStatus centralExecutiveAuthorityStatus;

    @NotNull
    @Size(min = 8, max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @NotNull
    @Size(max = 512)
    @Column(name = "full_name_ukr", length = 512, nullable = false)
    private String fullNameUkr;

    @Size(max = 512)
    @Column(name = "full_name_eng", length = 512)
    private String fullNameEng;

    @NotNull
    @Size(max = 15)
    @Column(name = "tel_number", length = 15, nullable = false)
    private String telNumber;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "deleted_by")
    @JsonIgnore
    private String deletedBy;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @JsonIgnoreProperties("")
    private Address address;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CentralExecutiveAuthorityStatus getCentralExecutiveAuthorityStatus() {
        return centralExecutiveAuthorityStatus;
    }

    public void setCentralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus centralExecutiveAuthorityStatus) {
        this.centralExecutiveAuthorityStatus = centralExecutiveAuthorityStatus;
    }

    public CentralExecutiveAuthority centralExecutiveAuthorityStatus(CentralExecutiveAuthorityStatus centralExecutiveAuthorityStatus) {
        this.centralExecutiveAuthorityStatus = centralExecutiveAuthorityStatus;
        return this;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public CentralExecutiveAuthority deletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CentralExecutiveAuthority code(String code) {
        this.code = code;
        return this;
    }

    public String getFullNameUkr() {
        return fullNameUkr;
    }

    public void setFullNameUkr(String fullNameUkr) {
        this.fullNameUkr = fullNameUkr;
    }

    public CentralExecutiveAuthority fullNameUkr(String fullNameUkr) {
        this.fullNameUkr = fullNameUkr;
        return this;
    }

    public String getFullNameEng() {
        return fullNameEng;
    }

    public void setFullNameEng(String fullNameEng) {
        this.fullNameEng = fullNameEng;
    }

    public CentralExecutiveAuthority fullNameEng(String fullNameEng) {
        this.fullNameEng = fullNameEng;
        return this;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public CentralExecutiveAuthority telNumber(String telNumber) {
        this.telNumber = telNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CentralExecutiveAuthority email(String email) {
        this.email = email;
        return this;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public CentralExecutiveAuthority deletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public CentralExecutiveAuthority address(Address address) {
        this.address = address;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CentralExecutiveAuthority centralExecutiveAuthority = (CentralExecutiveAuthority) o;
        if (centralExecutiveAuthority.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), centralExecutiveAuthority.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CentralExecutiveAuthority{" +
            "id=" + getId() +
            ", centralExecutiveAuthorityStatus='" + getCentralExecutiveAuthorityStatus() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", code='" + getCode() + "'" +
            ", fullNameUkr='" + getFullNameUkr() + "'" +
            ", fullNameEng='" + getFullNameEng() + "'" +
            ", telNumber='" + getTelNumber() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
