package com.idevhub.tapas.service.impl;


import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.repository.StatehoodSubjectRepresentRepository;
import com.idevhub.tapas.security.SecurityUtils;
import com.idevhub.tapas.service.EntityNotFoundException;
import com.idevhub.tapas.service.StatehoodSubjectService;
import com.idevhub.tapas.service.dto.StatehoodSubjectRepresentUpdateStatusDTO;
import com.idevhub.tapas.service.errors.WrongRepresentStatusException;
import com.idevhub.tapas.service.feign.RemoteCryptographyServiceClient;
import com.idevhub.tapas.service.utils.CompareUtils;
import com.idevhub.tapas.service.utils.CryptoUtils;
import com.idevhub.tapas.service.utils.StringTransformUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.idevhub.dto.SignerInfo;

import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StatehoodSubjectRepresentOperationService {

    private final StatehoodSubjectRepresentRepository statehoodSubjectRepresentRepository;
    private final RemoteCryptographyServiceClient remoteCryptographyServiceClient;
    private final StatehoodSubjectService statehoodSubjectService;

    public String chekRepresenterAndReturnDataForSign(Long subjectId) {
        log.debug("try to make chekRepresenterAndReturnDataForSign");
        var currentUserId = SecurityUtils.getCurrentUserIdIfExists();

        StatehoodSubjectRepresent statehoodSubjectRepresent = statehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(currentUserId, subjectId)
            .orElseThrow(() -> new EntityNotFoundException(StatehoodSubjectRepresent.class));

        if (statehoodSubjectRepresent.getSubjectRepresentStatus() != StatehoodSubjectRepresentStatus.INACTIVE)
            throw new WrongRepresentStatusException("Represent must be INACTIVE");

        User declarant = statehoodSubjectRepresent.getDeclarant();
        checkStatehoodSubjectAndReturnRnokpp(subjectId);

        return CryptoUtils.makeBase64ForSign(declarant.getRnokpp(), declarant.getFullName(), "confirmLegalEntityRepresentation");
    }

    private String checkStatehoodSubjectAndReturnRnokpp(Long subjectId) {
        log.debug("try  to checkStatehoodSubjectAndReturnRnokpp");

        StatehoodSubject statehoodSubject = statehoodSubjectService.findOneById(subjectId);

        if (!statehoodSubject.getSubjectStatus().equals(UserStatus.ACTIVE)) {
            log.error("The status of SubjectStatus  is wrong. It must be ACTIVE ");
            throw new WrongRepresentStatusException("error.subject.wrong.status");
        }
        log.debug("check StatehoodSubject And Return Rnokpp success");
        return statehoodSubject.getSubjectCode();
    }

    public StatehoodSubjectRepresent updateRepresenterStatus(StatehoodSubjectRepresentUpdateStatusDTO input) {
        log.debug("try to make updateRepresenterStatus");
        var currentUserId = SecurityUtils.getCurrentUserIdIfExists();
        String statehoodSubjectEdrou = checkStatehoodSubjectAndReturnRnokpp(input.getSubjectId());
        StatehoodSubjectRepresent statehoodSubjectRepresent = statehoodSubjectRepresentRepository.findByDeclarant_IdAndStatehoodSubject_Id(currentUserId, input.getSubjectId())
            .orElseThrow(() -> new EntityNotFoundException(StatehoodSubjectRepresent.class));
        SignerInfo signerInfo = CryptoUtils.verifyByData(input.getRawSign(), input.getSignedBase64(), remoteCryptographyServiceClient);

        String edrpouFromSign = signerInfo.getOkpo_().trim();
        String rnokppFromSign = signerInfo.getGrfl_().trim();
        String fullNameFromSign = StringTransformUtils.removePunct(signerInfo.getCommon_name_());

        User currentDeclarant = statehoodSubjectRepresent.getDeclarant();
        String declarantFullName = StringTransformUtils.removePunct(currentDeclarant.getFullName());
        String declarantRnokpp = currentDeclarant.getRnokpp();
        CompareUtils.compareRnokppAndFullName(rnokppFromSign, declarantRnokpp, fullNameFromSign, declarantFullName);
        CompareUtils.compareRnokppAndeEdrpouStatehoodSubject(rnokppFromSign, statehoodSubjectEdrou, edrpouFromSign);

        statehoodSubjectRepresent.setApproveSignBase64(input.getSignedBase64());
        statehoodSubjectRepresent.setUpdatedAt(Instant.now());
        statehoodSubjectRepresent.setUpdatedBy(declarantFullName);
        statehoodSubjectRepresent.setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE);

        return statehoodSubjectRepresentRepository.save(statehoodSubjectRepresent);
    }

}
