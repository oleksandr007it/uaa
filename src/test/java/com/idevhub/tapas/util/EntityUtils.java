package com.idevhub.tapas.util;

import com.idevhub.tapas.domain.*;
import com.idevhub.tapas.domain.address.Address;
import com.idevhub.tapas.domain.enumeration.AccountDetailsStatus;
import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentType;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class EntityUtils {

    public static void persistPrivilegeForRepresent(EntityManager em, StatehoodSubjectRepresent represent, String privilegeCode) {
        var privilegeGroup = privilegeInGroup(em, privilegeCode, "TEST_GROUP_" + RandomStringUtils.randomAlphanumeric(5));
        em.persist(privilegeGroup);

        represent.setPrivilegeGroups(Set.of(privilegeGroup));
        em.persist(represent);

        em.flush();
    }

    public static void persistPrivilegeForUser(EntityManager em, User user, String privilegeCode) {
        var privilegeGroup = privilegeInGroup(em, privilegeCode, "TEST_GROUP_" + RandomStringUtils.randomAlphanumeric(5));
        em.persist(privilegeGroup);

        user.setPrivilegeGroups(Set.of(privilegeGroup));
        em.persist(user);

        em.flush();
    }

    public static PrivilegeGroup privilegeInGroup(EntityManager em, String privilegeCode, String groupCode) {
        var categoryCode = "C" + privilegeCode;
        var category = new PrivilegeCategory(categoryCode)
            .setFullNameUkr("fullNameUkr")
            .setFullNameEng("fullNameEng")
            .setGlobal(true)
            .setPrivilegeCategoryType(PrivilegeType.LEGAL_ENTITY);
        em.persist(category);


        var privilege = new Privilege(privilegeCode);
        privilege.setFullNameUkr("fullNameUkr");
        privilege.setFullNameEng("fullNameEng");
        privilege.setPrivilegeType(PrivilegeType.LEGAL_ENTITY);
        privilege.setPrivilegeCategory(category);

        em.persist(privilege);
        em.flush();

        return new PrivilegeGroup(groupCode, new HashSet<>(Set.of(privilege)))
            .setPrivilegeGroupType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("ukr")
            .setFullNameEng("eng")
            .setGlobal(true);
    }


    public static StatehoodSubject createStatehoodSubject(String status,EntityManager em,String edorpou) {

        StatehoodSubject statehoodSubject = new StatehoodSubject();
        statehoodSubject.setSubjectStatus(status);
        statehoodSubject.setSubjectCode(edorpou);

        Address address = new Address();
        address.setCountryCode("804");
        address.setPostalCode("030423");
        address.setHouseNumber("499");
        address.setPavilionNumber("342511");
        address.setApartmentNumber("888");
        em.persist(address);
        em.flush();


        statehoodSubject.setActualAddress(address);
        statehoodSubject.setSubjectShortName("shott Name");

        Kopfg kopfg = new Kopfg();
        kopfg.setCode(91034);
        kopfg.setName("Підприємець - фізична особа");
        em.persist(kopfg);
        em.flush();

        statehoodSubject.setKopfg(kopfg);
        statehoodSubject.setEmailApproved(false);
        statehoodSubject.setEmail("goggo@gddh.ua");
        statehoodSubject.setTelNumber("380764536188");
        statehoodSubject.setActualSameAsLegalAddress(true);
        statehoodSubject.setAccountDetailsStatus(AccountDetailsStatus.FULL_CONFIRMED);
        statehoodSubject.setSubjectName("Rgi b Nogi");
        statehoodSubject.setLegalAddress(address);
        statehoodSubject.setCreatedAt(Instant.now());
        statehoodSubject.setCreatedBy("creator");
        statehoodSubject.setEori("eori");
        em.persist(statehoodSubject);
        em.flush();
        return statehoodSubject;
    }

    public  static User createEntityUser(String rnokpp, String edorpou,EntityManager em) {
        User user = new User();
        user.setLogin("admin" + RandomStringUtils.randomAlphabetic(5));
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail(RandomStringUtils.randomAlphabetic(5) + "johndoe@localhost");
        user.setFirstName("Петренко");
        user.setLastName("Павло");
        user.setLangKey("Павлович");
        user.setFullName("Петренко Павло Павлович");
        user.setEdrpouCode(edorpou);
        user.setRnokpp(rnokpp);
        em.persist(user);
        em.flush();
        return user;
    }

    public  static  StatehoodSubjectRepresent createRepresenterAndSave(StatehoodSubject statehoodSubject, User declarant, StatehoodSubjectRepresentStatus status,EntityManager em) {

        StatehoodSubjectRepresent statehoodSubjectRepresent = new StatehoodSubjectRepresent();
        statehoodSubjectRepresent.setSubjectRepresentStatus(status);
        statehoodSubjectRepresent.setCreatedAt(Instant.now());
        statehoodSubjectRepresent.setCreatedBy("admin");
        statehoodSubjectRepresent.setStatehoodSubject(statehoodSubject);
        statehoodSubjectRepresent.setDeclarant(declarant);
        statehoodSubjectRepresent.setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE);
        statehoodSubjectRepresent.setCurrentContext(false);
        em.persist(statehoodSubjectRepresent);
        em.flush();
        return statehoodSubjectRepresent;
    }

    public static StatehoodSubjectRepresent statehoodSubjectRepresent(EntityManager em, User user) {
        var subject = statehoodSubject(em);
        em.persist(subject);
        em.flush();

        return new StatehoodSubjectRepresent()
            .setSubjectRepresentStatus(StatehoodSubjectRepresentStatus.ACTIVE)
            .setSubjectRepresentType(StatehoodSubjectRepresentType.CHIEF_EXECUTIVE)
            .setCurrentContext(true)
            .setCreatedBy("test")
            .setStatehoodSubject(subject)
            .setDeclarant(user);
    }

    public static User user() {
        return new User()
            .setLogin("admin_" + RandomStringUtils.randomAlphabetic(6))
            .setPassword(RandomStringUtils.randomAlphabetic(60))
            .setActivated(true)
            .setEmail("mail_" + RandomStringUtils.randomAlphabetic(15) + "@gmail.com")
            .setFirstName("Administrator")
            .setLastName("Ministrator")
            .setLangKey("uk");
    }

    public static StatehoodSubject statehoodSubject(EntityManager em) {
        var kofpg = new Kopfg()
            .setCode(0)
            .setName("test_kofpg")
            .setValidUntil(Instant.now().plus(1, ChronoUnit.DAYS))
            .setPreviousCodes("");
        var address = new Address();
        address.setCountryCode("804");
        address.setPostalCode("01001");
        address.setHouseNumber("7");
        address.setApartmentNumber("7");

        em.persist(kofpg);
        em.persist(address);
        em.flush();

        return new StatehoodSubject()
            .setSubjectStatus("ACTIVE")
            .setAccountDetailsStatus(AccountDetailsStatus.NOT_FULL)
            .setCreatedBy("test")
            .setSubjectCode("")
            .setSubjectName("")
            .setSubjectShortName("")
            .setHeadFullName("")
            .setTelNumber("")
            .setEmail("")
            .setEmailApproved(true)
            .setEori("")
            .setActualSameAsLegalAddress(false)
            .setKopfg(kofpg)
            .setLegalAddress(address)
            .setActualAddress(address);
    }
}
