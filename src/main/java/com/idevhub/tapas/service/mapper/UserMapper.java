package com.idevhub.tapas.service.mapper;

import com.idevhub.tapas.domain.Authority;
import com.idevhub.tapas.domain.CEADepartment;
import com.idevhub.tapas.domain.CentralExecutiveAuthority;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 * <p>
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
@RequiredArgsConstructor
public class UserMapper {

    private final AddressMapper addressMapper;
    private final CEADepartmentMapper ceaDepartmentMapper;
    private final CentralExecutiveAuthorityMapper ceaMapper;

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public UserDTO userToUserDTO(User user) {
        UserDTO targetDto = new UserDTO()
            .setId(user.getId())
            .setLogin(user.getLogin())
            .setFirstName(user.getFirstName())
            .setLastName(user.getLastName())
            .setEmail(user.getEmail())
            .setActivated(user.isActivated())
            .setLangKey(user.getLangKey())
            .setUserType(user.getUserType())
            .setMiddleName(user.getMiddleName())
            .setFullName(user.getFullName())
            .setStatus(user.getStatus())
            .setRnokpp(user.getRnokpp())
            .setEdrpouCode(user.getEdrpouCode())
            .setOrg(user.getOrg())
            .setOrgUnit(user.getOrgUnit())
            .setPosition(user.getPosition())
            .setPropertyStatus(user.getPropertyStatus())
            .setPhoneNumber(user.getPhoneNumber())
            .setPasportSn(user.getPasportSn())
            .setPasportIssuedBy(user.getPasportIssuedBy())
            .setPasportDate(user.getPasportDate())
            .setEmailApprove(user.getEmailApprove())
            .setCreatedBy(user.getCreatedBy())
            .setCreatedDate(user.getCreatedDate())
            .setLastModifiedBy(user.getLastModifiedBy())
//            .setLegalAddress(user.getLegalAddress() == null ? null : addressMapper.toDto(user.getLegalAddress()))
            .setLastModifiedDate(user.getLastModifiedDate())

            .setAuthorities(user.getAuthorities() == null ? null : user.getAuthorities().stream()
                .map(Authority::getName)
                .collect(Collectors.toSet()))

            .setCeaDepartment(user.getCeaDepartment() == null ? null : ceaDepartmentMapper.toDto(user.getCeaDepartment()))
            .setCentralExecutiveAuthority(user.getCentralExecutiveAuthority() == null ? null : ceaMapper.toDto(user.getCentralExecutiveAuthority()));

        return targetDto;
    }

    public ShortUserDTO userToShortUserDTO(User user) {
        return ShortUserDTO.builder()
            .id(user.getId())
            .rnokpp(user.getRnokpp())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .middleName(user.getMiddleName())
            .activated(user.isActivated())
            .status(user.getStatus())
            .propertyStatus(user.getPropertyStatus())
            .email(user.getEmail())
            .emailApprove(user.getEmailApprove())
            .phoneNumber(user.getPhoneNumber())
            .pasportSn(user.getPasportSn())
            .pasportIssuedBy(user.getPasportIssuedBy())
            .pasportDate(user.getPasportDate())
            .legalAddress(addressMapper.toDto(user.getLegalAddress()))
            .build();
    }

    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            user.setUserType(userDTO.getUserType());
            user.setMiddleName(userDTO.getMiddleName());
            user.setFullName(userDTO.getFullName());
            user.setStatus(userDTO.getStatus());
            user.setRnokpp(userDTO.getRnokpp());
            user.setEdrpouCode(userDTO.getEdrpouCode());
            user.setOrg(userDTO.getOrg());
            user.setOrgUnit(userDTO.getOrgUnit());
            user.setPosition(userDTO.getPosition());
            user.setPropertyStatus(userDTO.getPropertyStatus());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setPasportSn(userDTO.getPasportSn());
            user.setPasportIssuedBy(userDTO.getPasportIssuedBy());
            user.setPasportDate(userDTO.getPasportDate());
            user.setEmailApprove(userDTO.getEmailApprove());
            if (userDTO.getLegalAddress() != null)
                user.setLegalAddress(this.setAddress(userDTO.getLegalAddress().getId()));
            Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            user.setAuthorities(authorities);
            user.setCeaDepartment(userDTO.getCeaDepartment() == null ? null : ceaDepartmentMapper.toEntity(userDTO.getCeaDepartment()));
            user.setCentralExecutiveAuthority(userDTO.getCentralExecutiveAuthority() == null ? null : ceaMapper.toEntity(userDTO.getCentralExecutiveAuthority()));

            return user;
        }
    }

    private Address setAddress(String id) {
        Address address = new Address();
        address.setId(id);
        return address;
    }

    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities = authoritiesAsString.stream().map(string -> {
                Authority auth = new Authority();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        }

        return authorities;
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    public CEAEmployeeFindAllDTO toCEAEmployeeDto(User user) {
        CEAEmployeeFindAllDTO target = new CEAEmployeeFindAllDTO();

        target.setId(user.getId());
        target.setFirstName(user.getFirstName());
        target.setLastName(user.getLastName());
        target.setMiddleName(user.getMiddleName());
        target.setFullName(user.getFullName());
        target.setOrgUnit(user.getOrgUnit());
        target.setPosition(user.getPosition());

        if (user.getCentralExecutiveAuthority() != null)
            target.setCentralExecutiveAuthorityDTO(ceaMapper.toDto(user.getCentralExecutiveAuthority()));

        return target;
    }

    public CEAEmployeeContextDTO toCeaEmployeeContextDto(User user) {
        CEAEmployeeContextDTO target = new CEAEmployeeContextDTO();

        target.setUserId(user.getId());
        target.setFirstName(user.getFirstName());
        target.setLastName(user.getLastName());
        target.setMiddleName(user.getMiddleName());
        target.setFullName(user.getFullName());
        target.setPosition(user.getPosition());
        target.setPositionType(user.getPositionType());

        if (user.getCentralExecutiveAuthority() != null)
            target.setCeaCode(user.getCentralExecutiveAuthority().getCode());

        if (user.getCeaDepartment() != null)
            target.setCeaDepartmentId(user.getCeaDepartment().getId());

        return target;
    }

    public CeaEmployeeMainInfoDTO toCeaEmployeeMainInfoDto(User ceaUser) {
        CeaEmployeeMainInfoDTO target =
            CeaEmployeeMainInfoDTO.builder()
                .id(ceaUser.getId())
                .firstName(ceaUser.getFirstName())
                .lastName(ceaUser.getLastName())
                .middleName(ceaUser.getMiddleName())
                .rnokpp(ceaUser.getRnokpp())
                .email(ceaUser.getEmail())
                .phoneNumber(ceaUser.getPhoneNumber())
                .orgUnit(ceaUser.getOrgUnit())
                .position(ceaUser.getPosition())
                .build();

        return target;
    }

    public CEAEmployeeDTO toCeaEmployeeDto(User ceaUser) {
        CentralExecutiveAuthority cea = ceaUser.getCentralExecutiveAuthority();
        CEADepartment department = ceaUser.getCeaDepartment();
        return CEAEmployeeDTO.builder()
            .id(ceaUser.getId())
            .firstName(ceaUser.getFirstName())
            .lastName(ceaUser.getLastName())
            .middleName(ceaUser.getMiddleName())
            .rnokpp(ceaUser.getRnokpp())
            .email(ceaUser.getEmail())
            .phoneNumber(ceaUser.getPhoneNumber())
            .edrpouCode(cea.getCode())
            .departmentId(department == null ? null : department.getId())
            .departmentFullName(department == null ? null : department.getFullNameUkr())
            .position(ceaUser.getPosition())
            .positionType(ceaUser.getPositionType())
            .build();
    }
}
