package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.repository.StatehoodSubjectRepresentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentRepresentService {
    private final StatehoodSubjectRepresentRepository statehoodSubjectRepresentRepository;


    public Optional<Long> getId(Long userId) {
        return statehoodSubjectRepresentRepository.findByDeclarant_IdAndCurrentContextTrue(userId)
            .map(StatehoodSubjectRepresent::getId);
    }
}
