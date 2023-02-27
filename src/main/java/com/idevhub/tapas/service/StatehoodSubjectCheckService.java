package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.repository.StatehoodSubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatehoodSubjectCheckService {

    private final StatehoodSubjectRepository statehoodSubjectRepository;

    /**
     * Система перевіряє, що поточний Суб'єкт госопдарювання є активним ([ENT-0026 Суб'єкт господарювання].[A2 Статус] = "Активний").
     * <br>SET #3, checks #3
     *
     * @see <a href="https://confluence.customs.net.ua/pages/viewpage.action?pageId=6357353">Base checks</a>
     */
    public void checkSubjectIsActive(Long subjectId) {
        if (!statehoodSubjectRepository.existsByIdAndSubjectStatus(subjectId, UserStatus.ACTIVE))
            throw new ValidationException("error.subject.wrongStatus");
    }
}
