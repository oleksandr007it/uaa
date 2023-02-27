package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.config.Constants;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.SecurityUtils;
import com.idevhub.tapas.service.MailService;
import com.idevhub.tapas.service.UserService;
import com.idevhub.tapas.service.criteria.BackOfficeUserCriteria;
import com.idevhub.tapas.service.criteria.DmsuUserCriteria;
import com.idevhub.tapas.service.dto.*;
import com.idevhub.tapas.service.feign.LdapIntegrationClient;
import com.idevhub.tapas.service.mapper.UserMapper;
import com.idevhub.tapas.web.rest.errors.BadRequestAlertException;
import com.idevhub.tapas.web.rest.errors.EmailAlreadyUsedException;
import com.idevhub.tapas.web.rest.errors.LoginAlreadyUsedException;
import com.idevhub.tapas.web.rest.errors.UserNotFoundException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the {@link User} entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);
    private final UserService userService;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final UserMapper userMapper;
    private final LdapIntegrationClient ldapIntegrationClient;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public UserResource(UserService userService,
                        UserRepository userRepository,
                        MailService mailService,
                        UserMapper userMapper, LdapIntegrationClient ldapIntegrationClient) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userMapper = userMapper;
        this.ldapIntegrationClient = ldapIntegrationClient;
    }

    /**
     * {@code POST  /users}  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param userDTO the user to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new user, or with status {@code 400 (Bad Request)} if the login or email is already in use.
     * @throws URISyntaxException       if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if the login or email is already in use.
     */
    @Generated(value = "This is generate a sample java function")
    @PostMapping("/users")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert(applicationName, "userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    @PutMapping("/users/confirm-email")
    public ResponseEntity<Void> sendConfirmationEmail() {
        userService.sendEmailConfirmationEmail();

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createAlert(applicationName, "profile.notify.confirmationEmailSent", ""))
            .build();
    }

    /**
     * {@code PUT /users} : Updates an existing User.
     *
     * @param userDTO the user to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already in use.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already in use.
     */
    @Generated(value = "This is generate a sample java function")
    @PutMapping("/users")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);

        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());

        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }

        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());

        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }

        UserDTO updatedUser = userService.updateUser(userDTO);

        return ResponseEntity.ok(updatedUser);
    }

    /**
     * {@code GET /users} : get all users.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/backofficeuser")
    public BackOfficeUserDTO getBackOfficeCurrentUser() {
        Optional<User> optionalUser = Optional.ofNullable(userService.getCurrentUser());
        return optionalUser
            .map(BackOfficeUserDTO::new)
            .orElseThrow(UserNotFoundException::new);
    }

    @GetMapping("/current-user")
    public ShortUserDTO getCurrentUser() {
        Optional<User> optionalUser = Optional.ofNullable(userService.getCurrentUser());

        return optionalUser
            .map(userMapper::userToShortUserDTO)
            .orElseThrow(UserNotFoundException::new);
    }


    @GetMapping("/backoffice-user-by-id/{id}")
    public BackOfficeUserWithPrivilegeDTO getBackOfficeUserById(@PathVariable Long id) {
        log.debug("REST request to get BackOffice User by id: {}", id);
        return userService.getBackOfficeUserById(id);
    }


    @GetMapping("/backofficeuserlist")
    public ResponseEntity<List<BackOfficeUserDTO>> getBackOfficeUserListByCurrentEdrpouCode(Pageable pageable, BackOfficeUserCriteria criteria) {
        Page<BackOfficeUserDTO> page = userService.findAllBackOfficeUsersByCurrentEdrpouCode(pageable, criteria);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * Gets a list of all roles.
     *
     * @return a string list of all roles.
     */
    @Generated(value = "This is generate a sample java function")
    @GetMapping("/users/authorities")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * {@code GET /users/:login} : get the "login" user.
     *
     * @param login the login of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "login" user, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(userMapper::userToUserDTO));
    }


    /**
     * {@code DELETE /users/:login} : delete the "login" User.
     *
     * @param id the login of the user to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Generated(value = "This is generate a sample java function")
    @PostMapping("/users/delete/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> SoftDeleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User: {}", id);
        userService.softDeleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "userManagement.deleted", id.toString())).build();
    }


    @GetMapping("/users/current")
    public ResponseEntity<UserDTO> getCurrentLoginUser() {
        log.debug("REST request to get current login User");

        UserDTO currentUserDto = userService.getCurrentLoginUser();

        return ResponseEntity.ok(currentUserDto);
    }

    @GetMapping("/user-by-id/{userId}")
    public UserDTO findUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }


    @GetMapping("/user/current/position")
    public String getCurrentUserPosition() {
        log.debug("REST request to get current user position");

        String currentUserPosition = userService.getCurrentUserPosition();

        return currentUserPosition;
    }

    @GetMapping("/dmsu/user-list")
    public ResponseEntity<List<DmsuListUserDTO>> getDmsuUsersByCurrentUserOrg(@PageableDefault(size = 50) Pageable pageable, DmsuUserCriteria criteria) {
        log.debug("REST request to get all dmsu users with the same org of current logged in user");

        Page<DmsuListUserDTO> page = userService.getDmsuUsersByCurrentUserOrg(pageable, criteria);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/user/privileges")
    public ResponseEntity<List<String>> findAllPrivilegesByCurrentLoggedInUser() {
        log.debug("REST request to get all privileges by current logged in user");

        List<String> targetList = userService.findAllPrivilegesByCurrentLoggedInUser();

        return ResponseEntity.ok(targetList);
    }


    @GetMapping("/user/ldpapuser")
    public UserFromLdapDTO findLoginedUserFromLdap() {
        log.debug("REST request to get all privileges by current logged in user");
        String searchId = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new UserNotFoundException());
        List<UserFromLdapDTO> userFromLdapDTOList = ldapIntegrationClient.getUserFromLdap(searchId);
        if ((userFromLdapDTOList.size() == 0) || (userFromLdapDTOList.size() > 1)) {
            throw new RuntimeException("error find user from Ldap");
        }
        return userFromLdapDTOList.get(0);
    }


    @GetMapping("/backoffice/employees/has-privilege")
    public ResponseEntity<List<BackOfficeUserDTO>> getAllCustomsEmployeeHasPrivilege(@RequestParam(required = false) String fullNameLike, @RequestParam String customsCode, @RequestParam String privilegeCode) {
        log.debug("REST request to get active backoffice employees by customs {} that has privilegeCode={}", customsCode, privilegeCode);
        return new ResponseEntity<>(userService.getAllActiveBackOfficeEmployeeHasPrivilege(fullNameLike, customsCode, privilegeCode), HttpStatus.OK);
    }
}
