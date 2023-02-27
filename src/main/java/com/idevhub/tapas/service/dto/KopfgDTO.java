package com.idevhub.tapas.service.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the kopfg entity.
 */
public class KopfgDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    @Min(value = 3)
    @Max(value = 3)
    private Integer code;

    @NotNull
    @Size(min = 1, max = 128)
    private String name;

    private Instant validUntil;

    @Size(min = 3, max = 12)
    private String previousCodes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Instant validUntil) {
        this.validUntil = validUntil;
    }

    public String getPreviousCodes() {
        return previousCodes;
    }

    public void setPreviousCodes(String previousCodes) {
        this.previousCodes = previousCodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KopfgDTO kopfgDTO = (KopfgDTO) o;
        if (kopfgDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kopfgDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KopfgDTO{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", name='" + getName() + "'" +
            ", validUntil='" + getValidUntil() + "'" +
            ", previousCodes='" + getPreviousCodes() + "'" +
            "}";
    }
}
