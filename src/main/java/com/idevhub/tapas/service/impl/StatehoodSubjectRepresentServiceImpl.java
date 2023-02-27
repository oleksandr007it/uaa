package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.PrivilegeGroup;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.PropertyStatus;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import com.idevhub.tapas.repository.StatehoodSubjectRepresentRepository;
import com.idevhub.tapas.security.SecurityUtils;
import com.idevhub.tapas.service.EntityNotFoundException;
import com.idevhub.tapas.service.*;
import com.idevhub.tapas.service.criteria.RepresentCriteria;
import com.idevhub.tapas.service.criteria.RepresentSpecificationHelper;
import com.idevhub.tapas.service.dto.*;
import com.idevhub.tapas.service.errors.*;
import com.idevhub.tapas.service.mapper.StatehoodSubjectRepresentMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

/**
 * Service Implementation for managing StatehoodSubjectRepresent.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class StatehoodSubjectRepresentServiceImpl implements StatehoodSubjectRepresentService {

    private final Logger log = LoggerFactory.getLogger(StatehoodSubjectRepresentServiceImpl.class);

    private final StatehoodSubjectRepresentRepository statehoodSubjectRepresentRepository;
    private final StatehoodSubjectRepresentMapper statehoodSubjectRepresentMapper;
    private final UserService userService;
    private final StatehoodSubjectRepresentLookupService statehoodSubjectRepresentLookupService;
    private final RepresentSpecificationHelper representSpecificationHelper;
    private final MailService mailService;
    private final PrivilegeForRepresentService privilegeForRepresentService;

    @Override
    public StatehoodSubjectRepresentDTO create(StatehoodSubjectRepresentCreateDTO input) {
        log.debug("Request to save StatehoodSubjectRepresent : {}", input);

        StatehoodSubjectRepresent initiator = statehoodSubjectRepresentLookupService.tryToGetActiveRepresent(SecurityUtils.getCurrentUserIdIfExists());

        User candidate = userService
            .getActiveUserByRnokppAndNames(input.getRnokpp(), input.getFirstName(), input.getLastName(), input.getMiddleName());

        checkRepresentativePossibility(initiator.getStatehoodSubject(), candidate);

        StatehoodSubjectRepresent represent = createRepresenterAndSave(CreateRepresenterDTO.builder()
            .declarant(candidate).subject(initiator.getStatehoodSubject())
            .subjectRepresentStatus(StatehoodSubjectRepresentStatus.INACTIVE).subjectRepresentType(StatehoodSubjectRepresentType.OTHER_EMPLOYEE).build());
        sendInvitation(initiator.getStatehoodSubject(), candidate, initiator.getDeclarant().getFullName());

        return statehoodSubjectRepresentMapper.toDto(represent);
    }


    @Override
    public StatehoodSubjectRepresentDTO update(StatehoodSubjectRepresentUpdateDTO inputDto) {
        log.debug("Request to save StatehoodSubjectRepresent : {}", inputDto);

        if (inputDto.getPrivilegeGroupsIds() == null || inputDto.getPrivilegeGroupsIds().size() <= 0)
            throw new StatehoodSubjectAuthGroupGeneralException("error.auth.minimum.one.needed");

        StatehoodSubjectRepresent represent = statehoodSubjectRepresentRepository.findByRepresentId(inputDto.getRepresenterId()).orElseThrow(() -> new EntityNotFoundException(StatehoodSubjectRepresent.class));
        if (represent.getSubjectRepresentStatus() != StatehoodSubjectRepresentStatus.ACTIVE)
            throw new WrongRepresentStatusException("error.represent.wrong.status");

        Set<PrivilegeGroup> privilegeGroupsFromRequest = privilegeForRepresentService.convertStringListToPersistPrivilegeGroupSet(inputDto.getPrivilegeGroupsIds());
        represent.setPrivilegeGroups(privilegeGroupsFromRequest);

        represent.setUpdatedAt(Instant.now());
        represent.setUpdatedBy(SecurityUtils.getCurrentUserIdIfExists().toString());

        represent = statehoodSubjectRepresentRepository.save(represent);
        return statehoodSubjectRepresentMapper.toDto(represent);
    }

    /**
     * Get one statehoodSubjectRepresent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StatehoodSubjectRepresentWithNameDTO findOne(Long id) {
        log.debug("Request to get StatehoodSubjectRepresent : {}", id);

        StatehoodSubjectRepresent represent =
            statehoodSubjectRepresentRepository.findByRepresentId(id)
                .orElseThrow(() -> new RepresentNotFoundException("error.represent.by.id.not.found"));

        statehoodSubjectRepresentLookupService.tryToGetActiveRepresent(SecurityUtils.getCurrentUserIdIfExists(), represent.getStatehoodSubject().getId());

        return statehoodSubjectRepresentMapper.toDtoWithName(represent);
    }

    @Override
    @Transactional(readOnly = true)
    public StatehoodSubjectRepresentDTO getCurrentRepresentDto() {

        log.debug("Trying to get current StatehoodSubjectRepresent.");

        var currentUserId = SecurityUtils.getCurrentUserIdIfExists();

        var represent = statehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(currentUserId)
            .orElseThrow(() -> new RuntimeException("Declarant " + currentUserId + " is not a representative of any legal entities"));

        if (!statehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(currentUserId)
            .orElseThrow(() -> new RuntimeException("Declarant " + currentUserId + " is not a representative of any legal entities")).getStatehoodSubject().getSubjectStatus().equals(UserStatus.ACTIVE))
            throw new RuntimeException("Legal entity is not in active status!");

        StatehoodSubjectRepresentDTO target = statehoodSubjectRepresentMapper.toDto(represent);

        return target;
    }

    @Override
    public List<StatehoodSubjectRepresentMainInfoDTO> getAllActiveByDeclarantId(Long id) {
        List<StatehoodSubjectRepresent> representList =
            statehoodSubjectRepresentRepository.findAllByDeclarant_IdAndSubjectRepresentStatus(id, StatehoodSubjectRepresentStatus.ACTIVE);

        List<StatehoodSubjectRepresentMainInfoDTO> targetList = new ArrayList<>();

        targetList.add(getSelfRepresentDTO());
        targetList.addAll(statehoodSubjectRepresentMapper.toMainInfoDto(representList));

        return targetList;
    }

    private StatehoodSubjectRepresentMainInfoDTO getSelfRepresentDTO() {
        StatehoodSubjectRepresentMainInfoDTO self = new StatehoodSubjectRepresentMainInfoDTO();

        User currentUser = userService.getCurrentUser();

        self.setStatehoodSubjectFullName(currentUser.getFullName());

        return self;
    }

    @Override
    public Boolean hasAccessByIds(Long declarantId, Long subjectId) {
        return statehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(declarantId, subjectId)
            .filter(represent -> StatehoodSubjectRepresentStatus.DELETED != represent.getSubjectRepresentStatus())
            .isPresent();
    }

    @Override
    public Optional<StatehoodSubjectRepresent> findByDeclarantIdAndStatehoodSubjectId(Long currentUserId, Long id) {
        return statehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(currentUserId, id);
    }

    @Override
    public void changeContextTo(Long statehoodSubjectId) {
        Long declarantId = SecurityUtils.getCurrentUserIdIfExists();

        Optional<StatehoodSubjectRepresent> representOpt = statehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(declarantId);

        if (representOpt.isPresent()) {
            StatehoodSubjectRepresent represent = representOpt.get();

            if (!represent.getStatehoodSubject().getId().equals(statehoodSubjectId)) {
                represent.setCurrentContext(false);
                statehoodSubjectRepresentRepository.save(represent);

                findAndSetContextActive(declarantId, statehoodSubjectId);
            }
        } else {
            findAndSetContextActive(declarantId, statehoodSubjectId);
        }
    }

    private void findAndSetContextActive(Long declarantId, Long statehoodSubjectId) {
        if (statehoodSubjectId == null) return;

        Optional<StatehoodSubjectRepresent> targetOpt =
            statehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(declarantId, statehoodSubjectId);

        if (!targetOpt.isPresent())
            throw new RuntimeException("Represent with ID " + declarantId +
                " hasn't access to statehood subject with ID " + statehoodSubjectId);

        targetOpt.get().setCurrentContext(true);

        statehoodSubjectRepresentRepository.save(targetOpt.get());
    }

    @Override
    public Page<StatehoodSubjectRepresentMainInfoDTO> findAllRepresentersBySubjectId(Pageable pageable, RepresentCriteria criteria) {

        StatehoodSubjectRepresent currentdeclarant =
            statehoodSubjectRepresentLookupService.tryToGetActiveRepresent(SecurityUtils.getCurrentUserIdIfExists(), criteria.getSubjectId().getEquals());

        Specification<StatehoodSubjectRepresent> specification =
            representSpecificationHelper.createSpecification(criteria);

        userService.clearUserCaches(currentdeclarant.getDeclarant());

        Page<StatehoodSubjectRepresentMainInfoDTO> target =
            statehoodSubjectRepresentRepository.findAll(specification, pageable)
                .map(statehoodSubjectRepresentMapper::toMainInfoDto);

        return target;
    }

    @Override
    public StatehoodSubjectRepresentContextDTO getCurrentContextMainDTO() {
        Long currentUserId = SecurityUtils.getCurrentUserIdIfExists();

        Optional<StatehoodSubjectRepresent> subjectRepresentOptional =
            statehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(currentUserId);

        if (!subjectRepresentOptional.isPresent()) {
            StatehoodSubjectRepresentContextDTO selfRepresent = new StatehoodSubjectRepresentContextDTO();

            User user = userService.getCurrentUser();

            selfRepresent.setDeclarantId(user.getId());
            selfRepresent.setSubjectName(user.getFullName());

            return selfRepresent;
        }

        Optional<StatehoodSubject> subjectOptional = subjectRepresentOptional
            .map(StatehoodSubjectRepresent::getStatehoodSubject);

        return subjectOptional
            .map(subj ->
                new StatehoodSubjectRepresentContextDTO(subj.getSubjectName(), subj.getSubjectCode(), subj.getId(), currentUserId))
            .orElse(new StatehoodSubjectRepresentContextDTO());
    }

    @Override
    public StatehoodSubjectRepresentDTO removeRepresent(Long declarantId, Long subjectId) {
        log.debug("REST request to delete Represent where declarantId: {} and subjectId: {}", declarantId, subjectId);

        Long currentDeclarantId = SecurityUtils.getCurrentUserIdIfExists();

        if (currentDeclarantId == null)
            throw new UserAuthenticationNeedException("error.user.authentication.needed");

        StatehoodSubjectRepresent candidateToDelete =
            statehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(declarantId, subjectId)
                .orElseThrow(() -> new RepresentNotFoundException("error.represent.by.declarant.id.and.subject.id.not.found",
                    declarantId.toString(), subjectId.toString()));

        if (!currentDeclarantId.equals(candidateToDelete.getDeclarant().getId())) {
            statehoodSubjectRepresentLookupService.tryToGetActiveRepresent(SecurityUtils.getCurrentUserIdIfExists(), candidateToDelete.getStatehoodSubject().getId());

            if (candidateToDelete.getStatehoodSubject().getSubjectStatus().equals(UserStatus.DELETED))
                throw new WrongStatehoodSubjectStatusException("error.statehood.subject.wrong.status");

            if (candidateToDelete.getSubjectRepresentStatus().equals(StatehoodSubjectRepresentStatus.DELETED))
                throw new WrongRepresentStatusException("error.represent.status.deleted");
        } else {
            statehoodSubjectRepresentLookupService.tryToGetActiveRepresent(SecurityUtils.getCurrentUserIdIfExists(), candidateToDelete.getStatehoodSubject().getId());
        }

        String currentDeclarantLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new UserAuthenticationNeedException("error.user.authentication.needed"));
        Instant now = Instant.now();

        candidateToDelete.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.DELETED);
        candidateToDelete.setUpdatedAt(now);
        candidateToDelete.setUpdatedBy(currentDeclarantLogin);
        candidateToDelete.setDeletedAt(now);
        candidateToDelete.setDeletedBy(currentDeclarantLogin);

        candidateToDelete = statehoodSubjectRepresentRepository.save(candidateToDelete);

        return statehoodSubjectRepresentMapper.toDto(candidateToDelete);
    }

    @Override
    public List<StatehoodSubjectRepresentMainInfoDTO> getAllActiveByCurrentDeclarant() {
        User declarant = userService.getCurrentUser();

        List<StatehoodSubjectRepresentMainInfoDTO> target =
            getAllActiveByDeclarantId(declarant.getId());

        return target;
    }


    @Override
    public StatehoodSubjectRepresent createRepresenterAndSave(CreateRepresenterDTO createRepresenterDTO) {
        StatehoodSubjectRepresent statehoodSubjectRepresent = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent.setSubjectRepresentStatus(createRepresenterDTO.getSubjectRepresentStatus());
        statehoodSubjectRepresent.setCreatedAt(Instant.now());
        Long currentId = SecurityUtils.getCurrentUserIdIfExists();
        statehoodSubjectRepresent.setCreatedBy(currentId.toString());
        PrivilegeGroup privilegeGroup = privilegeForRepresentService.getGlobalGroupByName();
        Set<PrivilegeGroup> privilegeGroups = new HashSet<>();
        privilegeGroups.add(privilegeGroup);
        statehoodSubjectRepresent.setPrivilegeGroups(privilegeGroups);
        statehoodSubjectRepresent.setStatehoodSubject(createRepresenterDTO.getSubject());
        statehoodSubjectRepresent.setDeclarant(createRepresenterDTO.getDeclarant());
        statehoodSubjectRepresent.setSubjectRepresentType(createRepresenterDTO.getSubjectRepresentType());
        if (createRepresenterDTO.getApproveSignBase64() != null) {
            statehoodSubjectRepresent.setApproveSignBase64(createRepresenterDTO.getApproveSignBase64());
        }
        statehoodSubjectRepresent.setCurrentContext(false);
        statehoodSubjectRepresent = statehoodSubjectRepresentRepository.save(statehoodSubjectRepresent);

        return statehoodSubjectRepresent;
    }

    private void checkRepresentativePossibility(StatehoodSubject subject, User candidate) {
        log.debug("Trying to check {} to representative possibility", candidate.getFullName());

        if (!candidate.getPropertyStatus().equals(PropertyStatus.CONFIRMED))
            throw new UserPropertyStatusException("error.user.invalid.property.status");

        if (hasAccessByIds(candidate.getId(), subject.getId()))
            throw new UserAlreadyRepresentativeException("error.user.already.representative");

        log.debug("{} can be a representative of {}.", candidate.getFullName(), subject.getSubjectName());
    }

    private void sendInvitation(StatehoodSubject subject, User toInvite, String invitedByFullName) {

        log.debug("Sending representative invitation. Candidate - {}. Subject - {}. Invited by - {}", toInvite.getFullName(), subject.getSubjectName(), invitedByFullName);
        RepresentativeInvitation invitation = RepresentativeInvitation.builder()
            .userToInviteFullName(toInvite.getFullName())
            .userToInviteEmail(toInvite.getEmail())
            .invitedByFullName(invitedByFullName)
            .subjectCode(subject.getSubjectCode())
            .subjectShortName(subject.getSubjectShortName())
            .langKey(toInvite.getLangKey())
            .build();

        String htmlTemplateName = "representativeInvitationEmail";

        mailService.sendStatehoodSubjectRepresentInvitationEmail(invitation, htmlTemplateName);
        log.debug("Invitation send.");
    }
}
