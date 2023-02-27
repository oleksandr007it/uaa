package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.address.NaisAtsDenormalizedObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NaisAtsDenormalizedObject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaisAtsDenormalizedObjectRepository extends JpaRepository<NaisAtsDenormalizedObject, Long>, JpaSpecificationExecutor<NaisAtsDenormalizedObject> {

}
