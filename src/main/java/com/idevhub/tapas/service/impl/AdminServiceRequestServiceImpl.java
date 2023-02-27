package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.repository.StatehoodSubjectRepository;
import com.idevhub.tapas.repository.StatehoodSubjectRepresentRepository;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.security.SecurityUtils;
import com.idevhub.tapas.service.AdminServiceRequestService;
import com.idevhub.tapas.service.UserService;
import com.idevhub.tapas.service.dto.*;
import com.idevhub.tapas.service.errors.UserAuthenticationNeedException;
import com.idevhub.tapas.service.errors.WrongActiveContextException;
import com.idevhub.tapas.service.mapper.AddressMapper;
import com.idevhub.tapas.service.mapper.AdminServiceRequestMapper;
import com.idevhub.tapas.service.utils.AddressUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceRequestServiceImpl implements AdminServiceRequestService {

    private final UserRepository userRepository;

    private final AdminServiceRequestMapper adminServiceRequestMapper;

    private final StatehoodSubjectRepresentRepository subjectRepresentRepository;

    private final StatehoodSubjectRepository subjectRepository;

    private final UserService userService;

    private final AddressMapper addressMapper;

    @Override
    public AdminServiceRequestFullRespDTO getFullRespDTO(Long declarantId, Long statehoodSubjectId) {
        Optional<User> applicantOpt = userRepository.findOneById(declarantId);

        //todo change to customize exception
        if (!applicantOpt.isPresent())
            throw new RuntimeException("User with id:" + declarantId + " NOT FOUND");

        return getResp(applicantOpt.get(), statehoodSubjectId);
    }

    @Override
    public AdminServiceRequestFullRespDTO getCurrentUserFullRespDTO(Long statehoodSubjectId) {
        User current = userService.getCurrentUser();

        return getResp(current, statehoodSubjectId);
    }

    private AdminServiceRequestFullRespDTO getResp(User declarant, Long statehoodSubjectId) {
        Optional<StatehoodSubject> statehoodSubject = subjectRepository.findById(statehoodSubjectId);

        if (!statehoodSubject.isPresent())
            throw new RuntimeException("StatehoodSubject with id:" + statehoodSubjectId + " NOT FOUND !!!");

        return adminServiceRequestMapper.toDto(declarant, statehoodSubject.get());
    }

    public Long getCurrentUserIdIfExists() {
        return SecurityUtils.getCurrentUserIdIfExists();
    }

    //TODO add checks for authGroups
    @Override
    public AdminServiceRequestBrokerageCreateDTO getASRBrokerageCreateData() {
        AdminServiceRequestBrokerageCreateDTO target = new AdminServiceRequestBrokerageCreateDTO();

        Long currentDeclarantId = getCurrentDeclarantIdOrThrowEx();

        StatehoodSubjectRepresent represent =
            subjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(currentDeclarantId)
                .orElseThrow(() -> new WrongActiveContextException("error.represent.no.active.context"));

        StatehoodSubject subject = represent.getStatehoodSubject();

        //TODO move to mapper
        target.setEdrpou(subject.getSubjectCode());
        target.setKopfg(subject.getKopfg().getCode().toString());
        target.setSubjectName(subject.getSubjectName());
        target.setHeadFullName(subject.getHeadFullName());
        target.setLegalAddress(addressMapper.toDto(subject.getLegalAddress()).getFullAddress());
        target.setKoatuu(addressMapper.toDto(subject.getLegalAddress()).getKoatuuCode());
        target.setSubjectTelNumber(subject.getTelNumber());
        target.setSubjectEmail(subject.getEmail());
        target.setStatehoodSubjectId(subject.getId());
        target.setDeclarantId(represent.getDeclarant().getId());

        String fullNameWithInitials = getFullNameWithInitials(represent.getDeclarant());
        target.setCreatedBy(fullNameWithInitials);
        target.setExecutedBy(fullNameWithInitials);

        return target;
    }

    private String getFullNameWithInitials(User declarant) {
        String nameInitial = declarant.getFirstName().substring(0, 1);
        String middleNameInitial = declarant.getMiddleName().substring(0, 1);
        String target = declarant.getLastName() + " " + nameInitial + ". " + middleNameInitial + ".";

        return target;
    }

    //TODO add checks for authGroups
    @Override
    public AdminServiceRequestWarehouseDTO getASRWarehouseCreateData() {
        AdminServiceRequestWarehouseDTO target = new AdminServiceRequestWarehouseDTO();

        Long currentDeclarantId = getCurrentDeclarantIdOrThrowEx();

        StatehoodSubjectRepresent represent =
            subjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(currentDeclarantId)
                .orElseThrow(() -> new WrongActiveContextException("error.represent.no.active.context"));

        StatehoodSubject subject = represent.getStatehoodSubject();

        //TODO move to mapper
        target.setEdrpou(subject.getSubjectCode());
        target.setSubjectFullName(subject.getSubjectName());
        target.setSubjectShortName(subject.getSubjectName());
        target.setHeadFullName(subject.getHeadFullName());
        target.setRegistrationAddress(addressMapper.toDto(subject.getLegalAddress()).getFullAddress());
        target.setStatehoodSubjectId(subject.getId());
        target.setApplicantTelNumber(subject.getTelNumber());
        target.setEmail(subject.getEmail());

        return target;
    }

    //TODO add checks for authGroups
    @Override
    public List<AdminServiceRequestBrokerageUpdateDTO> getASRBrokerageUpdateData(Set<Long> subjectsIds) {
        List<AdminServiceRequestBrokerageUpdateDTO> target = new ArrayList<>();

        Long currentDeclarantId = getCurrentDeclarantIdOrThrowEx();

        List<StatehoodSubjectRepresent> represents =
            subjectRepresentRepository.findAllByDeclarant_IdAndStatehoodSubject_IdIn(currentDeclarantId, new ArrayList<>(subjectsIds));

        represents.forEach(rep -> {
            StatehoodSubject subject = rep.getStatehoodSubject();

            //TODO move to mapper
            AdminServiceRequestBrokerageUpdateDTO outDto = new AdminServiceRequestBrokerageUpdateDTO();

            outDto.setEdrpou(subject.getSubjectCode());
            outDto.setKopfg(subject.getKopfg().getCode().toString());
            outDto.setSubjectName(subject.getSubjectName());
            outDto.setHeadFullName(subject.getHeadFullName());
            outDto.setLegalAddress(addressMapper.toDto(subject.getLegalAddress()).getFullAddress());
            outDto.setKoatuu(addressMapper.toDto(subject.getLegalAddress()).getKoatuuCode());
            outDto.setSubjectTelNumber(subject.getTelNumber());
            outDto.setSubjectEmail(subject.getEmail());
            outDto.setStatehoodSubjectId(subject.getId());
            outDto.setDeclarantId(rep.getDeclarant().getId());

            String fullNameWithInitials = getFullNameWithInitials(rep.getDeclarant());
            outDto.setCreatedBy(fullNameWithInitials);
            outDto.setExecutedBy(fullNameWithInitials);

            target.add(outDto);
        });

        return target;
    }

    public Long getCurrentDeclarantIdOrThrowEx() {
        Long currentDeclarantId = getCurrentUserIdIfExists();
        if (currentDeclarantId == null)
            throw new UserAuthenticationNeedException("error.user.authentication.needed");
        return currentDeclarantId;
    }


    //TODO add checks for authGroups
    @Override
    public List<AdminServiceRequestWarehouseDTO> getASRWarehouseUpdateData(Set<Long> subjectsIds) {
        List<AdminServiceRequestWarehouseDTO> target = new ArrayList<>();
        Long currentDeclarantId = getCurrentDeclarantIdOrThrowEx();

        List<StatehoodSubjectRepresent> represents =
            subjectRepresentRepository.findAllByDeclarant_IdAndStatehoodSubject_IdIn(currentDeclarantId, new ArrayList<>(subjectsIds));

        represents.forEach(rep -> {
            StatehoodSubject subject = rep.getStatehoodSubject();

            //TODO move to mapper
            AdminServiceRequestWarehouseDTO outDto = new AdminServiceRequestWarehouseDTO();

            outDto.setEdrpou(subject.getSubjectCode());
            outDto.setSubjectFullName(subject.getSubjectName());
            outDto.setSubjectShortName(subject.getSubjectName());
            outDto.setHeadFullName(subject.getHeadFullName());
            outDto.setRegistrationAddress(addressMapper.toDto(subject.getLegalAddress()).getFullAddress());
            outDto.setStatehoodSubjectId(subject.getId());
            outDto.setApplicantTelNumber(subject.getTelNumber());
            outDto.setEmail(subject.getEmail());

            String fullNameWithInitials = getFullNameWithInitials(rep.getDeclarant());

            target.add(outDto);
        });

        return target;
    }

    @Override
    public DataToApproveRequestDTO getDataToApproveRequest() {
        Long currentDeclarantId = getCurrentDeclarantIdOrThrowEx();

        StatehoodSubjectRepresent represent =
            subjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(currentDeclarantId)
                .orElseThrow(() -> new WrongActiveContextException("error.represent.no.active.context"));

        DataToApproveRequestDTO target = new DataToApproveRequestDTO();

        target.setEdrpouStr(represent.getStatehoodSubject().getSubjectCode());
        target.setRnokppStr(represent.getDeclarant().getRnokpp());

        return target;
    }

    @Override
    public DataToAppointRequestDTO getDataToAppointRequest(Long executantId) {
        DataToAppointRequestDTO target = new DataToAppointRequestDTO();

        User initiator = userService.getCurrentUser();
        UserDTO executant = userService.getUserById(executantId);
        List<String> initiatorPrivileges = userService.findAllPrivilegesByCurrentLoggedInUser();

        target.setExecutantOrg(executant.getOrg());
        target.setInitiatorOrg(initiator.getOrg());
        target.setExecutantFullName(executant.getFullName());
        target.setInitiatorPrivileges(initiatorPrivileges);

        return target;
    }

    @Override
    public Long getOrgHeadId() {
        Long orgHeadId = userService.getOrgHeadId();

        return orgHeadId;
    }
}
