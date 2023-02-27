package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.PrivilegeGroup;
import com.idevhub.tapas.domain.enumeration.ActiveStatus;
import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PrivilegeGroupRepository extends JpaRepository<PrivilegeGroup, String> {
    Optional<PrivilegeGroup> findByCodeAndStatus(String groupCode, ActiveStatus status);

    Optional<PrivilegeGroup> findByCodeAndPrivilegeGroupTypeAndStatus(String groupCode, PrivilegeType type, ActiveStatus status);

    Set<PrivilegeGroup> findAllByPrivilegeGroupTypeAndStatus(PrivilegeType type, ActiveStatus status);

    Optional<PrivilegeGroup> findFirstByFullNameUkrAndPrivilegeGroupTypeAndStatus(String fullNameUkr, PrivilegeType groupType, ActiveStatus status);

    @Override
    default void delete(PrivilegeGroup entity) {
        entity.setStatus(ActiveStatus.DELETED);
        save(entity);
    }
}
