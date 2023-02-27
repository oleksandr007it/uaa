package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.StatehoodSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the StatehoodSubject entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatehoodSubjectRepository extends JpaRepository<StatehoodSubject, Long>, JpaSpecificationExecutor<StatehoodSubject> {

    @Query("select s from StatehoodSubject s where s.id = :id and s.subjectStatus <> :subjectStatus")
    Optional<StatehoodSubject> findByIdAndSubjectStatusNot(Long id, String subjectStatus);

    Optional<StatehoodSubject> findBySubjectCodeAndSubjectStatusNot(String subjectCode, String subjectStatus);

    boolean existsByIdAndSubjectStatus(Long id, String status);
}
