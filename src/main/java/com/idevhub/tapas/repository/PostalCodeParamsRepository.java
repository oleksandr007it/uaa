package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.address.PostalCodeParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostalCodeParamsRepository extends JpaRepository<PostalCodeParams, Long> {

    @Query("SELECT p FROM PostalCodeParams p " +
        "WHERE p.countryCode = :countryCode " +
        "AND p.status = com.idevhub.tapas.domain.address.enumeration.PostalCodeParamsStatus.ACTIVE ")
    Optional<PostalCodeParams> findActiveByCountryCode(@Param("countryCode") String countryCode);
}
