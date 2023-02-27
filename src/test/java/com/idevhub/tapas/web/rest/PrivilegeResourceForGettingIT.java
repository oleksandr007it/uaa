package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.UaaApp;
import com.idevhub.tapas.domain.Privilege;
import com.idevhub.tapas.domain.PrivilegeCategory;
import com.idevhub.tapas.domain.PrivilegeGroup;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.enumeration.ActiveStatus;
import com.idevhub.tapas.domain.enumeration.PrivilegeType;
import com.idevhub.tapas.security.WithMockCustomUser;
import com.idevhub.tapas.security.utils.TestSecurityUtils;
import com.idevhub.tapas.service.dto.PrivilegeDTO;
import com.idevhub.tapas.service.dto.PrivilegeGroupWithPrivilegesDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.idevhub.tapas.util.EntityUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {UaaApp.class})
@AutoConfigureMockMvc
@WithMockUser
@DisplayName("Privilege for user and represent test")
class PrivilegeResourceForGettingIT {

    @Autowired
    EntityManager em;


    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Test privileges returns correctly for current user")
    @WithMockCustomUser
    @Transactional
    void getUserPrivilegeCodes_CurrentUser() throws Exception {
        // GIVEN
        // Create some User
        var user = user();
        em.persist(user);
        // Create and add some privilege for User
        persistPrivilegeForUser(em, user, "V1_1_1_test");
        em.flush();

        // Set user as current
        TestSecurityUtils.setSecurityContext(user.getId());

        // WHEN
        mvc.perform(get("/api/privileges-and-groups/user/privilege-codes")
            .accept(APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            // Has expected privilege
            .andExpect(jsonPath("$.[*]", hasItem("V1_1_1_test")));
    }


    @Test
    @DisplayName("Test privileges returns correctly for different users")
    @WithMockCustomUser
    @Transactional
    void getUserPrivilegeCodes_CustomUser() throws Exception {
        // GIVEN
        // Create some User
        var user1 = user();
        em.persist(user1);
        // Create and add some privilege for User
        persistPrivilegeForUser(em, user1, "V1_1_1_test");
        // Create another User
        var user2 = user();
        em.persist(user2);
        // Create and add some privilege for User
        persistPrivilegeForUser(em, user2, "V2_2_2_test");
        em.flush();


        // WHEN
        mvc.perform(get("/api/privileges-and-groups/user/" + user1.getId() + "/privilege-codes")
            .accept(APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            // Has expected privilege
            .andExpect(jsonPath("$.[?(@=='V1_1_1_test')]").exists())
            // Has no unexpected privilege
            .andExpect(jsonPath("$.[?(@=='V2_2_2_test')]").doesNotExist());

        // WHEN
        mvc.perform(get("/api/privileges-and-groups/user/" + user2.getId() + "/privilege-codes")
            .accept(APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            // Has expected privilege
            .andExpect(jsonPath("$.[?(@=='V2_2_2_test')]").exists())
            // Has no unexpected privilege
            .andExpect(jsonPath("$.[?(@=='V1_1_1_test')]").doesNotExist());
    }

    @Test
    @DisplayName("Test both User and Represent privileges are present for current user")
    @Transactional
    @WithMockCustomUser
    void getUserPrivilegeCodes_DifferentUsers() throws Exception {
        // GIVEN
        // Create some User
        var user = user();
        em.persist(user);
        // Create and add some privilege for User
        persistPrivilegeForUser(em, user, "V1_2_3_test_for_user");
        // Create Subject Represent for User with privilege groups
        var subjectRepresent = statehoodSubjectRepresent(em, user);
        em.persist(subjectRepresent);
        // Create and add some privilege for Represent
        persistPrivilegeForRepresent(em, subjectRepresent, "V1_2_3_test_for_represent");
        em.flush();

        // Set user as current
        TestSecurityUtils.setSecurityContext(user.getId());

        // WHEN
        mvc.perform(get("/api/privileges-and-groups/user/privilege-codes")
            .accept(APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            // Has expected privileges from user group and represent group
            .andExpect(jsonPath("$.[*]", hasItem("V1_2_3_test_for_user")))
            .andExpect(jsonPath("$.[*]", hasItem("V1_2_3_test_for_represent")));
    }


    @Test
    @DisplayName("Getting subject groups returns correctly")
    @WithMockCustomUser
    @Transactional
    void getSubjectGroups() throws Exception {
        // GIVEN
        // Create target subject
        var targetSubject = statehoodSubject(em);
        em.persist(targetSubject);
        // Create some other subject
        var someOtherSubject = statehoodSubject(em);
        em.persist(someOtherSubject);

        // Create some global group with meta
        var globalGroup = privilegeInGroup(em, "V1_1_1_test", "TEST_GROUP_GLOBAL");
        globalGroup
            .setPrivilegeGroupType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("ukr")
            .setFullNameEng("eng")
            .setGlobal(true);
        em.persist(globalGroup);

        // Create some local group with meta for created Subject
        var localGroup = privilegeInGroup(em, "V1_1_2_test", "TEST_GROUP_LOCAL");
        localGroup
            .setPrivilegeGroupType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("ukr")
            .setFullNameEng("eng")
            .setStatehoodSubject(new StatehoodSubject().setId(targetSubject.getId()))
            .setGlobal(false);
        em.persist(localGroup);

        // Create some other local group NOT for target Subject
        var localGroup2 = privilegeInGroup(em, "V1_1_3_test", "TEST_GROUP_LOCAL2");
        localGroup2
            .setPrivilegeGroupType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("ukr")
            .setFullNameEng("eng")
            .setStatehoodSubject(new StatehoodSubject().setId(someOtherSubject.getId()))
            .setGlobal(false);
        em.persist(localGroup2);

        em.flush();

        // WHEN
        mvc.perform(get("/api/privileges-and-groups/subject/" + targetSubject.getId() + "/groups")
            .accept(APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            // Has expected groups
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[*].code", hasItems("TEST_GROUP_GLOBAL", "TEST_GROUP_LOCAL")))
            .andExpect(jsonPath("$.[*].isGlobal", hasItems(true, false)))
            .andExpect(jsonPath("$.[*].status", hasItems("ACTIVE")))
            .andExpect(jsonPath("$.[*].fullNameUkr").isNotEmpty())
            .andExpect(jsonPath("$.[*].fullNameEng").isNotEmpty());
    }


    @Test
    @DisplayName("Getting subject group returns correctly")
    @WithMockCustomUser
    @Transactional
    void getSubjectGroup() throws Exception {
        // GIVEN
        // Create target subject
        var targetSubject = statehoodSubject(em);
        em.persist(targetSubject);
        // Create some other subject
        var someOtherSubject = statehoodSubject(em);
        em.persist(someOtherSubject);

        // Create some local group with meta for created Subject
        var localGroup = privilegeInGroup(em, "V1_1_1_test", "TEST_GROUP_LOCAL");
        localGroup
            .setPrivilegeGroupType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("ukr")
            .setFullNameEng("eng")
            .setStatehoodSubject(new StatehoodSubject().setId(targetSubject.getId()))
            .setGlobal(false);
        em.persist(localGroup);


        em.flush();

        // WHEN
        mvc.perform(get("/api/privileges-and-groups/subject/" + targetSubject.getId() + "/groups/TEST_GROUP_LOCAL")
            .accept(APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            // Has expected groups
            .andExpect(jsonPath("$.code", is("TEST_GROUP_LOCAL")))
            .andExpect(jsonPath("$.isGlobal", is(false)))
            .andExpect(jsonPath("$.status", is("ACTIVE")))
            .andExpect(jsonPath("$.fullNameUkr", is("ukr")))
            .andExpect(jsonPath("$.fullNameEng", is("eng")))
            .andExpect(jsonPath("$.privileges").isNotEmpty())
            .andExpect(jsonPath("$.privileges[0].code").value("V1_1_1_test"))
            .andExpect(jsonPath("$.privileges[0].fullNameUkr").isNotEmpty())
            .andExpect(jsonPath("$.privileges[0].fullNameEng").isNotEmpty());
    }

    @Test
    @DisplayName("Test represent groups returns correctly")
    @WithMockCustomUser
    @Transactional
    void getRepresentGroup() throws Exception {
        // GIVEN
        // Create target subject
        var targetUser = user();
        em.persist(targetUser);
        // Create Subject Represent for User with privilege groups
        var targetSubjectRepresent = statehoodSubjectRepresent(em, targetUser);
        em.persist(targetSubjectRepresent);

        var targetSubject = targetSubjectRepresent.getStatehoodSubject();

        // Create some global group with meta
        var globalGroup = privilegeInGroup(em, "V1_1_1_test", "TEST_GROUP_GLOBAL");
        globalGroup.setPrivilegeGroupType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("ukr")
            .setFullNameEng("eng")
            .setGlobal(true);
        em.persist(globalGroup);

        // Create some local group with meta for created Subject
        var localGroup = privilegeInGroup(em, "V1_1_2_test", "TEST_GROUP_LOCAL");
        localGroup
            .setPrivilegeGroupType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("ukr")
            .setFullNameEng("eng")
            .setStatehoodSubject(new StatehoodSubject().setId(targetSubject.getId()))
            .setGlobal(false);
        em.persist(localGroup);

        targetSubjectRepresent.setPrivilegeGroups(new HashSet<>(Set.of(globalGroup, localGroup)));
        em.merge(targetSubjectRepresent);

        em.flush();

        // Change security context on current user
        TestSecurityUtils.setSecurityContext(targetUser.getId());

        // WHEN
        mvc.perform(get("/api/privileges-and-groups/represent/" + targetSubjectRepresent.getId() + "/groups")
            .accept(APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            // Has expected groups
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$.[*].code", hasItems("TEST_GROUP_GLOBAL", "TEST_GROUP_LOCAL")))
            .andExpect(jsonPath("$.[*].isGlobal", hasItems(true, false)))
            .andExpect(jsonPath("$.[*].status", hasItems("ACTIVE")))
            .andExpect(jsonPath("$.[*].fullNameUkr").isNotEmpty())
            .andExpect(jsonPath("$.[*].fullNameEng").isNotEmpty());

    }

    @Test
    @DisplayName("Test subject group can be deleted if it's local")
    @WithMockCustomUser
    @Transactional
    void deleteSubjectGroups_CanDeleteLocalGroup() throws Exception {
        // GIVEN
        // Create target subject
        var targetUser = user();
        em.persist(targetUser);
        // Create Subject Represent for User with privilege groups
        var targetSubjectRepresent = statehoodSubjectRepresent(em, targetUser);
        em.persist(targetSubjectRepresent);

        var targetSubject = targetSubjectRepresent.getStatehoodSubject();

        // Create some global group with meta
        var globalGroup = privilegeInGroup(em, "V1_1_1_test", "TEST_GROUP_GLOBAL");
        globalGroup
            .setPrivilegeGroupType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("ukr")
            .setFullNameEng("eng")
            .setGlobal(true);
        em.persist(globalGroup);

        // Create some local group with meta for created Subject
        var localGroup = privilegeInGroup(em, "V1_1_2_test", "TEST_GROUP_LOCAL");
        localGroup
            .setPrivilegeGroupType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("ukr")
            .setFullNameEng("eng")
            .setStatehoodSubject(new StatehoodSubject().setId(targetSubject.getId()))
            .setGlobal(false);
        em.persist(localGroup);

        targetSubjectRepresent.setPrivilegeGroups(new HashSet<>(Set.of(globalGroup, localGroup)));
        em.merge(targetSubjectRepresent);

        em.flush();

        // Change security context on current user
        TestSecurityUtils.setSecurityContext(targetUser.getId());

        ///////////////////////
        /// Check group can't be deleted because it linked with subject represent
        ///////////////////////

        // WHEN
        mvc.perform(delete("/api/privileges-and-groups/subject/groups/" + "TEST_GROUP_LOCAL")
            .accept(APPLICATION_JSON))
            // THEN
            .andExpect(status().is5xxServerError());

        // THEN
        // Group exists in database and status is ACTIVE
        var group = em.find(PrivilegeGroup.class, "TEST_GROUP_LOCAL");
        assertThat(group).isNotNull();
        assertThat(group.getStatus()).isEqualTo(ActiveStatus.ACTIVE);

        ///////////////////////
        /// Check group can be deleted after it's not releted to any represent
        ///////////////////////

        // Remove groups from subject
        targetSubjectRepresent.setPrivilegeGroups(new HashSet<>());
        em.merge(targetSubjectRepresent);

        // WHEN
        mvc.perform(delete("/api/privileges-and-groups/subject/groups/" + "TEST_GROUP_LOCAL")
            .accept(APPLICATION_JSON))
            // THEN
            .andExpect(status().is2xxSuccessful());

        // THEN
        // Group exists in database and status is DELETED
        group = em.find(PrivilegeGroup.class, "TEST_GROUP_LOCAL");
        assertThat(group).isNotNull();
        assertThat(group.getStatus()).isEqualTo(ActiveStatus.DELETED);
    }

    @Test
    @DisplayName("Test subject group can not be deleted if it's global")
    @WithMockCustomUser
    @Transactional
    void deleteSubjectGroups_FailDeleteGlobalGroup() throws Exception {
        // GIVEN
        // Create target subject
        var targetSubject = statehoodSubject(em);
        em.persist(targetSubject);

        // Create some global group with meta
        var globalGroup = privilegeInGroup(em, "V1_1_1_test", "TEST_GROUP_GLOBAL");
        globalGroup
            .setPrivilegeGroupType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("ukr")
            .setFullNameEng("eng")
            .setGlobal(true);
        em.persist(globalGroup);


        // Create some local group with meta for created Subject
        var localGroup = privilegeInGroup(em, "V1_1_2_test", "TEST_GROUP_LOCAL");
        localGroup
            .setPrivilegeGroupType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("ukr")
            .setFullNameEng("eng")
            .setStatehoodSubject(new StatehoodSubject().setId(targetSubject.getId()))
            .setGlobal(false);
        em.persist(localGroup);

        em.flush();

        // WHEN
        mvc.perform(delete("/api/privileges-and-groups/subject/groups/" + "TEST_GROUP_GLOBAL")
            .accept(APPLICATION_JSON))
            // THEN
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType(APPLICATION_PROBLEM_JSON));

        // THEN
        // Both groups exists and were not deleted
        assertThat(em.find(PrivilegeGroup.class, "TEST_GROUP_GLOBAL")).isNotNull();
        assertThat(em.find(PrivilegeGroup.class, "TEST_GROUP_LOCAL")).isNotNull();
    }

    @Test
    @DisplayName("Category returns with its privileges")
    @WithMockCustomUser
    @Transactional
    void getPrivilegeCategories() throws Exception {
        // GIVEN
        // Create privilege category
        var category = new PrivilegeCategory("V1")
            .setPrivilegeCategoryType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameEng("my category")
            .setFullNameUkr("моя категория");
        em.persist(category);

        // Create other category - with different type
        var category2 = new PrivilegeCategory("V2")
            .setPrivilegeCategoryType(PrivilegeType.DMSU)
            .setFullNameEng("my category 2")
            .setFullNameUkr("моя категория 2");

        em.persist(category);
        em.persist(category2);

        // Create privilege
        var privilege = new Privilege("V1_1_1")
            .setPrivilegeCategory(category)
            .setFullNameUkr("моя привилегия")
            .setFullNameEng("my privilege")
            .setPrivilegeType(PrivilegeType.LEGAL_ENTITY);
        em.persist(privilege);

        category.setPrivileges(new HashSet<>(List.of(privilege)));
        em.persist(category);

        em.flush();

        // WHEN
        mvc.perform(get("/api/privileges-and-groups/categories?privilegeType=LEGAL_ENTITY")
            .accept(APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            // Has expected privilege
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$.[0].code").value("V1"))
            .andExpect(jsonPath("$.[0].fullNameUkr").value("моя категория"))
            .andExpect(jsonPath("$.[0].fullNameEng").value("my category"))
            .andExpect(jsonPath("$.[0].privileges").isNotEmpty())
            .andExpect(jsonPath("$.[0].privileges[0].code").value("V1_1_1"))
            .andExpect(jsonPath("$.[0].privileges[0].fullNameUkr").value("моя привилегия"))
            .andExpect(jsonPath("$.[0].privileges[0].fullNameEng").value("my privilege"));
    }


    @Test
    @DisplayName("Correctly create new local privilege group")
    @WithMockCustomUser
    @Transactional
    void createPrivilegeGroup() throws Exception {
        // GIVEN
        var user = user();
        em.persist(user);

        var subjectRepresent = statehoodSubjectRepresent(em, user);
        em.persist(subjectRepresent);

        var subject = subjectRepresent.getStatehoodSubject();

        var c1 = new PrivilegeCategory("C")
            .setGlobal(false)
            .setFullNameUkr("setFullNameUkr")
            .setFullNameEng("setFullNameEng")
            .setPrivilegeCategoryType(PrivilegeType.LEGAL_ENTITY);
        em.persist(c1);
        // Create privilege
        var p1 = new Privilege("V1_1_1")
            .setPrivilegeCategory(c1)
            .setPrivilegeType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("privilege1");
        var p2 = new Privilege("V2_1_1")
            .setPrivilegeCategory(c1)
            .setPrivilegeType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("privilege2");
        em.persist(p1);
        em.persist(p2);

        em.flush();

        // Create privilege group
        var newPrivilegeGroup = new PrivilegeGroupWithPrivilegesDTO();
        newPrivilegeGroup.setPrivileges(List.of(
            new PrivilegeDTO("V1_1_1"),
            new PrivilegeDTO("V2_1_1"))
        );
        newPrivilegeGroup.setFullNameUkr("нова група");

        em.flush();

        TestSecurityUtils.setSecurityContext(user.getId());

        // WHEN
        mvc.perform(post("/api/privileges-and-groups/subject/" + subject.getId() + "/groups")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(newPrivilegeGroup)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").isNotEmpty())
            .andExpect(jsonPath("$.isGlobal").value(false))
            .andExpect(jsonPath("$.status").value("ACTIVE"))
            .andExpect(jsonPath("$.fullNameUkr").value("нова група"))
            .andExpect(jsonPath("$.privileges[0].code").value("V1_1_1"))
            .andExpect(jsonPath("$.privileges[0].fullNameUkr").value("privilege1"))
            .andExpect(jsonPath("$.privileges[1].code").value("V2_1_1"))
            .andExpect(jsonPath("$.privileges[1].fullNameUkr").value("privilege2"));
    }


    @Test
    @DisplayName("Correctly update local privilege group")
    @WithMockCustomUser
    @Transactional
    void updatePrivilegeGroup() throws Exception {
        // GIVEN
        var user = user();
        em.persist(user);

        var subjectRepresent = statehoodSubjectRepresent(em, user);
        em.persist(subjectRepresent);

        var subject = subjectRepresent.getStatehoodSubject();

        var c1 = new PrivilegeCategory("C")
            .setGlobal(false)
            .setFullNameUkr("setFullNameUkr")
            .setFullNameEng("setFullNameEng")
            .setPrivilegeCategoryType(PrivilegeType.LEGAL_ENTITY);
        em.persist(c1);
        // Create privilege
        var p1 = new Privilege("V1_1_1")
            .setPrivilegeCategory(c1)
            .setPrivilegeType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("privilege1");
        var p2 = new Privilege("V2_1_1")
            .setPrivilegeCategory(c1)
            .setPrivilegeType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("privilege2");
        var p3 = new Privilege("V3_1_1")
            .setPrivilegeCategory(c1)
            .setPrivilegeType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("privilege3");
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);

        var group = new PrivilegeGroup("GROUP1", new HashSet<>(Set.of(p1, p2, p3)))
            .setPrivilegeGroupType(PrivilegeType.LEGAL_ENTITY)
            .setFullNameUkr("ukr")
            .setFullNameEng("eng")
            .setStatehoodSubject(subject)
            .setGlobal(false);
        em.persist(group);

        em.flush();

        TestSecurityUtils.setSecurityContext(user.getId());

        // Create privilege group update dto
        var privilegeGroupChanges = new PrivilegeGroupWithPrivilegesDTO();
        privilegeGroupChanges.setPrivileges(List.of(
            new PrivilegeDTO("V2_1_1"),
            new PrivilegeDTO("V3_1_1")
        ));
        privilegeGroupChanges.setCode("GROUP1");
        privilegeGroupChanges.setFullNameUkr("нова група оновлена");

        // WHEN
        mvc.perform(put("/api/privileges-and-groups/subject/" + subject.getId() + "/groups")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(privilegeGroupChanges)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").isNotEmpty())
            .andExpect(jsonPath("$.isGlobal").value(false))
            .andExpect(jsonPath("$.status").value("ACTIVE"))
            .andExpect(jsonPath("$.fullNameUkr").value("нова група оновлена"))

            .andExpect(jsonPath("$.privileges", hasSize(2)))
            .andExpect(jsonPath("$.privileges[0].code").value("V2_1_1"))
            .andExpect(jsonPath("$.privileges[0].fullNameUkr").value("privilege2"))
            .andExpect(jsonPath("$.privileges[1].code").value("V3_1_1"))
            .andExpect(jsonPath("$.privileges[1].fullNameUkr").value("privilege3"));
    }

}
