package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.address.NaisAtsObjectType;
import com.idevhub.tapas.domain.address.NaisAtsObjectTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NaisAtsObjectType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaisAtsObjectTypeRepository extends JpaRepository<NaisAtsObjectType, NaisAtsObjectTypeId> {

}
