package com.idevhub.tapas.service.impl;


import com.idevhub.tapas.UaaApp;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.utils.TestSecurityUtils;
import com.idevhub.tapas.service.dto.StatehoodSubjectRepresentDTO;
import com.idevhub.tapas.service.dto.StatehoodSubjectRepresentUpdateDTO;
import com.idevhub.tapas.util.EntityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.idevhub.tapas.util.EntityUtils.privilegeInGroup;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApp.class)
@WithMockUser(value = "admin", username = "admin", authorities = AuthoritiesConstants.ADMIN)
public class StatehoodSubjectRepresentServiceImpIT {

    private static final String EDRPOU = "12345678";
    private static final String RNOKPP = "1234567890";

    @Autowired
    private StatehoodSubjectRepresentServiceImpl statehoodSubjectRepresentService;

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    @DisplayName("Update Representer group")
    void testUpdateRepresenterStatus() throws Exception {
        var localGroup = privilegeInGroup(em, "V1_1_2_test", "GR_V1_LEGAL_ENTITY");
        em.persist(localGroup);
        localGroup = privilegeInGroup(em, "V1_1_3_test", "GR_V1_LEGAL_ENTITY99");
        em.persist(localGroup);

        StatehoodSubject statehoodSubject = EntityUtils.createStatehoodSubject(UserStatus.ACTIVE, em, EDRPOU);
        User declarant = EntityUtils.createEntityUser(RNOKPP, EDRPOU, em);
        StatehoodSubjectRepresent statehoodSubjectRepresent = EntityUtils.createRepresenterAndSave(statehoodSubject, declarant, StatehoodSubjectRepresentStatus.ACTIVE, em);
        TestSecurityUtils.setSecurityContext(declarant.getId());
        StatehoodSubjectRepresentUpdateDTO inputDto = new StatehoodSubjectRepresentUpdateDTO();
        inputDto.setRepresenterId(statehoodSubjectRepresent.getId());
        List<String> list = new ArrayList<>();
        list.add("GR_V1_LEGAL_ENTITY99");
        inputDto.setPrivilegeGroupsIds(list);
        StatehoodSubjectRepresentDTO statehoodSubjectRepresentDTO = statehoodSubjectRepresentService.update(inputDto);

        assertEquals(statehoodSubjectRepresentDTO.getPrivilegeGroups().get(0).getCode(), "GR_V1_LEGAL_ENTITY99");
        em.remove(statehoodSubject);
        em.remove(declarant);
        em.remove(statehoodSubjectRepresent);
    }

    @Test
    @Transactional
    @DisplayName("Update Representer group with  Status  INACTIVE")
    void testUpdateRepresenterStatusEx() throws Exception {
        var localGroup = privilegeInGroup(em, "V1_1_2_test", "GR_V1_LEGAL_ENTITY");
        em.persist(localGroup);
        localGroup = privilegeInGroup(em, "V1_1_3_test", "GR_V1_LEGAL_ENTITY99");
        em.persist(localGroup);

        StatehoodSubject statehoodSubject = EntityUtils.createStatehoodSubject(UserStatus.ACTIVE, em, EDRPOU);
        User declarant = EntityUtils.createEntityUser(RNOKPP, EDRPOU, em);
        StatehoodSubjectRepresent statehoodSubjectRepresent = EntityUtils.createRepresenterAndSave(statehoodSubject, declarant, StatehoodSubjectRepresentStatus.INACTIVE, em);
        TestSecurityUtils.setSecurityContext(declarant.getId());
        StatehoodSubjectRepresentUpdateDTO inputDto = new StatehoodSubjectRepresentUpdateDTO();
        inputDto.setRepresenterId(statehoodSubjectRepresent.getId());
        List<String> list = new ArrayList<>();
        list.add("GR_V1_LEGAL_ENTITY99");
        inputDto.setPrivilegeGroupsIds(list);
        assertThatThrownBy(() -> {
            statehoodSubjectRepresentService.update(inputDto);
        }).isInstanceOf(RuntimeException.class);
        em.remove(statehoodSubject);
        em.remove(declarant);
        em.remove(statehoodSubjectRepresent);
    }


}
