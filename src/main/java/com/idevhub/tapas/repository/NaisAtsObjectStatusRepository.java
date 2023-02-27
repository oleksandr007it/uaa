package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.address.NaisAtsObjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NaisAtsObjectStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaisAtsObjectStatusRepository extends JpaRepository<NaisAtsObjectStatus, Long> {

}
