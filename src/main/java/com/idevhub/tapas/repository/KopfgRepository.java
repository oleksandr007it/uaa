package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.Kopfg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the kopfg entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KopfgRepository extends JpaRepository<Kopfg, Long> {

    Optional<Kopfg> findByNameContainsIgnoreCase(String name);

    Optional<Kopfg> findByCode(Integer code);
}

