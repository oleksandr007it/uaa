package com.idevhub.tapas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idevhub.tapas.domain.enumeration.EmailConfirmationStatus;
import com.idevhub.tapas.domain.enumeration.EmailRejectionReason;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A EmailConfirmation.
 */
@Entity
@Table(name = "email_confirmation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EmailConfirmation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "confirmation_status", nullable = false)
    private EmailConfirmationStatus confirmationStatus;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "valid_until", nullable = false)
    private Instant validUntil;

    @NotNull
    @Size(min = 5, max = 254)
    @Column(name = "email", length = 254, nullable = false)
    private String email;

    @NotNull
    @Column(name = "html_template_name", nullable = false)
    private String htmlTemplateName;

    @NotNull
    @Column(name = "lang_key", nullable = false)
    private String langKey;

    @Column(name = "rejected_at")
    private Instant rejectedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "reject_description")
    private EmailRejectionReason rejectDescription;

    @Column(name = "approved_at")
    private Instant approvedAt;

    @Column(name = "ip_address")
    private String ipAddress;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("")
    private User declarant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("")
    private StatehoodSubject statehoodSubject;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public EmailConfirmation confirmationStatus(EmailConfirmationStatus confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public EmailConfirmation createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public EmailConfirmation validUntil(Instant validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailConfirmation email(String email) {
        this.email = email;
        return this;
    }

    public String getHtmlTemplateName() {
        return htmlTemplateName;
    }

    public void setHtmlTemplateName(String htmlTemplateName) {
        this.htmlTemplateName = htmlTemplateName;
    }

    public EmailConfirmation htmlTemplateName(String htmlTemplateName) {
        this.htmlTemplateName = htmlTemplateName;
        return this;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public EmailConfirmation langKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public Instant getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(Instant rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public EmailConfirmation rejectedAt(Instant rejectedAt) {
        this.rejectedAt = rejectedAt;
        return this;
    }

    public EmailRejectionReason getRejectDescription() {
        return rejectDescription;
    }

    public void setRejectDescription(EmailRejectionReason rejectDescription) {
        this.rejectDescription = rejectDescription;
    }

    public EmailConfirmation rejectDescription(EmailRejectionReason rejectDescription) {
        this.rejectDescription = rejectDescription;
        return this;
    }

    public Instant getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(Instant approvedAt) {
        this.approvedAt = approvedAt;
    }

    public EmailConfirmation approvedAt(Instant approvedAt) {
        this.approvedAt = approvedAt;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public EmailConfirmation ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public EmailConfirmation createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public User getDeclarant() {
        return declarant;
    }

    public void setDeclarant(User user) {
        this.declarant = user;
    }

    public EmailConfirmation declarant(User user) {
        this.declarant = user;
        return this;
    }

    public StatehoodSubject getStatehoodSubject() {
        return statehoodSubject;
    }

    public void setStatehoodSubject(StatehoodSubject statehoodSubject) {
        this.statehoodSubject = statehoodSubject;
    }

    public EmailConfirmation statehoodSubject(StatehoodSubject statehoodSubject) {
        this.statehoodSubject = statehoodSubject;
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
        EmailConfirmation emailConfirmation = (EmailConfirmation) o;
        if (emailConfirmation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailConfirmation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailConfirmation{" +
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
            "}";
    }
}
