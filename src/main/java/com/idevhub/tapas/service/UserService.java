package com.idevhub.tapas.service;

import com.idevhub.tapas.config.Constants;
import com.idevhub.tapas.domain.Authority;
import com.idevhub.tapas.domain.CEAEmployee;
import com.idevhub.tapas.domain.EmailConfirmation;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.PropertyStatus;
import com.idevhub.tapas.domain.constants.RepoConst;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.constants.UserType;
import com.idevhub.tapas.repository.AddressRepository;
import com.idevhub.tapas.repository.AuthorityRepository;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.SecurityUtils;
import com.idevhub.tapas.service.criteria.*;
import com.idevhub.tapas.service.dto.*;
import com.idevhub.tapas.service.errors.EmailAlreadyUsedException;
import com.idevhub.tapas.service.errors.IncorrectValueException;
import com.idevhub.tapas.service.errors.UserNotFoundException;
import com.idevhub.tapas.service.errors.UserNotLoggedInException;
import com.idevhub.tapas.service.mapper.AddressMapper;
import com.idevhub.tapas.service.mapper.UserMapper;
import io.github.jhipster.security.RandomUtil;
import io.github.jhipster.service.filter.StringFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static com.idevhub.tapas.domain.constants.UserStatus.ACTIVE;

/**
 * Service class for managing users.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BackOfficeUserHelper backOfficeUserHelper;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final CacheManager cacheManager;
    private final MailService mailService;
    private final EmailConfirmationService emailConfirmationService;
    private final UserMapper userMapper;
    private final UserSpecificationHelper userHelper;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final DmsuUserHelper dmsuUserHelper;

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }

        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                .map(authorityRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }

        userRepository.save(user);
        this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey) {
        SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin)
            .ifPresent(user -> {
                user.setFirstName(firstName);
                user.setLastName(lastName);
                if (email != null) {
                    user.setEmail(email.toLowerCase());
                }
                user.setLangKey(langKey);
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
            });
    }


    public void softDeleteById(Long id) {
        userRepository.findOneById(id).ifPresent(this::softDelete);
    }

    private void softDelete(User user) {
        SecurityUtils.getCurrentUserLogin().ifPresent(initiator -> {
            user.setStatus(UserStatus.DELETED);
            user.setDeletedDate(Instant.now());
            user.setDeletedBy(initiator);
            user.setActivated(false);
            this.clearUserCaches(user);
            log.debug("Soft Deleted User: {}", user);
        });
    }


    public Optional<ShortUserDTO> updateDeclarantAccount(ShortUserDTO userDTO) {

        return Optional.of(getUserOneById(userDTO.getId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(user -> {
                this.clearUserCaches(user);

                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.setMiddleName(userDTO.getMiddleName());
                String fullName = userDTO.getLastName() + " " + userDTO.getFirstName() + " " + userDTO.getMiddleName();
                user.setFullName(fullName);
                user.setPasportSn(userDTO.getPasportSn());
                user.setPasportDate(userDTO.getPasportDate());
                user.setPhoneNumber(userDTO.getPhoneNumber());
                user.setPasportIssuedBy(userDTO.getPasportIssuedBy());
                user.setPropertyStatus(userDTO.getPropertyStatus());
                if (userDTO.getEmail() != null) {
                    user.setEmail(userDTO.getEmail().toLowerCase());
                    user.setEmailApprove(userDTO.isEmailApprove());
                }
                user.setLegalAddress(addressMapper.mapOrUpdateAddress(userDTO.getLegalAddress(), user.getLegalAddress()));
                this.clearUserCaches(user);
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(userMapper::userToShortUserDTO);
    }


    public void updateLangKey(String langKey) {
        User currentUser = getCurrentUser();
        currentUser.setLangKey(langKey);
        clearUserCaches(currentUser);
        userRepository.save(currentUser);
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public UserDTO updateUser(UserDTO userDTO) {
        User user = userRepository.findOneById(userDTO.getId())
            .orElseThrow(() -> new UserNotFoundException("error.user.not.found.by.id", userDTO.getId().toString()));

        clearUserCaches(user);
        checkDataValidation(userDTO);

        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setPhoneNumber(userDTO.getPhoneNumber());

        if (!userDTO.getEmail().equals(user.getEmail())) {
            user.setEmail(userDTO.getEmail().toLowerCase());
            user.setEmailApprove(false);
        }

        if (!userDTO.getPasportSn().equals(user.getPasportSn())) {
            user.setPasportSn(userDTO.getPasportSn());
            user.setPasportIssuedBy(userDTO.getPasportIssuedBy());
            user.setPasportDate(user.getPasportDate());
        }

        if (userDTO.getLegalAddress() != null) {
            user.setLegalAddress(addressMapper.mapOrUpdateAddress(userDTO.getLegalAddress(), user.getLegalAddress()));
        }

        user.setActivated(userDTO.isActivated());
        user.setLangKey(userDTO.getLangKey());

        Set<Authority> managedAuthorities = user.getAuthorities();
        managedAuthorities.clear();
        userDTO.getAuthorities().stream()
            .map(authorityRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(managedAuthorities::add);

        user.setLastModifiedBy(userDTO.getLogin());
        user.setLastModifiedDate(Instant.now());
        user.setPropertyStatus(PropertyStatus.NOT_CONFIRMED);

        user.setRnokpp(userDTO.getRnokpp());
        user.setEmailApprove(userDTO.getEmailApprove());

        clearUserCaches(user);

        user = userRepository.save(user);

        log.debug("Changed Information for User: {}", user);

        return userMapper.userToUserDTO(user);
    }

    private void checkDataValidation(UserDTO userDTO) {
        if (userDTO.getFirstName() == null ||
            userDTO.getLastName() == null ||
            userDTO.getPhoneNumber() == null ||
            userDTO.getEmail() == null ||
            userDTO.getPasportSn() == null ||
            userDTO.getPasportIssuedBy() == null ||
            userDTO.getPasportDate() == null)

            throw new IncorrectValueException("error.incorrect.value");
    }


    @Transactional(readOnly = true)
    public Optional<User> getUserOneById(Long id) {
        return userRepository.findOneById(id);

    }


    public Page<BackOfficeUserDTO> findAllBackOfficeUsersByCurrentEdrpouCode(Pageable pageable, BackOfficeUserCriteria criteria) {
        log.debug("Request to get all findAllBackOfficeUsers by pageable and criteria");
        User currentUser = getCurrentUser();
        String edrpouCode = currentUser.getEdrpouCode();
        if (edrpouCode != null && !edrpouCode.isEmpty()) {
            StringFilter edrpouCodeFilter = new StringFilter();
            String filter = edrpouCode;
            edrpouCodeFilter.setEquals(filter);
            criteria.setEdrpouCode(edrpouCodeFilter);
        }
        Specification<User> specification = backOfficeUserHelper.createSpecification(criteria);
        return userRepository.findAll(specification, pageable)
            .map(BackOfficeUserDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(userMapper::userToUserDTO);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        Optional<String> currentUserLoginOpt = SecurityUtils.getCurrentUserLogin();

        log.debug("Looking for user with login={}", currentUserLoginOpt.orElse(null));
        Optional<User> userOpt = currentUserLoginOpt.flatMap(userRepository::findOneWithAuthoritiesByLogin)
            .or(() -> {
                var currentUserId = SecurityUtils.getCurrentUserIdIfExists();
                log.debug("Try to looking for user with id={}", currentUserId);

                if (currentUserId == null) return Optional.empty();
                return userRepository.findOneWithAuthoritiesById(currentUserId)
                    .or(() -> {
                        log.debug("Try to looking for user with id={} without caching", currentUserId);
                        return userRepository.findById(currentUserId);
                    });
            });

        return userOpt;
    }


    /**
     * Gets a list of all the authorities.
     *
     * @return a list of all the authorities.
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }


    public void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(RepoConst.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        Objects.requireNonNull(cacheManager.getCache(RepoConst.USERS_BY_ID_CACHE)).evict(user.getId());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(RepoConst.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }

    public void sendEmailConfirmationEmail() {
        User currentUser = getCurrentUser();

        String htmlTemplateName = "userEmailConfimEmail";

        EmailConfirmation confirmation = emailConfirmationService.save(currentUser, htmlTemplateName, currentUser.getEmail(), null);

        mailService.sendUserEmailConfirmationEmail(confirmation.getId(), currentUser, htmlTemplateName);
    }


    @Transactional(readOnly = true)
    public BackOfficeUserWithPrivilegeDTO getBackOfficeUserById(Long id) {
        return userRepository.findOneById(id)
            .map(BackOfficeUserWithPrivilegeDTO::new)
            .orElseThrow(() -> new UserNotFoundException("error.user.not.found.by.id", id.toString()));
    }

    public List<BackOfficeUserDTO> getAllActiveBackOfficeEmployeeHasPrivilege(String fullNameLike, String customsCode, String privilegeCode) {
        log.debug("Trying to get active backoffice employees by customs {} that has privilegeCode={}", customsCode, privilegeCode);

        BackOfficeUserCriteria criteria = new BackOfficeUserCriteria();

        if (fullNameLike != null) {
            StringFilter fullNameLikeFilter = new StringFilter();
            fullNameLikeFilter.setContains(fullNameLike);
            criteria.setFullName(fullNameLikeFilter);
        }

        StringFilter customsCodeFilter = new StringFilter();
        customsCodeFilter.setEquals(customsCode);
        criteria.setOrgCode(customsCodeFilter);

        StringFilter statusFilter = new StringFilter();
        statusFilter.setEquals(UserStatus.ACTIVE);
        criteria.setStatus(statusFilter);

        Specification<User> specification = backOfficeUserHelper.createSpecification(criteria);

        return userRepository.findAll(specification).stream()
            .filter(user -> user.loadAndGetPrivilegeCodes().contains(privilegeCode))
            .map(BackOfficeUserDTO::new)
            .collect(Collectors.toList());
    }

    public User getCurrentUser() {
        Long currentUserId = SecurityUtils.getCurrentUserIdIfExists();

        if (currentUserId == null)
            throw new UserNotLoggedInException("You need to be logged in!!!");

        Optional<User> currentUserOpt = userRepository.findOneById(currentUserId);

        if (currentUserOpt.isPresent()) {
            return currentUserOpt.get();
        } else {
            throw new UserNotFoundException("error.user.not.found.by.id", currentUserId.toString());
        }

    }

    public UserDTO getCurrentLoginUser() {
        User currentUser = getCurrentUser();

        return userMapper.userToUserDTO(currentUser);
    }

    public UserDTO getUserById(Long id) {
        Optional<User> targetUserOpt = getUserOneById(id);

        if (!targetUserOpt.isPresent())
            throw new UserNotFoundException("error.user.not.found.by.id", id.toString());

        return userMapper.userToUserDTO(targetUserOpt.get());
    }


    public String getCurrentUserPosition() {
        User currentUser = getCurrentUser();
        return currentUser.getPosition();
    }

    public User getActiveUserByRnokppAndNames(@NotNull String rnokpp, @NotNull String firstName, @NotNull String lastName, String middleName) {
        Specification<User> specification =
            userHelper.createRepresentSpecification(ACTIVE, rnokpp, firstName, lastName, middleName);

        User target = userRepository.findOne(specification)
            .orElseThrow(() -> new UserNotFoundException("error.user.not.found"));

        return target;
    }

    public Page<DmsuListUserDTO> getDmsuUsersByCurrentUserOrg(Pageable pageable, DmsuUserCriteria criteria) {
        User currentUser = getCurrentUser();

        StringFilter orgFilter = new StringFilter();
        orgFilter.setEquals(currentUser.getOrg());
        criteria.setOrg(orgFilter);

        Specification<User> specification =
            dmsuUserHelper.createSpecification(criteria);

        return userRepository.findAll(specification, pageable)
            .map(DmsuListUserDTO::new);
    }

    public Long getOrgHeadId() {
        User current = getCurrentUser();
        User head =
            userRepository.findByOrgAndPosition(current.getOrg(), "DIRECTOR")
                .orElseThrow(() -> new RuntimeException("User with org: " + current.getOrg() + " and position: DIRECTOR not found!"));

        return head.getId();
    }

    public List<String> findAllPrivilegesByCurrentLoggedInUser() {
        // TODO: Rewrite
        return Collections.emptyList();
    }

    public User saveCeaEmployeeAccount(CEAEmployee toSave) {

        userRepository.findOneByEmailIgnoreCase(toSave.getEmail()).ifPresent(user -> {
            if (toSave.getId() == null || !Objects.equals(toSave.getId(), user.getId()))
                throw new EmailAlreadyUsedException();
        });

        User user = toSave.getId() == null ?
            createCeaEmployeeTypeUser(toSave) :
            userRepository.findCeaEmployee(toSave.getId(), toSave.getCea().getCode())
                .orElseThrow(RuntimeException::new);

        user.setId(toSave.getId());
        user.setFirstName(toSave.getFirstName());
        user.setLastName(toSave.getLastName());
        user.setMiddleName(toSave.getMiddleName());
        user.setFullName(toSave.getFullName());
        user.setRnokpp(toSave.getRnokpp());
        user.setEdrpouCode(toSave.getCea().getCode());
        user.setEmail(toSave.getEmail().toLowerCase());
        user.setPhoneNumber(toSave.getPhoneNumber());
        user.setPosition(toSave.getPosition());
        user.setPositionType(toSave.getPositionType());
        user.setCeaDepartment(toSave.getCeaDepartment());
        user.setCentralExecutiveAuthority(toSave.getCea());
        user.setPropertyStatus(PropertyStatus.CONFIRMED);

        return userRepository.saveAndFlush(user);
    }

    public void softDeleteCeaEmployeeAccount(Long id, String ceaCode) throws UserNotFoundException {
        userRepository.findCeaEmployee(id, ceaCode).ifPresent(this::softDelete);
    }

    private User createCeaEmployeeTypeUser(CEAEmployee employee) {
        User user = new User();
        user.setLogin(RandomUtil.generateResetKey());
        user.setPassword(passwordEncoder.encode(RandomUtil.generatePassword()));
        user.setUserType(UserType.CEA_EMPLOYEE);
        user.setStatus(UserStatus.ACTIVE);
        user.setActivated(true);
        user.setLangKey(Constants.DEFAULT_LANGUAGE);
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.CEA_EMPLOYEE);
        user.getAuthorities().add(authority);
        return user;
    }

}
