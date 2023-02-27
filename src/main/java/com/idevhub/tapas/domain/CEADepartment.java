package com.idevhub.tapas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.enumeration.CEADepartmentStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;

/**
 * A CEADepartment.
 */
@Entity
@Table(name = "central_exec_auth_department")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CEADepartment extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "central_executive_authority_department_status", nullable = false)
    private CEADepartmentStatus ceaDepartmentStatus;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "deleted_by")
    private String deletedBy;

    @NotNull
    @Size(max = 256)
    @Column(name = "full_name_ukr", length = 256, nullable = false)
    private String fullNameUkr;

    @NotNull
    @Size(max = 256)
    @Column(name = "full_name_eng", length = 256)
    private String fullNameEng;

    @NotNull
    @Size(max = 256)
    @Column(name = "description", length = 256, nullable = false)
    private String description;

    @NotNull
    @Size(max = 15)
    @Column(name = "tel_number", length = 15, nullable = false)
    private String telNumber;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    @JsonIgnoreProperties("")
    private CentralExecutiveAuthority centralExecutiveAuthority;

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

    public CEADepartmentStatus getCeaDepartmentStatus() {
        return ceaDepartmentStatus;
    }

    public void setCeaDepartmentStatus(CEADepartmentStatus ceaDepartmentStatus) {
        this.ceaDepartmentStatus = ceaDepartmentStatus;
    }

    public CEADepartment ceaDepartmentStatus(CEADepartmentStatus ceaDepartmentStatus) {
        this.ceaDepartmentStatus = ceaDepartmentStatus;
        return this;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public CEADepartment deletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public CEADepartment deletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
        return this;
    }

    public String getFullNameUkr() {
        return fullNameUkr;
    }

    public void setFullNameUkr(String fullNameUkr) {
        this.fullNameUkr = fullNameUkr;
    }

    public CEADepartment fullNameUkr(String fullNameUkr) {
        this.fullNameUkr = fullNameUkr;
        return this;
    }

    public String getFullNameEng() {
        return fullNameEng;
    }

    public void setFullNameEng(String fullNameEng) {
        this.fullNameEng = fullNameEng;
    }

    public CEADepartment fullNameEng(String fullNameEng) {
        this.fullNameEng = fullNameEng;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CEADepartment description(String description) {
        this.description = description;
        return this;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public CEADepartment telNumber(String telNumber) {
        this.telNumber = telNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CEADepartment email(String email) {
        this.email = email;
        return this;
    }

    public CentralExecutiveAuthority getCentralExecutiveAuthority() {
        return centralExecutiveAuthority;
    }

    public void setCentralExecutiveAuthority(CentralExecutiveAuthority centralExecutiveAuthority) {
        this.centralExecutiveAuthority = centralExecutiveAuthority;
    }

    public CEADepartment centralExecutiveAuthority(CentralExecutiveAuthority centralExecutiveAuthority) {
        this.centralExecutiveAuthority = centralExecutiveAuthority;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public CEADepartment address(Address address) {
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
        CEADepartment ceaDepartment = (CEADepartment) o;
        if (ceaDepartment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ceaDepartment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CEADepartment{" +
            "id=" + getId() +
            ", ceaDepartmentStatus='" + getCeaDepartmentStatus() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", deletedBy='" + getDeletedBy() + "'" +
            ", fullNameUkr='" + getFullNameUkr() + "'" +
            ", fullNameEng='" + getFullNameEng() + "'" +
            ", description='" + getDescription() + "'" +
            ", telNumber='" + getTelNumber() + "'" +
            ", email='" + getEmail() + "'" +
            "}";
    }
}
