package com.idevhub.tapas.repository;


import com.idevhub.tapas.domain.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the OauthClientDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, Long> {

    OauthClientDetails findOneByClientId(String clientId);

}
