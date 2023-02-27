package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.repository.StatehoodSubjectRepresentRepository;
import com.idevhub.tapas.service.errors.WrongRepresentStatusException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus.ACTIVE;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatehoodSubjectRepresentLookupService {

    private final StatehoodSubjectRepresentRepository representRepository;

    public StatehoodSubjectRepresent tryToGetActiveRepresent(Long userId) {
        log.debug("Try to get and check represent for userId={}", userId);

        var represent = representRepository.findByDeclarant_IdAndCurrentContextTrue(userId)
            .orElseThrow(() -> new EntityNotFoundException(StatehoodSubjectRepresent.class));

        checkStatus(represent, ACTIVE);

        return represent;
    }

    public StatehoodSubjectRepresent tryToGetActiveRepresent(Long userId, Long subjectId) {
        log.debug("Try to get and check represent for userId={} and subjectId={}", userId, subjectId);

        var represent = representRepository.findByDeclarant_IdAndStatehoodSubject_Id(userId, subjectId)
            .orElseThrow(() -> new EntityNotFoundException(StatehoodSubjectRepresent.class));

        checkStatus(represent, ACTIVE);

        return represent;
    }

    private void checkStatus(StatehoodSubjectRepresent represent, StatehoodSubjectRepresentStatus... statuses) {
        var hasValidStatus = ArrayUtils.contains(statuses, represent.getSubjectRepresentStatus());

        if (!hasValidStatus) {
            log.error("The status of represent is wrong. It must be in: " + Arrays.toString(statuses));
            throw new WrongRepresentStatusException("error.represent.wrong.status");
        }
    }

}
