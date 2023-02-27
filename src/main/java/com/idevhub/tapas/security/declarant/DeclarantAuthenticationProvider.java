package com.idevhub.tapas.security.declarant;

import com.idevhub.tapas.config.Constants;
import com.idevhub.tapas.domain.Authority;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.PropertyStatus;
import com.idevhub.tapas.domain.constants.RepoConst;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.constants.UserType;
import com.idevhub.tapas.repository.UserRepository;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.BaseAuthenticationProvider;
import com.idevhub.tapas.security.CustomUserDetails;
import com.idevhub.tapas.security.DomainUserDetailsService;
import com.idevhub.tapas.service.dto.UserDTOfromIdGovUa;
import io.github.jhipster.security.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static com.idevhub.tapas.service.utils.StringTransformUtils.hideStr;

@Component("declarantAuthenticationProvider")
public class DeclarantAuthenticationProvider extends BaseAuthenticationProvider {
    private final static String MESSAGE_FOR_LOG = "declaran";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(DeclarantAuthenticationProvider.class);

    private final CacheManager cacheManager;

    public DeclarantAuthenticationProvider(UserRepository userRepository, DomainUserDetailsService userDetailsService, CacheManager cacheManager) {
        super(userDetailsService);
        this.userRepository = userRepository;
        this.cacheManager = cacheManager;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        logger.info("Start authenticate " + MESSAGE_FOR_LOG + "provider");
        String userInfo = authentication.getName();

        checkUserInfo(userInfo, MESSAGE_FOR_LOG);

        UserDTOfromIdGovUa userFromIdGovUaDTO = tryToGetUserInfo(userInfo, MESSAGE_FOR_LOG);
        String rnokpp = userFromIdGovUaDTO.getRnokpp();
        String edrpou = userFromIdGovUaDTO.getEdrpouCode();

        var rnokppForPrint = hideStr(rnokpp);
        logger.debug("Looking for existing user with rnokpp={} and userType={}", rnokppForPrint, UserType.DECLARANT);

        User userFromrepo = userRepository.findOneByRnokppAndUserType(rnokpp, UserType.DECLARANT).orElse(null);

        if (userFromrepo == null) {
            logger.debug("User was not found by rnokpp={} and userType={}. Creating a new user.", rnokppForPrint, UserType.DECLARANT);

            var newUser = new User();

            newUser.setLogin("user" + RandomUtil.generateRandomAlphanumericString());
            newUser.setRnokpp(rnokpp);
            newUser.setEdrpouCode(edrpou);

            newUser.setFirstName(userFromIdGovUaDTO.getFirstName());
            newUser.setLastName(userFromIdGovUaDTO.getLastName());
            newUser.setMiddleName(userFromIdGovUaDTO.getMiddleName());
            newUser.setFullName(userFromIdGovUaDTO.getFullName());
            newUser.setOrgUnit(userFromIdGovUaDTO.getOrgUnit());
            newUser.setOrg(userFromIdGovUaDTO.getOrg());
            newUser.setPosition(userFromIdGovUaDTO.getPosition());
            newUser.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
            newUser.setStatus(UserStatus.ACTIVE);
            newUser.setPropertyStatus(PropertyStatus.NOT_COMPLETED);
            newUser.setPassword(passwordEncoder.encode(RandomUtil.generatePassword()));
            newUser.setActivated(true);
            newUser.setUserType(UserType.DECLARANT);
            Authority authority = new Authority();
            authority.setName(AuthoritiesConstants.DECLARANT);
            newUser.getAuthorities().add(authority);

            userFromrepo = userRepository.saveAndFlush(newUser);
            clearCache(userFromrepo);

            logger.debug("New user was created. Details: id={}, userType={}, rnokpp={}, edrpou={}",
                userFromrepo.getId(), userFromrepo.getUserType(), hideStr(userFromrepo.getRnokpp()), hideStr(userFromrepo.getEdrpouCode()));
        }

        if (!userFromrepo.isActivated()) {
            logger.error("User Declarant is not activated");
            throw new BadCredentialsException("User Declarant is not activated");
        }

        CustomUserDetails userDetails = tryToGetUserDetailsByRnokppAndUserType(rnokpp, UserType.DECLARANT);

        var authorities = userDetails.getAuthorities();
        checkAuthorities(authorities, AuthoritiesConstants.DECLARANT);

        UsernamePasswordAuthenticationToken authenticationWithAuthorities = tryToMakeAuthenticationTokenAndAddUserIdToDetails(userDetails, authorities, authentication);

        return authenticationWithAuthorities;
    }

    private void clearCache(User user) {
        var loginCache = cacheManager.getCache(RepoConst.USERS_BY_LOGIN_CACHE);
        if (loginCache != null) {
            loginCache.evict(user.getLogin());
        }
        var idCache = cacheManager.getCache(RepoConst.USERS_BY_ID_CACHE);
        if (idCache != null) {
            idCache.evict(user.getId());
        }
        if (user.getEmail() != null) {
            var emailCache = cacheManager.getCache(RepoConst.USERS_BY_EMAIL_CACHE);
            if (emailCache != null) {
                emailCache.evict(user.getEmail());
            }
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(DeclarantAuthenticationToken.class);
    }
}
