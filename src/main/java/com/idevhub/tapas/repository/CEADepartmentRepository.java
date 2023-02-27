package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.CEADepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the CEADepartment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CEADepartmentRepository extends JpaRepository<CEADepartment, Long> {

    @Query("SELECT dep FROM CEADepartment dep " +
        "JOIN dep.centralExecutiveAuthority cea " +
        "WHERE cea.code = :edrpou AND dep.id = :departmentId ")
    Optional<CEADepartment> findCeaDepartmentById(@Param("edrpou") String edrpou, @Param("departmentId") Long departmentId);

    @Query("SELECT dep FROM CEADepartment dep " +
        "JOIN dep.centralExecutiveAuthority cea " +
        "WHERE cea.code = :edrpou ")
    List<CEADepartment> findAllCeaDepartments(@Param("edrpou") String edrpou);

}
