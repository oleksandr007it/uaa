package com.idevhub.tapas.service.dto;

import com.idevhub.tapas.domain.enumeration.EmailConfirmationStatus;
import com.idevhub.tapas.domain.enumeration.EmailRejectionReason;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the EmailConfirmation entity.
 */
public class EmailConfirmationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private EmailConfirmationStatus confirmationStatus;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant validUntil;

    @NotNull
    @Size(min = 5, max = 254)
    private String email;

    @NotNull
    private String htmlTemplateName;

    @NotNull
    private String langKey;

    private Instant rejectedAt;

    private EmailRejectionReason rejectDescription;

    private Instant approvedAt;

    private String ipAddress;

    private Long createdById;

    private Long declarantId;

    private Long statehoodSubjectId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmailConfirmationStatus getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(EmailConfirmationStatus confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHtmlTemplateName() {
        return htmlTemplateName;
    }

    public void setHtmlTemplateName(String htmlTemplateName) {
        this.htmlTemplateName = htmlTemplateName;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Instant getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(Instant rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public EmailRejectionReason getRejectDescription() {
        return rejectDescription;
    }

    public void setRejectDescription(EmailRejectionReason rejectDescription) {
        this.rejectDescription = rejectDescription;
    }

    public Instant getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Instant approvedAt) {
        this.approvedAt = approvedAt;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long userId) {
        this.createdById = userId;
    }

    public Long getDeclarantId() {
        return declarantId;
    }

    public void setDeclarantId(Long userId) {
        this.declarantId = userId;
    }

    public Long getStatehoodSubjectId() {
        return statehoodSubjectId;
    }

    public void setStatehoodSubjectId(Long statehoodSubjectId) {
        this.statehoodSubjectId = statehoodSubjectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailConfirmationDTO emailConfirmationDTO = (EmailConfirmationDTO) o;
        if (emailConfirmationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailConfirmationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailConfirmationDTO{" +
            "id=" + getId() +
            ", confirmationStatus='" + getConfirmationStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            ", email='" + getEmail() + "'" +
            ", htmlTemplateName='" + getHtmlTemplateName() + "'" +
            ", langKey='" + getLangKey() + "'" +
            ", rejectedAt='" + getRejectedAt() + "'" +
            ", rejectDescription='" + getRejectDescription() + "'" +
            ", approvedAt='" + getApprovedAt() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", createdBy=" + getCreatedById() +
            ", declarant=" + getDeclarantId() +
            ", statehoodSubject=" + getStatehoodSubjectId() +
            "}";
    }
}
