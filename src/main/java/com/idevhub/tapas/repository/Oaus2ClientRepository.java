package com.idevhub.tapas.repository;


import com.idevhub.tapas.domain.Oaus2Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Oaus2ClientRepository extends JpaRepository<Oaus2Client, Long> {

    Optional<Oaus2Client> findOneByClientId(String clientId);

}
