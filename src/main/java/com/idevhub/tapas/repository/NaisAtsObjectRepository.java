package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.address.NaisAtsObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the NaisAtsObject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NaisAtsObjectRepository extends JpaRepository<NaisAtsObject, Long> {

    @Query("SELECT o.id FROM NaisAtsObject o " +
        "ORDER BY o.type.level, o.type.code ")
    List<Long> findAllIdsOrderedByType();

    boolean existsByParent_Id(Long parentId);
}
