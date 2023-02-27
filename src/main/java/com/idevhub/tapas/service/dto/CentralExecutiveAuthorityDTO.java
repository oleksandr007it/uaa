package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.enumeration.CentralExecutiveAuthorityStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the CentralExecutiveAuthority entity.
 */
public class CentralExecutiveAuthorityDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private CentralExecutiveAuthorityStatus centralExecutiveAuthorityStatus;

    @NotNull
    private Instant createdDate;

    @NotNull
    private Instant lastModifiedDate;

    private Instant deletedAt;

    @NotNull
    @Size(min = 8, max = 10)
    private String code;

    @NotNull
    @Size(max = 512)
    private String fullNameUkr;

    @Size(max = 512)
    private String fullNameEng;

    @NotNull
    @Size(max = 15)
    private String telNumber;

    @NotNull
    private String email;

    private String createdBy;

    private String lastModifiedBy;

    private String deletedBy;

    private String addressId;

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

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullNameUkr() {
        return fullNameUkr;
    }

    public void setFullNameUkr(String fullNameUkr) {
        this.fullNameUkr = fullNameUkr;
    }

    public String getFullNameEng() {
        return fullNameEng;
    }

    public void setFullNameEng(String fullNameEng) {
        this.fullNameEng = fullNameEng;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String userLogin) {
        this.lastModifiedBy = userLogin;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String userLogin) {
        this.deletedBy = userLogin;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CentralExecutiveAuthorityDTO centralExecutiveAuthorityDTO = (CentralExecutiveAuthorityDTO) o;
        if (centralExecutiveAuthorityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), centralExecutiveAuthorityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CentralExecutiveAuthorityDTO{" +
            "id=" + getId() +
            ", centralExecutiveAuthorityStatus='" + getCentralExecutiveAuthorityStatus() + "'" +
            ", createdAt='" + getCreatedDate() + "'" +
            ", updatedAt='" + getLastModifiedDate() + "'" +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", code='" + getCode() + "'" +
            ", fullNameUkr='" + getFullNameUkr() + "'" +
            ", fullNameEng='" + getFullNameEng() + "'" +
            ", telNumber='" + getTelNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedBy='" + getLastModifiedBy() + "'" +
            ", deletedBy='" + getDeletedBy() + "'" +
            ", address=" + getAddressId() +
            "}";
    }
}
