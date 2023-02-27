package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.CentralExecutiveAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the CentralExecutiveAuthority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentralExecutiveAuthorityRepository extends JpaRepository<CentralExecutiveAuthority, Long> {

    @Query("SELECT cea FROM CentralExecutiveAuthority cea " +
        "WHERE cea.code = :code " +
        "AND cea.centralExecutiveAuthorityStatus <> com.idevhub.tapas.domain.enumeration.CentralExecutiveAuthorityStatus.DELETED ")
    Optional<CentralExecutiveAuthority> findByCode(@Param("code") String code);

}
