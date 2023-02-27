package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.*;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import com.idevhub.tapas.repository.AddressRepository;
import com.idevhub.tapas.repository.KopfgRepository;
import com.idevhub.tapas.repository.StatehoodSubjectRepository;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.security.SecurityUtils;
import com.idevhub.tapas.service.*;
import com.idevhub.tapas.service.criteria.StatehoodSubjectCriteria;
import com.idevhub.tapas.service.criteria.StatehoodSubjectSpecificationHelper;
import com.idevhub.tapas.service.dto.*;
import com.idevhub.tapas.service.errors.UserNotFoundException;
import com.idevhub.tapas.service.feign.IntegrationEDRClient;
import com.idevhub.tapas.service.feign.RemoteCryptographyServiceClient;
import com.idevhub.tapas.service.mapper.AddressMapper;
import com.idevhub.tapas.service.mapper.StatehoodSubjectMapper;
import com.idevhub.tapas.service.utils.CompareUtils;
import com.idevhub.tapas.service.utils.CryptoUtils;
import com.idevhub.tapas.service.utils.StringTransformUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.idevhub.dto.SignerInfo;

import java.math.BigInteger;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.idevhub.tapas.domain.enumeration.ActiveStatus.ACTIVE;

/**
 * Service Implementation for managing StatehoodSubject.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class StatehoodSubjectServiceImpl implements StatehoodSubjectService {

    private final Logger log = LoggerFactory.getLogger(StatehoodSubjectServiceImpl.class);

    private final StatehoodSubjectRepository statehoodSubjectRepository;
    private final StatehoodSubjectMapper statehoodSubjectMapper;
    private final EmailConfirmationService emailConfirmationService;
    private final StatehoodSubjectRepresentService statehoodSubjectRepresentService;
    private final MailService mailService;
    private final StatehoodSubjectSpecificationHelper specificationHelper;
    private final StatehoodSubjectRepresentLookupService statehoodSubjectRepresentLookupService;
    private final AddressRepository addressRepository;
    private final RemoteCryptographyServiceClient remoteCryptographyServiceClient;
    private final UserRepository userRepository;
    private final IntegrationEDRClient integrationEDRClient;
    private final KopfgRepository kopfgRepository;
    private final AddressMapper addressMapper;
    private final PrivilegeForSubjectFindService privilegeGroupsService;

    /**
     * Save a statehoodSubject.
     *
     * @param inputDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StatehoodSubjectDTO save(StatehoodSubjectCreateDTO inputDTO) {
        log.debug("Request to save StatehoodSubject : {}", inputDTO);

        StatehoodSubject statehoodSubject = statehoodSubjectMapper.toEntity(inputDTO);
        AddressSaveDTO addressSaveDTO = getAddress(inputDTO.getLegalAddressId(), inputDTO.getActualAddressId(), inputDTO.getIsActualSameAsLegalAddress());

        statehoodSubject.setActualAddress(addressSaveDTO.getActual());
        statehoodSubject.setLegalAddress(addressSaveDTO.getLegal());

        statehoodSubject.setCreatedAt(Instant.now());
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL);
        statehoodSubject.setEmailApproved(false);

        statehoodSubject = statehoodSubjectRepository.save(statehoodSubject);

        return statehoodSubjectMapper.toDto(statehoodSubject);
    }

    @Data
    @AllArgsConstructor
    private class AddressSaveDTO {
        Address actual;
        Address legal;
    }

    private AddressSaveDTO getAddress(String legalAddressId, String actualAddressId, Boolean isActualSameAsLegalAddress) {
        Address actual;
        Address legal =
            addressRepository.findById(legalAddressId)
                .orElseThrow(() -> new RuntimeException("Address with id: " +
                    legalAddressId + " not found!"));
        if (isActualSameAsLegalAddress)
            actual = legal;
        else
            actual = addressRepository.findById(actualAddressId)
                .orElseThrow(() -> new RuntimeException("Address with id: " +
                    actualAddressId + " not found!"));

        AddressSaveDTO addressSaveDTO = new AddressSaveDTO(actual, legal);
        return addressSaveDTO;
    }

    @Override
    public StatehoodSubjectDTO update(StatehoodSubjectDTO inputDTO) {
        log.debug("Request to update StatehoodSubject : {}", inputDTO);

        Long id = inputDTO.getId();
        Long currentUserId = SecurityUtils.getCurrentUserIdIfExists();
        StatehoodSubject statehoodSubject = statehoodSubjectRepository
            .findById(id).orElseThrow(() -> new EntityNotFoundException(StatehoodSubject.class, id));

        boolean isActualSameAsLegalAddress = inputDTO.getActualSameAsLegalAddress();
        if (isActualSameAsLegalAddress) {
            statehoodSubject.setActualAddress(null);
        } else {
            statehoodSubject.setActualAddress(addressMapper.mapOrUpdateAddress(inputDTO.getActualAddress(), statehoodSubject.getActualAddress()));
        }
        statehoodSubject.setActualSameAsLegalAddress(isActualSameAsLegalAddress);

        if (inputDTO.getTelNumber() != null)
            statehoodSubject.setTelNumber(inputDTO.getTelNumber());
        if (inputDTO.getEori() != null)
            statehoodSubject.setEori(inputDTO.getEori());
        if (inputDTO.getEmail() != null && !inputDTO.getEmail().equals(statehoodSubject.getEmail())) {
            statehoodSubject.setEmail(inputDTO.getEmail());
            statehoodSubject.setEmailApproved(false);
            statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.FULL_NOT_CONFIRMED);
        }
        statehoodSubject.setUpdatedAt(Instant.now());
        statehoodSubject.setUpdatedBy(currentUserId.toString());

        return statehoodSubjectMapper.toDto(statehoodSubject);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatehoodSubjectDTO> findAll(Pageable pageable,
                                             StatehoodSubjectCriteria criteria) {
        log.debug("Request to get all StatehoodSubjects");

        Specification<StatehoodSubject> specification =
            specificationHelper.createSpecification(criteria);

        return statehoodSubjectRepository.findAll(specification, pageable)
            .map(statehoodSubjectMapper::toDto);
    }


    /**
     * Get one statehoodSubject by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StatehoodSubjectDTO findOne(Long id) {
        log.debug("Request to get StatehoodSubject : {}", id);

        Long currentUserId = SecurityUtils.getCurrentUserIdIfExists();

        StatehoodSubjectRepresent represent =
            statehoodSubjectRepresentService.findByDeclarantIdAndStatehoodSubjectId(currentUserId, id)
                .orElseThrow(() -> {
                    if (statehoodSubjectRepository.findByIdAndSubjectStatusNot(id, UserStatus.DELETED).isEmpty())
                        return new RuntimeException("StatehoodSubject with id:" + id + " NOT FOUND!!!");

                    return new RuntimeException("You don't have rights to get any info about subjects with id:" + id);
                });

        return statehoodSubjectMapper.toDto(represent.getStatehoodSubject());
    }


    @Override
    @Transactional(readOnly = true)
    public StatehoodSubject findOneById(Long id) {
        log.debug("Request to get StatehoodSubject by ID : {}", id);

        return statehoodSubjectRepository.findById(id).orElseThrow(() -> new RuntimeException("StatehoodSubject with id:" + id + " NOT FOUND!!!"));

    }

    @Override
    public StatehoodSubjectWithPrivilegeGroupsDTO getCurrentSubjectByRepresentativeWithPrivilegeGroups() {

        log.debug("Trying to get current subject by active context...");

        Long currentUserId = SecurityUtils.getCurrentUserIdIfExists();

        StatehoodSubjectRepresent subjectRepresent = statehoodSubjectRepresentLookupService.tryToGetActiveRepresent(currentUserId);

        StatehoodSubject currentSubject = subjectRepresent.getStatehoodSubject();

        List<PrivilegeGroupGeneralDTO> authGroups = privilegeGroupsService.getGroupsForSubject(currentSubject.getId()).stream()
            .filter(group -> group.getStatus() == ACTIVE)
            .collect(Collectors.toList());

        StatehoodSubjectWithPrivilegeGroupsDTO subjectDTO = new StatehoodSubjectWithPrivilegeGroupsDTO();
        subjectDTO.setCode(currentSubject.getSubjectCode());
        subjectDTO.setShortName(currentSubject.getSubjectShortName());
        subjectDTO.setPrivilegeGroups(authGroups);

        return subjectDTO;
    }

    /**
     * Delete the statehoodSubject by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatehoodSubject : {}", id);
        Optional<StatehoodSubject> target = statehoodSubjectRepository.findById(id);

        //todo change to customize exception
        if (target.isEmpty())
            throw new RuntimeException("Statehood Subject with id : " + id + " NOT FOUND!!!");

        Optional<String> currentUserLoginOpt = SecurityUtils.getCurrentUserLogin();

        //todo change to customize exception
        if (currentUserLoginOpt.isEmpty())
            throw new RuntimeException("You need to be logged in!!!");

        target.get().setDeletedAt(Instant.now());
        target.get().setDeletedBy(currentUserLoginOpt.get());

        statehoodSubjectRepository.save(target.get());
    }

    @Override
    public void sendSubjectConfirmationEmail(StatehoodSubjectSendConfirmEmailDTO inputData) {

        StatehoodSubjectRepresent represent =
            statehoodSubjectRepresentLookupService.tryToGetActiveRepresent(SecurityUtils.getCurrentUserIdIfExists(), inputData.getStatehoodSubjectId());

        String htmlTemplateName = "statehoodSubjectEmailConfimEmail";

        StatehoodSubject statehoodSubject = represent.getStatehoodSubject();

        EmailConfirmation confirmation =
            emailConfirmationService.save(represent.getDeclarant(), htmlTemplateName, inputData.getEmail(), statehoodSubject);

        mailService.sendStatehoodSubjectEmailConfirmationEmail(confirmation, htmlTemplateName, statehoodSubject.getSubjectName());
    }


    private static final String EDR_NOT_ACTIVE = "StatehoodSubject validation Error not found  active StatehoodSubject from  EDR";
    private static final String NOT_FOUND_DETAILS = "Not found details  about statehoodSubject";


    @Override
    @SneakyThrows
    public SubjectInfoForAdd getActiveSubjectsByCodeOrPassport(String codeOrPassport) {
        Optional<StatehoodSubject> optionalStatehoodSubject = statehoodSubjectRepository.findBySubjectCodeAndSubjectStatusNot(codeOrPassport, UserStatus.DELETED);
        SubjectInfoForAdd subjectInfoForAdd = new SubjectInfoForAdd();


        if (optionalStatehoodSubject.isPresent()) {
            StatehoodSubject statehoodSubject = optionalStatehoodSubject.get();
            if (validationAndSynchroniseInternalStatehoodSubject(statehoodSubject)) {
                subjectInfoForAdd.setSubjectName(statehoodSubject.getSubjectName());
                subjectInfoForAdd.setSubjectCode(statehoodSubject.getSubjectCode());
                subjectInfoForAdd.setInternal(true);
            }
        } else {
            try {
                Optional<SubjectMainInfo> optionalSubjectMainInfo = integrationEDRClient.getActiveSubjectInfoByCode(codeOrPassport);
                if (optionalSubjectMainInfo.isPresent()) {
                    subjectInfoForAdd.setSubjectName(optionalSubjectMainInfo.get().getName());
                    subjectInfoForAdd.setSubjectCode(optionalSubjectMainInfo.get().getCode());
                    subjectInfoForAdd.setInternal(false);
                } else {
                    log.error(EDR_NOT_ACTIVE);
                    throw new RuntimeException(EDR_NOT_ACTIVE);
                }
            } catch (Exception e) {
                log.error("Active statehoodSubject with code: {}  NOT FOUND!!!", codeOrPassport);
                throw new RuntimeException("Active statehoodSubject with code: " + codeOrPassport + " NOT FOUND!!!");
            }

        }

        String signForApproveBase64 = CryptoUtils.makeBase64ForSign(subjectInfoForAdd.getSubjectCode(), subjectInfoForAdd.getSubjectName(), "confirmLegalEntityRepresentation");
        subjectInfoForAdd.setRawDataBase64(signForApproveBase64);
        return subjectInfoForAdd;
    }

    public Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserIdIfExists();
    }

    @Override
    public Long createStatehoodSubjectAndAddRepreseter(SubjectInfoForAdd subjectInfoForAdd) {


        String edrpouStatehoodSubject = subjectInfoForAdd.getSubjectCode();
        SignerInfo signerInfo = CryptoUtils.verifyByData(subjectInfoForAdd.getRawDataBase64(), subjectInfoForAdd.getSignBase64(), remoteCryptographyServiceClient);
        String edrpouFromSign = signerInfo.getOkpo_();
        String rnokppFromSign = signerInfo.getGrfl_();
        String fullNameFromSign = StringTransformUtils.removePunct(signerInfo.getCommon_name_());


        Long currentUserId = getCurrentUserId();
        User currentDeclarant = userRepository.findOneById(currentUserId)
            .orElseThrow(() -> new UserNotFoundException("error.user.not.found.by.id", currentUserId.toString()));

        String declarantFullName = StringTransformUtils.removePunct(currentDeclarant.getFullName());
        String declarantRnokpp = currentDeclarant.getRnokpp();

        CompareUtils.compareRnokppAndFullName(rnokppFromSign, declarantRnokpp, fullNameFromSign, declarantFullName);
        CompareUtils.compareRnokppAndeEdrpouStatehoodSubject(rnokppFromSign, edrpouStatehoodSubject, edrpouFromSign);

        Optional<SubjectMainInfo> optionalSubjectMainInfo = integrationEDRClient.getActiveSubjectInfoByCode(edrpouStatehoodSubject);

        if (optionalSubjectMainInfo.isEmpty()) {
            log.error(EDR_NOT_ACTIVE);
            throw new RuntimeException(EDR_NOT_ACTIVE);
        }

        SubjectMainInfo subjectMainInfo = optionalSubjectMainInfo.get();
        Optional<SubjectDTO> optionalSubjectDTO = integrationEDRClient.getSubjectDTODetails(BigInteger.valueOf(subjectMainInfo.getId()));

        if (optionalSubjectDTO.isEmpty()) {
            log.error(NOT_FOUND_DETAILS);
            throw new RuntimeException(NOT_FOUND_DETAILS);
        }

        SubjectDTO subjectDTODatails = optionalSubjectDTO.get();
        String fullNameFromEdr = StringTransformUtils.removePunct(subjectDTODatails.getName());

        if (!declarantFullName.equals(fullNameFromEdr)) {
            if (!iSHead(subjectDTODatails.getHeadFullName(), declarantFullName)) {
                throw new RuntimeException("Declarant is not Head or Owner  Company");
            }
        }

        StatehoodSubject statehoodSubject;
        Optional<StatehoodSubject> optionalStatehoodSubject = statehoodSubjectRepository.findBySubjectCodeAndSubjectStatusNot(edrpouStatehoodSubject, UserStatus.DELETED);

        if (optionalStatehoodSubject.isPresent()) {
            statehoodSubject = optionalStatehoodSubject.get();

        } else {
            statehoodSubject = createStatehoodSubject(subjectDTODatails, subjectInfoForAdd, declarantFullName);
        }
        statehoodSubjectRepresentService.createRepresenterAndSave(CreateRepresenterDTO.builder().declarant(currentDeclarant).subject(statehoodSubject)
            .subjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE).subjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE)
            .approveSignBase64(subjectInfoForAdd.getSignBase64()).build());

        return statehoodSubject.getId();
    }


    private StatehoodSubject createStatehoodSubject(SubjectDTO subjectDTODatails, SubjectInfoForAdd subjectInfoForAdd, String creator) {

        StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setSubjectStatus(UserStatus.ACTIVE);
        statehoodSubject.setSubjectCode(subjectDTODatails.getCode());
        if (subjectInfoForAdd.getEori() != null)
            statehoodSubject.setEori(subjectInfoForAdd.getEori());

        //TODO Parse  Addres and fill all  values DTO
        Address address = new Address();
        address.setCountryCode("804");

        address.setPostalCode(subjectDTODatails.getAddressFromEDR().getZip());
        address.setHouseNumber(subjectDTODatails.getAddressFromEDR().getParts().getHouse());
        address.setPavilionNumber(subjectDTODatails.getAddressFromEDR().getParts().getBuilding());
        address.setApartmentNumber(subjectDTODatails.getAddressFromEDR().getParts().getHouse());

        addressRepository.saveAndFlush(address);
        statehoodSubject.setActualSameAsLegalAddress(true);
        statehoodSubject.setActualAddress(address);
        statehoodSubject.setLegalAddress(address);

        statehoodSubject.setSubjectShortName(subjectDTODatails.getName());
        statehoodSubject.setKopfg(createKopfg(subjectDTODatails.getOlfName()));
        statehoodSubject.setEmailApproved(false);
        statehoodSubject.setEmail(subjectInfoForAdd.getEmail());
        if (subjectInfoForAdd.getTelNumber() != null)
            statehoodSubject.setTelNumber(subjectInfoForAdd.getTelNumber());
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.FULL_NOT_CONFIRMED);
        statehoodSubject.setSubjectName(subjectDTODatails.getName());
        statehoodSubject.setCreatedAt(Instant.now());
        statehoodSubject.setCreatedBy(creator);
        statehoodSubject.setEori("eori");

        return statehoodSubjectRepository.saveAndFlush(statehoodSubject);
    }

    private Kopfg createKopfg(String kopfgName) {
        if (kopfgName != null) {
            Optional<Kopfg> kopfgOptional = kopfgRepository.findByCode(Integer.parseInt(kopfgName));
            if (kopfgOptional.isPresent())
                return kopfgOptional.get();
        }

        Optional<Kopfg> kopfgOptional = kopfgRepository.findByCode(910);
        if (kopfgOptional.isPresent())
            return kopfgOptional.get();

        Kopfg kopfg = new Kopfg();
        kopfg.setCode(910);
        kopfg.setName("Підприємець - фізична особа");
        kopfgRepository.saveAndFlush(kopfg);
        return kopfg;
    }


    private boolean iSHead(List<String> heads, String declarant) {
        for (String head : heads) {
            if (StringTransformUtils.removePunct(head).equals(declarant)) {
                return true;
            }
        }
        return false;
    }


    private boolean validationAndSynchroniseInternalStatehoodSubject(StatehoodSubject statehoodSubject) {
        Optional<SubjectMainInfo> optionalSubjectMainInfo = integrationEDRClient.getActiveSubjectInfoByCode(statehoodSubject.getSubjectCode());
        if (optionalSubjectMainInfo.isEmpty()) {
//            statehoodSubject.setSubjectStatus(UserStatus.DELETED);
//            statehoodSubjectRepository.save(statehoodSubject);
            log.error(EDR_NOT_ACTIVE);
            throw new RuntimeException(EDR_NOT_ACTIVE);
        } else if (!statehoodSubject.getSubjectStatus().equals(UserStatus.ACTIVE)) {
            statehoodSubject.setSubjectStatus(UserStatus.ACTIVE);
            statehoodSubjectRepository.save(statehoodSubject);
        }
        return true;
    }


}
