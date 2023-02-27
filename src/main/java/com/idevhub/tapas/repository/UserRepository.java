package com.idevhub.tapas.repository;

import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.RepoConst;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLogin(String login);

    Optional<User> findOneByRnokppAndEdrpouCodeAndUserType(String rnokpp, String edrpouCode, String userType);

    Optional<User> findOneByRnokppAndUserType(String rnokpp, String userType);

    Optional<User> findOneById(Long id);

    @EntityGraph(attributePaths = {"authorities", "ceaDepartment", "centralExecutiveAuthority"})
    @Cacheable(cacheNames = RepoConst.USERS_BY_ID_CACHE)
    Optional<User> findOneWithAuthoritiesById(Long id);

    @EntityGraph(attributePaths = {"authorities", "ceaDepartment", "centralExecutiveAuthority"})
    @Cacheable(cacheNames = RepoConst.USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = {"authorities", "ceaDepartment", "centralExecutiveAuthority"})
    @Cacheable(cacheNames = RepoConst.USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User> findAllByLoginNot(Pageable pageable, String login);

    @Query()
    Optional<User> findByOrgAndPosition(String org, String position);

    @Query("SELECT user FROM User user " +
        "JOIN user.centralExecutiveAuthority cea " +
        "WHERE user.id = :id AND cea.code = :ceaCode ")
    Optional<User> findCeaEmployee(@Param("id") Long id, @Param("ceaCode") String ceaCode);

    @Query("SELECT user FROM User user " +
        "JOIN user.ceaDepartment dep " +
        "JOIN dep.centralExecutiveAuthority cea " +
        "WHERE dep.id = :departmentId AND cea.code = :ceaCode ")
    List<User> findAllCeaEmployeeByDepartmentId(@Param("departmentId") Long departmentId, @Param("ceaCode") String ceaCode);
}
