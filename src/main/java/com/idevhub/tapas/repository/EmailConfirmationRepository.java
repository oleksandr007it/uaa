package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.EmailConfirmation;
import com.idevhub.tapas.domain.enumeration.EmailConfirmationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the EmailConfirmation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmation, Long> {

    @Query("select e from EmailConfirmation e left join fetch e.declarant where e.id = :id")
    Optional<EmailConfirmation> findOneById(Long id);

    @Query("select e from EmailConfirmation e left join fetch e.declarant where e.confirmationStatus in (:statuses)")
    List<EmailConfirmation> findAllByConfirmationStatusIn(List<EmailConfirmationStatus> statuses);
}
