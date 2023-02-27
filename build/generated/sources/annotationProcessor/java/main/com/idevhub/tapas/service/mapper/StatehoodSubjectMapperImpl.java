package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.Kopfg;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.service.dto.StatehoodSubjectCreateDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectDTO;
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
public class StatehoodSubjectMapperImpl implements StatehoodSubjectMapper {

    @Autowired
    private KopfgMapper kopfgMapper;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<StatehoodSubject> toEntity(List<StatehoodSubjectDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StatehoodSubject> list = new ArrayList<StatehoodSubject>( dtoList.size() );
        for ( StatehoodSubjectDTO statehoodSubjectDTO : dtoList ) {
            list.add( toEntity( statehoodSubjectDTO ) );
        }

        return list;
    }

    @Override
    public List<StatehoodSubjectDTO> toDto(List<StatehoodSubject> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StatehoodSubjectDTO> list = new ArrayList<StatehoodSubjectDTO>( entityList.size() );
        for ( StatehoodSubject statehoodSubject : entityList ) {
            list.add( toDto( statehoodSubject ) );
        }

        return list;
    }

    @Override
    public Set<StatehoodSubjectDTO> toDto(Set<StatehoodSubject> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<StatehoodSubjectDTO> set = new HashSet<StatehoodSubjectDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( StatehoodSubject statehoodSubject : entitySet ) {
            set.add( toDto( statehoodSubject ) );
        }

        return set;
    }

    @Override
    public StatehoodSubjectDTO toDto(StatehoodSubject statehoodSubject) {
        if ( statehoodSubject == null ) {
            return null;
        }

        StatehoodSubjectDTO statehoodSubjectDTO = new StatehoodSubjectDTO();

        statehoodSubjectDTO.setKopfgId( statehoodSubjectKopfgId( statehoodSubject ) );
        Integer code = statehoodSubjectKopfgCode( statehoodSubject );
        if ( code != null ) {
            statehoodSubjectDTO.setKopfgCode( String.valueOf( code ) );
        }
        statehoodSubjectDTO.setId( statehoodSubject.getId() );
        statehoodSubjectDTO.setSubjectStatus( statehoodSubject.getSubjectStatus() );
        statehoodSubjectDTO.setAccountDetailsStatus( statehoodSubject.getAccountDetailsStatus() );
        statehoodSubjectDTO.setCreatedAt( statehoodSubject.getCreatedAt() );
        statehoodSubjectDTO.setUpdatedAt( statehoodSubject.getUpdatedAt() );
        statehoodSubjectDTO.setDeletedAt( statehoodSubject.getDeletedAt() );
        statehoodSubjectDTO.setSubjectCode( statehoodSubject.getSubjectCode() );
        statehoodSubjectDTO.setSubjectName( statehoodSubject.getSubjectName() );
        statehoodSubjectDTO.setSubjectShortName( statehoodSubject.getSubjectShortName() );
        statehoodSubjectDTO.setHeadFullName( statehoodSubject.getHeadFullName() );
        statehoodSubjectDTO.setTelNumber( statehoodSubject.getTelNumber() );
        statehoodSubjectDTO.setEmail( statehoodSubject.getEmail() );
        statehoodSubjectDTO.setIsEmailApproved( statehoodSubject.isIsEmailApproved() );
        statehoodSubjectDTO.setEori( statehoodSubject.getEori() );
        statehoodSubjectDTO.setIsActualSameAsLegalAddress( statehoodSubject.isIsActualSameAsLegalAddress() );
        statehoodSubjectDTO.setCreatedBy( statehoodSubject.getCreatedBy() );
        statehoodSubjectDTO.setUpdatedBy( statehoodSubject.getUpdatedBy() );
        statehoodSubjectDTO.setDeletedBy( statehoodSubject.getDeletedBy() );
        statehoodSubjectDTO.setLegalAddress( addressMapper.toDto( statehoodSubject.getLegalAddress() ) );
        statehoodSubjectDTO.setActualAddress( addressMapper.toDto( statehoodSubject.getActualAddress() ) );

        return statehoodSubjectDTO;
    }

    @Override
    public StatehoodSubject toEntity(StatehoodSubjectCreateDTO statehoodSubjectDTO) {
        if ( statehoodSubjectDTO == null ) {
            return null;
        }

        StatehoodSubject statehoodSubject = new StatehoodSubject();

        statehoodSubject.kopfg( kopfgMapper.fromId( statehoodSubjectDTO.getKopfgId() ) );
        statehoodSubject.subjectStatus( statehoodSubjectDTO.getSubjectStatus() );
        statehoodSubject.subjectCode( statehoodSubjectDTO.getSubjectCode() );
        statehoodSubject.subjectName( statehoodSubjectDTO.getSubjectName() );
        statehoodSubject.subjectShortName( statehoodSubjectDTO.getSubjectShortName() );
        statehoodSubject.headFullName( statehoodSubjectDTO.getHeadFullName() );
        statehoodSubject.telNumber( statehoodSubjectDTO.getTelNumber() );
        statehoodSubject.email( statehoodSubjectDTO.getEmail() );
        statehoodSubject.eori( statehoodSubjectDTO.getEori() );
        statehoodSubject.setIsActualSameAsLegalAddress( statehoodSubjectDTO.getIsActualSameAsLegalAddress() );

        return statehoodSubject;
    }

    @Override
    public StatehoodSubject toEntity(StatehoodSubjectDTO statehoodSubjectDTO) {
        if ( statehoodSubjectDTO == null ) {
            return null;
        }

        StatehoodSubject statehoodSubject = new StatehoodSubject();

        statehoodSubject.kopfg( kopfgMapper.fromId( statehoodSubjectDTO.getKopfgId() ) );
        statehoodSubject.id( statehoodSubjectDTO.getId() );
        statehoodSubject.subjectStatus( statehoodSubjectDTO.getSubjectStatus() );
        statehoodSubject.accountDetailsStatus( statehoodSubjectDTO.getAccountDetailsStatus() );
        statehoodSubject.createdAt( statehoodSubjectDTO.getCreatedAt() );
        statehoodSubject.updatedAt( statehoodSubjectDTO.getUpdatedAt() );
        statehoodSubject.deletedAt( statehoodSubjectDTO.getDeletedAt() );
        statehoodSubject.subjectCode( statehoodSubjectDTO.getSubjectCode() );
        statehoodSubject.subjectName( statehoodSubjectDTO.getSubjectName() );
        statehoodSubject.subjectShortName( statehoodSubjectDTO.getSubjectShortName() );
        statehoodSubject.headFullName( statehoodSubjectDTO.getHeadFullName() );
        statehoodSubject.telNumber( statehoodSubjectDTO.getTelNumber() );
        statehoodSubject.email( statehoodSubjectDTO.getEmail() );
        statehoodSubject.setIsEmailApproved( statehoodSubjectDTO.isIsEmailApproved() );
        statehoodSubject.eori( statehoodSubjectDTO.getEori() );
        statehoodSubject.setIsActualSameAsLegalAddress( statehoodSubjectDTO.isIsActualSameAsLegalAddress() );
        statehoodSubject.createdBy( statehoodSubjectDTO.getCreatedBy() );
        statehoodSubject.updatedBy( statehoodSubjectDTO.getUpdatedBy() );
        statehoodSubject.deletedBy( statehoodSubjectDTO.getDeletedBy() );
        statehoodSubject.legalAddress( addressMapper.toEntity( statehoodSubjectDTO.getLegalAddress() ) );
        statehoodSubject.actualAddress( addressMapper.toEntity( statehoodSubjectDTO.getActualAddress() ) );

        return statehoodSubject;
    }

    private Long statehoodSubjectKopfgId(StatehoodSubject statehoodSubject) {
        if ( statehoodSubject == null ) {
            return null;
        }
        Kopfg kopfg = statehoodSubject.getKopfg();
        if ( kopfg == null ) {
            return null;
        }
        Long id = kopfg.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer statehoodSubjectKopfgCode(StatehoodSubject statehoodSubject) {
        if ( statehoodSubject == null ) {
            return null;
        }
        Kopfg kopfg = statehoodSubject.getKopfg();
        if ( kopfg == null ) {
            return null;
        }
        Integer code = kopfg.getCode();
        if ( code == null ) {
            return null;
        }
        return code;
    }
}
