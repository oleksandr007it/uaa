package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.EmailConfirmation;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.service.dto.EmailConfirmationDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-11T01:53:05+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class EmailConfirmationMapperImpl implements EmailConfirmationMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StatehoodSubjectMapper statehoodSubjectMapper;

    @Override
    public List<EmailConfirmation> toEntity(List<EmailConfirmationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<EmailConfirmation> list = new ArrayList<EmailConfirmation>( dtoList.size() );
        for ( EmailConfirmationDTO emailConfirmationDTO : dtoList ) {
            list.add( toEntity( emailConfirmationDTO ) );
        }

        return list;
    }

    @Override
    public List<EmailConfirmationDTO> toDto(List<EmailConfirmation> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<EmailConfirmationDTO> list = new ArrayList<EmailConfirmationDTO>( entityList.size() );
        for ( EmailConfirmation emailConfirmation : entityList ) {
            list.add( toDto( emailConfirmation ) );
        }

        return list;
    }

    @Override
    public Set<EmailConfirmationDTO> toDto(Set<EmailConfirmation> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<EmailConfirmationDTO> set = new HashSet<EmailConfirmationDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( EmailConfirmation emailConfirmation : entitySet ) {
            set.add( toDto( emailConfirmation ) );
        }

        return set;
    }

    @Override
    public EmailConfirmationDTO toDto(EmailConfirmation emailConfirmation) {
        if ( emailConfirmation == null ) {
            return null;
        }

        EmailConfirmationDTO emailConfirmationDTO = new EmailConfirmationDTO();

        emailConfirmationDTO.setDeclarantId( emailConfirmationDeclarantId( emailConfirmation ) );
        emailConfirmationDTO.setStatehoodSubjectId( emailConfirmationStatehoodSubjectId( emailConfirmation ) );
        emailConfirmationDTO.setCreatedById( emailConfirmationCreatedById( emailConfirmation ) );
        emailConfirmationDTO.setId( emailConfirmation.getId() );
        emailConfirmationDTO.setConfirmationStatus( emailConfirmation.getConfirmationStatus() );
        emailConfirmationDTO.setCreatedAt( emailConfirmation.getCreatedAt() );
        emailConfirmationDTO.setValidUntil( emailConfirmation.getValidUntil() );
        emailConfirmationDTO.setEmail( emailConfirmation.getEmail() );
        emailConfirmationDTO.setHtmlTemplateName( emailConfirmation.getHtmlTemplateName() );
        emailConfirmationDTO.setLangKey( emailConfirmation.getLangKey() );
        emailConfirmationDTO.setRejectedAt( emailConfirmation.getRejectedAt() );
        emailConfirmationDTO.setRejectDescription( emailConfirmation.getRejectDescription() );
        emailConfirmationDTO.setApprovedAt( emailConfirmation.getApprovedAt() );
        emailConfirmationDTO.setIpAddress( emailConfirmation.getIpAddress() );

        return emailConfirmationDTO;
    }

    @Override
    public EmailConfirmation toEntity(EmailConfirmationDTO emailConfirmationDTO) {
        if ( emailConfirmationDTO == null ) {
            return null;
        }

        EmailConfirmation emailConfirmation = new EmailConfirmation();

        emailConfirmation.statehoodSubject( statehoodSubjectMapper.fromId( emailConfirmationDTO.getStatehoodSubjectId() ) );
        emailConfirmation.createdBy( userMapper.userFromId( emailConfirmationDTO.getCreatedById() ) );
        emailConfirmation.declarant( userMapper.userFromId( emailConfirmationDTO.getDeclarantId() ) );
        emailConfirmation.setId( emailConfirmationDTO.getId() );
        emailConfirmation.confirmationStatus( emailConfirmationDTO.getConfirmationStatus() );
        emailConfirmation.createdAt( emailConfirmationDTO.getCreatedAt() );
        emailConfirmation.validUntil( emailConfirmationDTO.getValidUntil() );
        emailConfirmation.email( emailConfirmationDTO.getEmail() );
        emailConfirmation.htmlTemplateName( emailConfirmationDTO.getHtmlTemplateName() );
        emailConfirmation.langKey( emailConfirmationDTO.getLangKey() );
        emailConfirmation.rejectedAt( emailConfirmationDTO.getRejectedAt() );
        emailConfirmation.rejectDescription( emailConfirmationDTO.getRejectDescription() );
        emailConfirmation.approvedAt( emailConfirmationDTO.getApprovedAt() );
        emailConfirmation.ipAddress( emailConfirmationDTO.getIpAddress() );

        return emailConfirmation;
    }

    @Override
    public EmailConfirmation toEntity(User user) {
        if ( user == null ) {
            return null;
        }

        EmailConfirmation emailConfirmation = new EmailConfirmation();

        emailConfirmation.langKey( user.getLangKey() );
        emailConfirmation.createdBy( userMapper.userFromId( user.getId() ) );
        emailConfirmation.setId( user.getId() );

        emailConfirmation.confirmationStatus( getDefaultStatus() );
        emailConfirmation.createdAt( getNow() );

        return emailConfirmation;
    }

    private Long emailConfirmationDeclarantId(EmailConfirmation emailConfirmation) {
        if ( emailConfirmation == null ) {
            return null;
        }
        User declarant = emailConfirmation.getDeclarant();
        if ( declarant == null ) {
            return null;
        }
        Long id = declarant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long emailConfirmationStatehoodSubjectId(EmailConfirmation emailConfirmation) {
        if ( emailConfirmation == null ) {
            return null;
        }
        StatehoodSubject statehoodSubject = emailConfirmation.getStatehoodSubject();
        if ( statehoodSubject == null ) {
            return null;
        }
        Long id = statehoodSubject.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long emailConfirmationCreatedById(EmailConfirmation emailConfirmation) {
        if ( emailConfirmation == null ) {
            return null;
        }
        User createdBy = emailConfirmation.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        Long id = createdBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
