package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.PrivilegeCategory;
import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface PrivilegeCategoryRepository extends JpaRepository<PrivilegeCategory, String> {
    List<PrivilegeCategory> findAllByPrivilegeCategoryTypeOrderByCode(PrivilegeType type);
}
