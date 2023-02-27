package com.idevhub.tapas.service.impl;

import com.idevhub.tapas.UaaApp;
import com.idevhub.tapas.domain.StatehoodSubject;
import com.idevhub.tapas.domain.StatehoodSubjectRepresent;
import com.idevhub.tapas.domain.User;
import com.idevhub.tapas.domain.constants.UserStatus;
import com.idevhub.tapas.domain.enumeration.StatehoodSubjectRepresentStatus;
import com.idevhub.tapas.repository.StatehoodSubjectRepresentRepository;
import com.idevhub.tapas.security.AuthoritiesConstants;
import com.idevhub.tapas.security.utils.TestSecurityUtils;
import com.idevhub.tapas.service.StatehoodSubjectRepresentLookupService;
import com.idevhub.tapas.service.StatehoodSubjectService;
import com.idevhub.tapas.service.dto.StatehoodSubjectRepresentUpdateStatusDTO;
import com.idevhub.tapas.service.errors.WrongRepresentStatusException;
import com.idevhub.tapas.service.feign.CBackDictionariesClient;
import com.idevhub.tapas.service.feign.RemoteCryptographyServiceClient;
import com.idevhub.tapas.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ua.idevhub.dto.SignerInfo;
import ua.idevhub.dto.VerifyByDataDTO;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApp.class)
@WithMockUser(value = "admin", username = "admin", authorities = AuthoritiesConstants.ADMIN)
class StatehoodSubjectRepresentOperationServiceIT {

    @MockBean(name = "com.idevhub.tapas.service.feign.CBackDictionariesClient")
    private CBackDictionariesClient mock_dictionariesClient;

    @Autowired
    private StatehoodSubjectRepresentRepository mockStatehoodSubjectRepresentRepository;
    @Autowired
    private StatehoodSubjectRepresentLookupService mockStatehoodSubjectRepresentLookupService;
    @Mock
    private RemoteCryptographyServiceClient mockRemoteCryptographyServiceClient;
    @Autowired
    private StatehoodSubjectService mockStatehoodSubjectService;

    private StatehoodSubjectRepresentOperationService statehoodSubjectRepresentOperationServiceUnderTest;

    private static final String EDRPOU = "12345678";
    private static final String RNOKPP = "1234567890";

    @Autowired
    private EntityManager em;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        statehoodSubjectRepresentOperationServiceUnderTest = new StatehoodSubjectRepresentOperationService(mockStatehoodSubjectRepresentRepository, mockRemoteCryptographyServiceClient, mockStatehoodSubjectService);
    }

    private SignerInfo createSignerInfo(String edrpou, String rnokppo) {
        SignerInfo signerInfo = new SignerInfo();
        signerInfo.setOkpo_(edrpou);
        signerInfo.setGrfl_(rnokppo);
        signerInfo.setCommon_name_("Петренко павло Павло:%вич");
        return signerInfo;
    }




    @Test
    @Transactional
    @DisplayName("create StatehoodSubject and Represeter and make rawSignBase64")
    void testChekRepresenterAndReturnDataForSign() {
        StatehoodSubject statehoodSubject = EntityUtils.createStatehoodSubject(UserStatus.ACTIVE,em,EDRPOU);
        User declarant = EntityUtils.createEntityUser(RNOKPP, EDRPOU,em);
        StatehoodSubjectRepresent statehoodSubjectRepresent = EntityUtils.createRepresenterAndSave(statehoodSubject, declarant, StatehoodSubjectRepresentStatus.INACTIVE,em);
        TestSecurityUtils.setSecurityContext(declarant.getId());
        String rawSignBase64 = statehoodSubjectRepresentOperationServiceUnderTest.chekRepresenterAndReturnDataForSign(statehoodSubject.getId());
        assertFalse(rawSignBase64.isEmpty());
        em.remove(statehoodSubject);
        em.remove(declarant);
        em.remove(statehoodSubjectRepresent);
    }

    @Test
    @Transactional
    @DisplayName("create StatehoodSubject with BLOCKED  status and Represeter and trow exceptions")
    void testChekRepresenterAndReturnDataForSignEx() {
        StatehoodSubject statehoodSubject = EntityUtils.createStatehoodSubject(UserStatus.BLOCKED,em,EDRPOU);
        User declarant = EntityUtils.createEntityUser(RNOKPP, EDRPOU,em);
        StatehoodSubjectRepresent statehoodSubjectRepresent = EntityUtils.createRepresenterAndSave(statehoodSubject, declarant, StatehoodSubjectRepresentStatus.INACTIVE,em);
        TestSecurityUtils.setSecurityContext(declarant.getId());
        assertThatThrownBy(() -> {
            statehoodSubjectRepresentOperationServiceUnderTest.chekRepresenterAndReturnDataForSign(statehoodSubject.getId());
        }).isInstanceOf(WrongRepresentStatusException.class);
        em.remove(statehoodSubject);
        em.remove(declarant);
        em.remove(statehoodSubjectRepresent);
    }

    @Test
    @Transactional
    @DisplayName("create StatehoodSubject and Represeter with ACTIVE status  and trow exceptions")
    void testChekRepresenterAndReturnDataForSignEx2() {
        StatehoodSubject statehoodSubject = EntityUtils.createStatehoodSubject(UserStatus.ACTIVE,em,EDRPOU);
        User declarant = EntityUtils.createEntityUser(RNOKPP, EDRPOU,em);
        StatehoodSubjectRepresent statehoodSubjectRepresent = EntityUtils.createRepresenterAndSave(statehoodSubject, declarant, StatehoodSubjectRepresentStatus.ACTIVE,em);
        TestSecurityUtils.setSecurityContext(declarant.getId());
        assertThatThrownBy(() -> {
            statehoodSubjectRepresentOperationServiceUnderTest.chekRepresenterAndReturnDataForSign(statehoodSubject.getId());
        }).isInstanceOf(WrongRepresentStatusException.class);
        em.remove(statehoodSubject);
        em.remove(declarant);
        em.remove(statehoodSubjectRepresent);
    }


    @Test
    @Transactional
    @DisplayName("Update Representer Status to ACTIVE")
    void testUpdateRepresenterStatus() throws Exception {

        SignerInfo signerInfo = createSignerInfo(EDRPOU, RNOKPP);
        when(mockRemoteCryptographyServiceClient.verifyByData(any(VerifyByDataDTO.class))).thenReturn(signerInfo);
        StatehoodSubject statehoodSubject = EntityUtils.createStatehoodSubject(UserStatus.ACTIVE,em,EDRPOU);
        User declarant = EntityUtils.createEntityUser(RNOKPP, EDRPOU,em);
        StatehoodSubjectRepresent statehoodSubjectRepresent = EntityUtils.createRepresenterAndSave(statehoodSubject, declarant, StatehoodSubjectRepresentStatus.INACTIVE,em);
        TestSecurityUtils.setSecurityContext(declarant.getId());
        StatehoodSubjectRepresentUpdateStatusDTO in = new StatehoodSubjectRepresentUpdateStatusDTO();
        in.setRawSign("raw");
        in.setSignedBase64("signed");
        in.setSubjectId(statehoodSubject.getId());
        StatehoodSubjectRepresent result = statehoodSubjectRepresentOperationServiceUnderTest.updateRepresenterStatus(in);
        assertEquals(result.getSubjectRepresentStatus(), StatehoodSubjectRepresentStatus.ACTIVE);
        em.remove(statehoodSubject);
        em.remove(declarant);
        em.remove(statehoodSubjectRepresent);
    }

    @Test
    @Transactional
    @DisplayName("Update Representer Status to ACTIVE with empty EDRPOU")
    void testUpdateRepresenterStatusEmptyEdrpou() throws Exception {

        SignerInfo signerInfo = createSignerInfo("", EDRPOU);
        when(mockRemoteCryptographyServiceClient.verifyByData(any(VerifyByDataDTO.class))).thenReturn(signerInfo);
        StatehoodSubject statehoodSubject = EntityUtils.createStatehoodSubject(UserStatus.ACTIVE,em,EDRPOU);
        User declarant = EntityUtils.createEntityUser(EDRPOU, EDRPOU,em);
        StatehoodSubjectRepresent statehoodSubjectRepresent = EntityUtils.createRepresenterAndSave(statehoodSubject, declarant, StatehoodSubjectRepresentStatus.INACTIVE,em);
        TestSecurityUtils.setSecurityContext(declarant.getId());
        StatehoodSubjectRepresentUpdateStatusDTO in = new StatehoodSubjectRepresentUpdateStatusDTO();
        in.setRawSign("raw");
        in.setSignedBase64("signed");
        in.setSubjectId(statehoodSubject.getId());
        StatehoodSubjectRepresent result = statehoodSubjectRepresentOperationServiceUnderTest.updateRepresenterStatus(in);
        assertEquals(result.getSubjectRepresentStatus(), StatehoodSubjectRepresentStatus.ACTIVE);
        em.remove(statehoodSubject);
        em.remove(declarant);
        em.remove(statehoodSubjectRepresent);
    }


    @Test
    @Transactional
    @DisplayName("Update Representer Status to ACTIVE with empty EDRPOU and wrong RNOKPP from cert")
    void testUpdateRepresenterStatusEmptyEdrpouWrongRnEX() throws Exception {

        SignerInfo signerInfo = createSignerInfo("", RNOKPP);
        when(mockRemoteCryptographyServiceClient.verifyByData(any(VerifyByDataDTO.class))).thenReturn(signerInfo);
        StatehoodSubject statehoodSubject = EntityUtils.createStatehoodSubject(UserStatus.ACTIVE,em,EDRPOU);
        User declarant = EntityUtils.createEntityUser(EDRPOU, EDRPOU,em);
        StatehoodSubjectRepresent statehoodSubjectRepresent = EntityUtils.createRepresenterAndSave(statehoodSubject, declarant, StatehoodSubjectRepresentStatus.INACTIVE,em);
        TestSecurityUtils.setSecurityContext(declarant.getId());
        StatehoodSubjectRepresentUpdateStatusDTO in = new StatehoodSubjectRepresentUpdateStatusDTO();
        in.setRawSign("raw");
        in.setSignedBase64("signed");
        in.setSubjectId(statehoodSubject.getId());
        assertThatThrownBy(() -> {
            statehoodSubjectRepresentOperationServiceUnderTest.updateRepresenterStatus(in);
        }).isInstanceOf(RuntimeException.class);
        em.remove(statehoodSubject);
        em.remove(declarant);
        em.remove(statehoodSubjectRepresent);
    }


    @Test
    @Transactional
    @DisplayName("UUpdate Representer Status to ACTIVE with empty EDRPOU and wrong RNOKPP from user")
    void testUpdateRepresenterStatusEmptyEdrpouEX() throws Exception {

        SignerInfo signerInfo = createSignerInfo("", EDRPOU);
        when(mockRemoteCryptographyServiceClient.verifyByData(any(VerifyByDataDTO.class))).thenReturn(signerInfo);
        StatehoodSubject statehoodSubject = EntityUtils.createStatehoodSubject(UserStatus.ACTIVE,em,EDRPOU);
        User declarant = EntityUtils.createEntityUser(RNOKPP, EDRPOU,em);
        StatehoodSubjectRepresent statehoodSubjectRepresent = EntityUtils.createRepresenterAndSave(statehoodSubject, declarant, StatehoodSubjectRepresentStatus.INACTIVE,em);
        TestSecurityUtils.setSecurityContext(declarant.getId());
        StatehoodSubjectRepresentUpdateStatusDTO in = new StatehoodSubjectRepresentUpdateStatusDTO();
        in.setRawSign("raw");
        in.setSignedBase64("signed");
        in.setSubjectId(statehoodSubject.getId());

        assertThatThrownBy(() -> {
            statehoodSubjectRepresentOperationServiceUnderTest.updateRepresenterStatus(in);
        }).isInstanceOf(RuntimeException.class);


        em.remove(statehoodSubject);
        em.remove(declarant);
        em.remove(statehoodSubjectRepresent);
    }

}
