package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.PrivilegeGroup;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.service.dto.PrivilegeGroupGeneralDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectRepresentDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectRepresentMainInfoDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectRepresentWithNameDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-11T01:53:04+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@Component
public class StatehoodSubjectRepresentMapperImpl implements StatehoodSubjectRepresentMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StatehoodSubjectMapper statehoodSubjectMapper;

    @Override
    public List<StatehoodSubjectRepresent> toEntity(List<StatehoodSubjectRepresentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<StatehoodSubjectRepresent> list = new ArrayList<StatehoodSubjectRepresent>( dtoList.size() );
        for ( StatehoodSubjectRepresentDTO statehoodSubjectRepresentDTO : dtoList ) {
            list.add( toEntity( statehoodSubjectRepresentDTO ) );
        }

        return list;
    }

    @Override
    public List<StatehoodSubjectRepresentDTO> toDto(List<StatehoodSubjectRepresent> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StatehoodSubjectRepresentDTO> list = new ArrayList<StatehoodSubjectRepresentDTO>( entityList.size() );
        for ( StatehoodSubjectRepresent statehoodSubjectRepresent : entityList ) {
            list.add( toDto( statehoodSubjectRepresent ) );
        }

        return list;
    }

    @Override
    public Set<StatehoodSubjectRepresentDTO> toDto(Set<StatehoodSubjectRepresent> entitySet) {
        if ( entitySet == null ) {
            return null;
        }

        Set<StatehoodSubjectRepresentDTO> set = new HashSet<StatehoodSubjectRepresentDTO>( Math.max( (int) ( entitySet.size() / .75f ) + 1, 16 ) );
        for ( StatehoodSubjectRepresent statehoodSubjectRepresent : entitySet ) {
            set.add( toDto( statehoodSubjectRepresent ) );
        }

        return set;
    }

    @Override
    public StatehoodSubjectRepresentDTO toDto(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }

        StatehoodSubjectRepresentDTO statehoodSubjectRepresentDTO = new StatehoodSubjectRepresentDTO();

        statehoodSubjectRepresentDTO.setUpdatedBy( statehoodSubjectRepresent.getUpdatedBy() );
        statehoodSubjectRepresentDTO.setStatehoodSubjectId( statehoodSubjectRepresentStatehoodSubjectId( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentDTO.setDeclarantId( statehoodSubjectRepresentDeclarantId( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentDTO.setCreatedBy( statehoodSubjectRepresent.getCreatedBy() );
        statehoodSubjectRepresentDTO.setDeclarantLogin( statehoodSubjectRepresentDeclarantLogin( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentDTO.setDeletedBy( statehoodSubjectRepresent.getDeletedBy() );
        statehoodSubjectRepresentDTO.setIsCurrentContext( statehoodSubjectRepresent.isCurrentContext() );
        statehoodSubjectRepresentDTO.setId( statehoodSubjectRepresent.getId() );
        statehoodSubjectRepresentDTO.setSubjectRepresentStatus( statehoodSubjectRepresent.getSubjectRepresentStatus() );
        statehoodSubjectRepresentDTO.setCreatedAt( statehoodSubjectRepresent.getCreatedAt() );
        statehoodSubjectRepresentDTO.setUpdatedAt( statehoodSubjectRepresent.getUpdatedAt() );
        statehoodSubjectRepresentDTO.setDeletedAt( statehoodSubjectRepresent.getDeletedAt() );
        statehoodSubjectRepresentDTO.setSubjectRepresentType( statehoodSubjectRepresent.getSubjectRepresentType() );
        statehoodSubjectRepresentDTO.setPrivilegeGroups( privilegeGroupSetToPrivilegeGroupGeneralDTOList( statehoodSubjectRepresent.getPrivilegeGroups() ) );

        return statehoodSubjectRepresentDTO;
    }

    @Override
    public StatehoodSubjectRepresentMainInfoDTO toMainInfoDto(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }

        StatehoodSubjectRepresentMainInfoDTO statehoodSubjectRepresentMainInfoDTO = new StatehoodSubjectRepresentMainInfoDTO();

        statehoodSubjectRepresentMainInfoDTO.setStatehoodSubjectId( statehoodSubjectRepresentStatehoodSubjectId( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentMainInfoDTO.setDeclarantId( statehoodSubjectRepresentDeclarantId( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentMainInfoDTO.setSubjectStatus( statehoodSubjectRepresentStatehoodSubjectSubjectStatus( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentMainInfoDTO.setSubjectRepresentType( statehoodSubjectRepresent.getSubjectRepresentType() );
        statehoodSubjectRepresentMainInfoDTO.setAccountDetailsStatus( statehoodSubjectRepresentStatehoodSubjectAccountDetailsStatus( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentMainInfoDTO.setStatehoodSubjectEdrpou( statehoodSubjectRepresentStatehoodSubjectSubjectCode( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentMainInfoDTO.setDeclarantFirstName( statehoodSubjectRepresentDeclarantFirstName( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentMainInfoDTO.setDeclarantMiddleName( statehoodSubjectRepresentDeclarantMiddleName( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentMainInfoDTO.setDeclarantLastName( statehoodSubjectRepresentDeclarantLastName( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentMainInfoDTO.setStatehoodSubjectFullName( statehoodSubjectRepresentStatehoodSubjectSubjectName( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentMainInfoDTO.setDeclarantFullName( statehoodSubjectRepresentDeclarantFullName( statehoodSubjectRepresent ) );
        statehoodSubjectRepresentMainInfoDTO.setId( statehoodSubjectRepresent.getId() );
        statehoodSubjectRepresentMainInfoDTO.setSubjectRepresentStatus( statehoodSubjectRepresent.getSubjectRepresentStatus() );

        return statehoodSubjectRepresentMainInfoDTO;
    }

    @Override
    public List<StatehoodSubjectRepresentMainInfoDTO> toMainInfoDto(List<StatehoodSubjectRepresent> statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }

        List<StatehoodSubjectRepresentMainInfoDTO> list = new ArrayList<StatehoodSubjectRepresentMainInfoDTO>( statehoodSubjectRepresent.size() );
        for ( StatehoodSubjectRepresent statehoodSubjectRepresent1 : statehoodSubjectRepresent ) {
            list.add( toMainInfoDto( statehoodSubjectRepresent1 ) );
        }

        return list;
    }

    @Override
    public StatehoodSubjectRepresentWithNameDTO toDtoWithName(StatehoodSubjectRepresent represent) {
        if ( represent == null ) {
            return null;
        }

        StatehoodSubjectRepresentWithNameDTO statehoodSubjectRepresentWithNameDTO = new StatehoodSubjectRepresentWithNameDTO();

        statehoodSubjectRepresentWithNameDTO.setFirstName( statehoodSubjectRepresentDeclarantFirstName( represent ) );
        statehoodSubjectRepresentWithNameDTO.setLastName( statehoodSubjectRepresentDeclarantLastName( represent ) );
        statehoodSubjectRepresentWithNameDTO.setMiddleName( statehoodSubjectRepresentDeclarantMiddleName( represent ) );
        statehoodSubjectRepresentWithNameDTO.setStatehoodSubjectId( statehoodSubjectRepresentStatehoodSubjectId( represent ) );
        statehoodSubjectRepresentWithNameDTO.setDeclarantId( statehoodSubjectRepresentDeclarantId( represent ) );
        statehoodSubjectRepresentWithNameDTO.setId( represent.getId() );
        statehoodSubjectRepresentWithNameDTO.setSubjectRepresentStatus( represent.getSubjectRepresentStatus() );
        statehoodSubjectRepresentWithNameDTO.setSubjectRepresentType( represent.getSubjectRepresentType() );

        return statehoodSubjectRepresentWithNameDTO;
    }

    @Override
    public StatehoodSubjectRepresent toEntity(StatehoodSubjectRepresentDTO statehoodSubjectRepresentDTO) {
        if ( statehoodSubjectRepresentDTO == null ) {
            return null;
        }

        StatehoodSubjectRepresent statehoodSubjectRepresent = new StatehoodSubjectRepresent();

        statehoodSubjectRepresent.setStatehoodSubject( statehoodSubjectMapper.fromId( statehoodSubjectRepresentDTO.getStatehoodSubjectId() ) );
        statehoodSubjectRepresent.setUpdatedBy( statehoodSubjectRepresentDTO.getUpdatedBy() );
        statehoodSubjectRepresent.setCreatedBy( statehoodSubjectRepresentDTO.getCreatedBy() );
        statehoodSubjectRepresent.setDeletedBy( statehoodSubjectRepresentDTO.getDeletedBy() );
        statehoodSubjectRepresent.setDeclarant( userMapper.userFromId( statehoodSubjectRepresentDTO.getDeclarantId() ) );
        statehoodSubjectRepresent.setId( statehoodSubjectRepresentDTO.getId() );
        statehoodSubjectRepresent.setSubjectRepresentStatus( statehoodSubjectRepresentDTO.getSubjectRepresentStatus() );
        statehoodSubjectRepresent.setCreatedAt( statehoodSubjectRepresentDTO.getCreatedAt() );
        statehoodSubjectRepresent.setUpdatedAt( statehoodSubjectRepresentDTO.getUpdatedAt() );
        statehoodSubjectRepresent.setDeletedAt( statehoodSubjectRepresentDTO.getDeletedAt() );
        statehoodSubjectRepresent.setSubjectRepresentType( statehoodSubjectRepresentDTO.getSubjectRepresentType() );
        statehoodSubjectRepresent.setPrivilegeGroups( privilegeGroupGeneralDTOListToPrivilegeGroupSet( statehoodSubjectRepresentDTO.getPrivilegeGroups() ) );

        return statehoodSubjectRepresent;
    }

    private Long statehoodSubjectRepresentStatehoodSubjectId(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }
        StatehoodSubject statehoodSubject = statehoodSubjectRepresent.getStatehoodSubject();
        if ( statehoodSubject == null ) {
            return null;
        }
        Long id = statehoodSubject.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long statehoodSubjectRepresentDeclarantId(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }
        User declarant = statehoodSubjectRepresent.getDeclarant();
        if ( declarant == null ) {
            return null;
        }
        Long id = declarant.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String statehoodSubjectRepresentDeclarantLogin(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }
        User declarant = statehoodSubjectRepresent.getDeclarant();
        if ( declarant == null ) {
            return null;
        }
        String login = declarant.getLogin();
        if ( login == null ) {
            return null;
        }
        return login;
    }

    protected PrivilegeGroupGeneralDTO privilegeGroupToPrivilegeGroupGeneralDTO(PrivilegeGroup privilegeGroup) {
        if ( privilegeGroup == null ) {
            return null;
        }

        PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO = new PrivilegeGroupGeneralDTO();

        privilegeGroupGeneralDTO.setCode( privilegeGroup.getCode() );
        privilegeGroupGeneralDTO.setGlobal( privilegeGroup.isGlobal() );
        privilegeGroupGeneralDTO.setStatus( privilegeGroup.getStatus() );
        privilegeGroupGeneralDTO.setFullNameUkr( privilegeGroup.getFullNameUkr() );
        privilegeGroupGeneralDTO.setFullNameEng( privilegeGroup.getFullNameEng() );

        return privilegeGroupGeneralDTO;
    }

    protected List<PrivilegeGroupGeneralDTO> privilegeGroupSetToPrivilegeGroupGeneralDTOList(Set<PrivilegeGroup> set) {
        if ( set == null ) {
            return null;
        }

        List<PrivilegeGroupGeneralDTO> list = new ArrayList<PrivilegeGroupGeneralDTO>( set.size() );
        for ( PrivilegeGroup privilegeGroup : set ) {
            list.add( privilegeGroupToPrivilegeGroupGeneralDTO( privilegeGroup ) );
        }

        return list;
    }

    private String statehoodSubjectRepresentStatehoodSubjectSubjectStatus(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }
        StatehoodSubject statehoodSubject = statehoodSubjectRepresent.getStatehoodSubject();
        if ( statehoodSubject == null ) {
            return null;
        }
        String subjectStatus = statehoodSubject.getSubjectStatus();
        if ( subjectStatus == null ) {
            return null;
        }
        return subjectStatus;
    }

    private AccountDetailsStatus statehoodSubjectRepresentStatehoodSubjectAccountDetailsStatus(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }
        StatehoodSubject statehoodSubject = statehoodSubjectRepresent.getStatehoodSubject();
        if ( statehoodSubject == null ) {
            return null;
        }
        AccountDetailsStatus accountDetailsStatus = statehoodSubject.getAccountDetailsStatus();
        if ( accountDetailsStatus == null ) {
            return null;
        }
        return accountDetailsStatus;
    }

    private String statehoodSubjectRepresentStatehoodSubjectSubjectCode(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }
        StatehoodSubject statehoodSubject = statehoodSubjectRepresent.getStatehoodSubject();
        if ( statehoodSubject == null ) {
            return null;
        }
        String subjectCode = statehoodSubject.getSubjectCode();
        if ( subjectCode == null ) {
            return null;
        }
        return subjectCode;
    }

    private String statehoodSubjectRepresentDeclarantFirstName(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }
        User declarant = statehoodSubjectRepresent.getDeclarant();
        if ( declarant == null ) {
            return null;
        }
        String firstName = declarant.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String statehoodSubjectRepresentDeclarantMiddleName(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }
        User declarant = statehoodSubjectRepresent.getDeclarant();
        if ( declarant == null ) {
            return null;
        }
        String middleName = declarant.getMiddleName();
        if ( middleName == null ) {
            return null;
        }
        return middleName;
    }

    private String statehoodSubjectRepresentDeclarantLastName(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }
        User declarant = statehoodSubjectRepresent.getDeclarant();
        if ( declarant == null ) {
            return null;
        }
        String lastName = declarant.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String statehoodSubjectRepresentStatehoodSubjectSubjectName(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }
        StatehoodSubject statehoodSubject = statehoodSubjectRepresent.getStatehoodSubject();
        if ( statehoodSubject == null ) {
            return null;
        }
        String subjectName = statehoodSubject.getSubjectName();
        if ( subjectName == null ) {
            return null;
        }
        return subjectName;
    }

    private String statehoodSubjectRepresentDeclarantFullName(StatehoodSubjectRepresent statehoodSubjectRepresent) {
        if ( statehoodSubjectRepresent == null ) {
            return null;
        }
        User declarant = statehoodSubjectRepresent.getDeclarant();
        if ( declarant == null ) {
            return null;
        }
        String fullName = declarant.getFullName();
        if ( fullName == null ) {
            return null;
        }
        return fullName;
    }

    protected PrivilegeGroup privilegeGroupGeneralDTOToPrivilegeGroup(PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO) {
        if ( privilegeGroupGeneralDTO == null ) {
            return null;
        }

        PrivilegeGroup privilegeGroup = new PrivilegeGroup();

        privilegeGroup.setCode( privilegeGroupGeneralDTO.getCode() );
        privilegeGroup.setStatus( privilegeGroupGeneralDTO.getStatus() );
        privilegeGroup.setGlobal( privilegeGroupGeneralDTO.isGlobal() );
        privilegeGroup.setFullNameUkr( privilegeGroupGeneralDTO.getFullNameUkr() );
        privilegeGroup.setFullNameEng( privilegeGroupGeneralDTO.getFullNameEng() );

        return privilegeGroup;
    }

    protected Set<PrivilegeGroup> privilegeGroupGeneralDTOListToPrivilegeGroupSet(List<PrivilegeGroupGeneralDTO> list) {
        if ( list == null ) {
            return null;
        }

        Set<PrivilegeGroup> set = new HashSet<PrivilegeGroup>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( PrivilegeGroupGeneralDTO privilegeGroupGeneralDTO : list ) {
            set.add( privilegeGroupGeneralDTOToPrivilegeGroup( privilegeGroupGeneralDTO ) );
        }

        return set;
    }
}
