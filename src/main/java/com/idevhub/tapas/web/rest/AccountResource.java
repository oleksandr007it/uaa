package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.PropertyStatus;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.security.SecurityUtils;
import com.idevhub.tapas.service.UserService;
import com.idevhub.tapas.service.dto.ShortUserDTO;
import com.idevhub.tapas.service.dto.UserDTO;
import com.idevhub.tapas.service.mapper.UserMapper;
import com.idevhub.tapas.web.rest.errors.EmailAlreadyUsedException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;


    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
            .map(userMapper::userToUserDTO)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    @PutMapping("/account/change-language/{langKey}")
    @Timed
    public ResponseEntity<Void> setLanguageCurenntUser(@PathVariable String langKey) {
        if ((!langKey.equals("ua")) && (!langKey.equals("en")) && (!langKey.equals("uk"))) {
            throw new AccountResourceException("Language not available");
        }
        userService.updateLangKey(langKey);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createAlert(applicationName, "profile.notify.updated", ""))
            .build();
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException          {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
            userDTO.getLangKey());


    }

    @PostMapping("/account/declarant")
    public ResponseEntity<ShortUserDTO> saveDeclarantAccount(@Valid @RequestBody ShortUserDTO declarantDTO) {

        String userLogin = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new AccountResourceException("Current user login not found"));

        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(declarantDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }

        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        } else {
            User userFromRepo = user.get();
            String emailFromRepo = userFromRepo.getEmail();
            String emailFromDTO = declarantDTO.getEmail();
            if (emailFromRepo != null && !emailFromRepo.equals(emailFromDTO)) {
                declarantDTO.setEmailApprove(false);
                declarantDTO.setPropertyStatus(PropertyStatus.NOT_COMPLETED);
            }
        }
        Optional<ShortUserDTO> updatedUser = userService.updateDeclarantAccount(declarantDTO);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert(applicationName, "profile.notify.updated", declarantDTO.getId().toString()));
    }

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }


}
