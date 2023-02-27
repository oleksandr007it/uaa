package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.EmailConfirmation;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.enumeration.EmailConfirmationStatus;
import com.idevhub.tapas.service.dto.EmailConfirmationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

/**
 * Mapper for the entity EmailConfirmation and its DTO EmailConfirmationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, StatehoodSubjectMapper.class})
public interface EmailConfirmationMapper extends EntityMapper<EmailConfirmationDTO, EmailConfirmation> {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "declarant.id", target = "declarantId")
    @Mapping(source = "statehoodSubject.id", target = "statehoodSubjectId")
    EmailConfirmationDTO toDto(EmailConfirmation emailConfirmation);

    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(source = "declarantId", target = "declarant")
    @Mapping(source = "statehoodSubjectId", target = "statehoodSubject")
    EmailConfirmation toEntity(EmailConfirmationDTO emailConfirmationDTO);

    @Mapping(target = "confirmationStatus", expression = "java(getDefaultStatus())")
    @Mapping(target = "createdAt", expression = "java(getNow())")
    @Mapping(source = "langKey", target = "langKey")
    @Mapping(source = "user.id", target = "createdBy")
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "declarant", ignore = true)
    @Mapping(target = "htmlTemplateName", ignore = true)
    @Mapping(target = "validUntil", ignore = true)
    @Mapping(target = "rejectedAt", ignore = true)
    @Mapping(target = "rejectDescription", ignore = true)
    @Mapping(target = "approvedAt", ignore = true)
    @Mapping(target = "ipAddress", ignore = true)
    @Mapping(target = "statehoodSubject", ignore = true)
    EmailConfirmation toEntity(User user);

    default EmailConfirmationStatus getDefaultStatus() {
        return EmailConfirmationStatus.ACTIVE;
    }

    default Instant getNow() {
        return Instant.now();
    }

    default EmailConfirmation fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailConfirmation emailConfirmation = new EmailConfirmation();
        emailConfirmation.setId(id);
        return emailConfirmation;
    }
}
