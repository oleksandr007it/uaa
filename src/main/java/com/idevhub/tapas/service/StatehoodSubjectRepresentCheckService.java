package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.repository.StatehoodSubjectRepresentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatehoodSubjectRepresentCheckService {

    private final StatehoodSubjectRepresentRepository representRepository;

    /**
     * Система перевіряє, що Декларант є підтвердженим представником поточного Суб'єкта господарювання
     * <br>SET #3, checks #2
     *
     * @see <a href="https://confluence.customs.net.ua/pages/viewpage.action?pageId=6357353">Base checks</a>
     */
    public void checkDeclarantIsActiveAndHaveSubject(Long declarantId, Long subjectId) {
        var represent = representRepository.findByDeclarant_IdAndStatehoodSubject_Id(declarantId, subjectId)
            .orElseThrow((() -> {
                log.error("There is no current represent with declarantId={} and subjectId={}", declarantId, subjectId);
                return new ValidationException("error.subjectRepresent.wrongStatus");
            }));

        if (represent.getSubjectRepresentStatus() != StatehoodSubjectRepresentStatus.ACTIVE) {
            log.error("Current represent with id={} has status={} but not ACTIVE status", represent.getId(), represent.getSubjectRepresentStatus());
            throw new ValidationException("error.subjectRepresent.wrongStatus");
        }
    }

    public void checkNoRepresentHasGroup(String groupCode) {
        if (representRepository.findAllForGroup(groupCode).size() > 0)
            throw new ValidationException("error.privilegeGroup.remainsLinkedWithSubject");
    }
}
