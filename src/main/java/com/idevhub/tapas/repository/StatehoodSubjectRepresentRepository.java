package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data JPA repository for the StatehoodSubjectRepresent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatehoodSubjectRepresentRepository extends JpaRepository<StatehoodSubjectRepresent, Long>, JpaSpecificationExecutor<StatehoodSubjectRepresent> {

    @Query("select represent from StatehoodSubjectRepresent represent left join fetch represent.declarant where represent.declarant.id = :declarantId and represent.statehoodSubject.id = :statehoodSubjectId")
    Optional<StatehoodSubjectRepresent> findByDeclarant_IdAndStatehoodSubject_Id(@Param("declarantId") Long declarantId, @Param("statehoodSubjectId") Long statehoodSubjectId);

    @Query("select represent from StatehoodSubjectRepresent represent left join fetch represent.declarant where represent.declarant.id = :declarantId and represent.subjectRepresentStatus = :status order by represent.statehoodSubject.subjectName")
    List<StatehoodSubjectRepresent> findAllByDeclarant_IdAndSubjectRepresentStatus(Long declarantId, StatehoodSubjectRepresentStatus status);

    Set<StatehoodSubjectRepresent> findAllByDeclarantId(Long declarantId);

    @Query("select represent from StatehoodSubjectRepresent  represent where represent.id = ?1")
    Optional<StatehoodSubjectRepresent> findByIdWithGroups(Long id);

    @Query("select represent from StatehoodSubjectRepresent represent left join fetch represent.declarant where represent.declarant.id = :declarantId and represent.currentContext = true")
    Optional<StatehoodSubjectRepresent> findByDeclarant_IdAndCurrentContextTrue(@Param("declarantId") Long declarantId);

    @Query("select r from StatehoodSubjectRepresent r left join fetch r.declarant where r.declarant.id = :declarantId and r.statehoodSubject.id in (:subjectIds)")
    List<StatehoodSubjectRepresent> findAllByDeclarant_IdAndStatehoodSubject_IdIn(Long declarantId, List<Long> subjectIds);

    @Query("select r from StatehoodSubjectRepresent r left join fetch r.declarant where r.id = ?1")
    Optional<StatehoodSubjectRepresent> findByRepresentId(Long id);

    Optional<StatehoodSubjectRepresent> findByDeclarantIdAndId(Long declarantId, Long representId);


    @Query("SELECT r FROM StatehoodSubjectRepresent r " +
        "JOIN FETCH r.privilegeGroups g " +
        "WHERE :code = g.code")
    Set<StatehoodSubjectRepresent> findAllForGroup(@Param("code") String groupCode);

    @Override
    @EntityGraph(attributePaths = {"declarant"})
    Page<StatehoodSubjectRepresent> findAll(Specification<StatehoodSubjectRepresent> spec, Pageable pageable);
}
