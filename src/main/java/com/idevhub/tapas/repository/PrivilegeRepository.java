package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.Privilege;
import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@SuppressWarnings("unused")
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, String> {
    Set<Privilege> findAllByCodeInAndPrivilegeType(Iterable<String> privilegeCodes, PrivilegeType privilegeType);
}
