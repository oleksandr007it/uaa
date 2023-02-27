package com.idevhub.tapas.service;

import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import com.idevhub.tapas.repository.PrivilegeCategoryRepository;
import com.idevhub.tapas.service.dto.PrivilegeCategoryDTO;
import com.idevhub.tapas.service.mapper.PrivilegeCategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrivilegeCategoriesService {
    private final PrivilegeCategoryRepository privilegeCategoryRepository;
    private final PrivilegeCategoryMapper privilegeCategoryMapper;

    public Set<PrivilegeCategoryDTO> getCategoriesWithPrivileges(PrivilegeType type) {
        log.debug("Getting categories with privileges for type={}", type);

        var categoryMetas = privilegeCategoryRepository.findAllByPrivilegeCategoryTypeOrderByCode(type);

        return privilegeCategoryMapper.toDto(categoryMetas);
    }
}
