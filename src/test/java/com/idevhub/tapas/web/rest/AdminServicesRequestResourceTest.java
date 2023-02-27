package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.service.AdminServiceRequestService;
import com.idevhub.tapas.service.NotificationService;

import com.idevhub.tapas.service.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AdminServicesRequestResourceTest {
    @Mock
    private NotificationService notificationService;

    @Mock
    private AdminServiceRequestService adminServiceRequestService;

    private AdminServicesRequestResource adminServicesRequestResource;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        adminServicesRequestResource = new AdminServicesRequestResource(adminServiceRequestService, notificationService);
    }

    @Test
    public void testGetAdminServiceRequestData() {
        final AdminServiceRequestFullRespDTO actualResult = getStubAdminServiceRequestFullRespDTO();

        when(adminServiceRequestService.getFullRespDTO(anyLong(), anyLong())).thenReturn(actualResult);

        final AdminServiceRequestFullRespDTO expectedResult = adminServicesRequestResource.getAdminServiceRequestData(1L, 2L);

        assertEquals(actualResult, expectedResult);
        assertThat(expectedResult.getEmail()).isNotEmpty();
        assertThat(expectedResult.getPhone()).isNotEmpty();
    }

    @Test
    public void testGetCurrentUserAdminServiceRequestData() {
        final AdminServiceRequestFullRespDTO actualResult = getStubAdminServiceRequestFullRespDTO();

        when(adminServiceRequestService.getCurrentUserFullRespDTO(anyLong())).thenReturn(actualResult);

        final AdminServiceRequestFullRespDTO expectedResult = adminServicesRequestResource.getCurrentUserAdminServiceRequestData(1L);

        assertEquals(actualResult, expectedResult);
        assertThat(expectedResult.getEmail()).isNotEmpty();
        assertThat(expectedResult.getPhone()).isNotEmpty();
    }

    @Test
    public void testGetASRBrokerageCreateData() {
        final AdminServiceRequestBrokerageCreateDTO actualResult = getStubAdminServiceRequestBrokerageCreateDTO();

        when(adminServiceRequestService.getASRBrokerageCreateData()).thenReturn(actualResult);

        final AdminServiceRequestBrokerageCreateDTO expectedResult = adminServicesRequestResource.getASRBrokerageCreateData();

        assertEquals(actualResult, expectedResult);
        assertThat(expectedResult.getSubjectEmail()).isNotEmpty();
        assertThat(expectedResult.getHeadFullName()).isNotEmpty();
    }

    @Test
    public void testGetASRWarehouseCreateData() {
        final AdminServiceRequestWarehouseDTO actualResult = getStubAdminServiceRequestWarehouseDTO();

        when(adminServiceRequestService.getASRWarehouseCreateData()).thenReturn(actualResult);

        final AdminServiceRequestWarehouseDTO expectedResult = adminServicesRequestResource.getASRWarehouseCreateData();

        assertEquals(actualResult, expectedResult);
        assertThat(expectedResult.getEdrpou()).isNotEmpty();
        assertThat(expectedResult.getRegistrationAddress()).isNotEmpty();
        assertThat(expectedResult.getApplicantTelNumber()).isNotEmpty();
        assertThat(expectedResult.getEmail()).isNotEmpty();
        assertThat(expectedResult.getSubjectFullName()).isNotEmpty();
        assertThat(expectedResult.getSubjectShortName()).isNotEmpty();
        assertThat(expectedResult.getHeadFullName()).isNotEmpty();
    }

    @Test
    public void testGetASRBrokerageUpdateData() {
        final List<AdminServiceRequestBrokerageUpdateDTO> actualResult = List.of(getStubAdminServiceRequestBrokerageUpdateDTO());

        when(adminServiceRequestService.getASRBrokerageUpdateData(any(Set.class))).thenReturn(actualResult);

        final List<AdminServiceRequestBrokerageUpdateDTO> expectedResult = adminServicesRequestResource.getASRBrokerageUpdateData(Set.of(0L));

        assertEquals(actualResult, expectedResult);
        assertThat(expectedResult.get(0).getEdrpou()).isNotEmpty();
        assertThat(expectedResult.get(0).getLegalAddress()).isNotEmpty();
    }

    @Test
    public void testGetASRWarehouseUpdateData() {
        final List<AdminServiceRequestWarehouseDTO> actualResult = List.of(getStubAdminServiceRequestWarehouseDTO());

        when(adminServiceRequestService.getASRWarehouseUpdateData(any(Set.class))).thenReturn(actualResult);

        final List<AdminServiceRequestWarehouseDTO> expectedResult = adminServicesRequestResource.getASRWarehouseUpdateData(Set.of(0L));

        assertEquals(actualResult, expectedResult);
        assertThat(expectedResult.get(0).getEdrpou()).isNotEmpty();
        assertThat(expectedResult.get(0).getRegistrationAddress()).isNotEmpty();
        assertThat(expectedResult.get(0).getApplicantTelNumber()).isNotEmpty();
        assertThat(expectedResult.get(0).getEmail()).isNotEmpty();
        assertThat(expectedResult.get(0).getSubjectFullName()).isNotEmpty();
        assertThat(expectedResult.get(0).getSubjectShortName()).isNotEmpty();
        assertThat(expectedResult.get(0).getHeadFullName()).isNotEmpty();
    }

    @Test
    public void testGetDataToApproveRequest() {
        final DataToApproveRequestDTO actualResult = getStubDataToApproveRequestDTO();

        when(adminServiceRequestService.getDataToApproveRequest()).thenReturn(actualResult);

        final DataToApproveRequestDTO expectedResult = adminServicesRequestResource.getDataToApproveRequest();

        assertEquals(actualResult, expectedResult);
        assertThat(expectedResult.getEdrpouStr()).isNotEmpty();
        assertThat(expectedResult.getRnokppStr()).isNotEmpty();
    }

    @Test
    public void testSendRequestApproveEmail() {
        final RequestApproveOrReissueDTO actualResult = getStubRequestApproveOrReissueDTO();

        final ResponseEntity<Void> state = adminServicesRequestResource.sendRequestApproveEmail(actualResult);

        assertThat(state).isNotNull();
    }

    @Test
    public void testSendRequestReissueEmail() {
        final RequestApproveOrReissueDTO actualResult = getStubRequestApproveOrReissueDTO();

        final ResponseEntity<Void> state = adminServicesRequestResource.sendRequestReissueEmail(actualResult);

        assertThat(state).isNotNull();
    }

    @Test
    public void testSendRequestSuspenseEmail() {
        final RequestSuspenseOrRevocationDTO actualResult = getStubRequestSuspenseOrRevocationDTO();

        final ResponseEntity<Void> state = adminServicesRequestResource.sendRequestSuspenseEmail(actualResult);

        assertThat(state).isNotNull();
    }

    @Test
    public void testSendRequestRevocationEmail() {
        final RequestSuspenseOrRevocationDTO actualResult = getStubRequestSuspenseOrRevocationDTO();

        final ResponseEntity<Void> state = adminServicesRequestResource.sendRequestRevocationEmail(actualResult);

        assertThat(state).isNotNull();
    }

    private RequestSuspenseOrRevocationDTO getStubRequestSuspenseOrRevocationDTO() {
        final RequestSuspenseOrRevocationDTO dto = new RequestSuspenseOrRevocationDTO();
        dto.setApplicantId(1L);
        dto.setStatehoodSubjectId(2L);

        return dto;
    }

    private RequestApproveOrReissueDTO getStubRequestApproveOrReissueDTO() {
        final RequestApproveOrReissueDTO dto = new RequestApproveOrReissueDTO();
        dto.setApplicantId(1L);
        dto.setBrokerRegNumber("broker_reg_number");
        dto.setEdrpouFromRegister("edrpou");
        dto.setLegalAddressFromRegister("legal_address");
        dto.setRecordingToRegisterDate("recording_to_register_date");
        dto.setStatehoodSubjectId(2L);
        dto.setSubjectFullNameFromRegister("subject_full_name");

        return dto;
    }

    private DataToApproveRequestDTO getStubDataToApproveRequestDTO() {
        final DataToApproveRequestDTO dto = new DataToApproveRequestDTO();
        dto.setEdrpouStr("edrpou");
        dto.setRnokppStr("rnokpp");

        return dto;
    }

    private AdminServiceRequestBrokerageUpdateDTO getStubAdminServiceRequestBrokerageUpdateDTO() {
        final AdminServiceRequestBrokerageUpdateDTO dto = new AdminServiceRequestBrokerageUpdateDTO();
        dto.setEdrpou("edrpou");
        dto.setKopfg("kopfg");
        dto.setSubjectName("subject_name");
        dto.setHeadFullName("head_full_name");
        dto.setLegalAddress("legal_address");
        dto.setKoatuu("koatuu");
        dto.setSubjectTelNumber("0123456789");
        dto.setSubjectEmail("http://test@mail.com");
        dto.setStatehoodSubjectId(0L);
        dto.setDeclarantId(1L);
        dto.setCreatedBy("created_by");
        dto.setExecutedBy("executed_by");

        return dto;
    }

    private AdminServiceRequestWarehouseDTO getStubAdminServiceRequestWarehouseDTO() {
        final AdminServiceRequestWarehouseDTO dto = new AdminServiceRequestWarehouseDTO();
        dto.setEdrpou("edrpou");
        dto.setSubjectFullName("subject_name");
        dto.setSubjectShortName("subject_short_name");
        dto.setHeadFullName("head_full_name");
        dto.setRegistrationAddress("address.1");
        dto.setStatehoodSubjectId(0L);
        dto.setApplicantTelNumber("380631234567");
        dto.setEmail("abc@gmail.com");

        return dto;
    }

    private AdminServiceRequestFullRespDTO getStubAdminServiceRequestFullRespDTO() {
        final AdminServiceRequestFullRespDTO dto = new AdminServiceRequestFullRespDTO();
        dto.setRNOKPP("RNOKPP");
        dto.setPhone("phone");
        dto.setEmail("email");
        dto.setEmailApproved(false);
        dto.setPassport("passport");
        dto.setPassportGivenBy("passportGivenBy");
        dto.setPassportGivenAt(Instant.ofEpochSecond(0L));
        dto.setRegistrationAddress("registrationAddress");
        dto.setPosition("position");
        dto.setDeclarantId(0L);

        return dto;
    }

    private AdminServiceRequestBrokerageCreateDTO getStubAdminServiceRequestBrokerageCreateDTO() {
        final AdminServiceRequestBrokerageCreateDTO dto = new AdminServiceRequestBrokerageCreateDTO();
        dto.setId(1L);
        dto.setEdrpou("edrpou");
        dto.setKopfg("kopfg");
        dto.setSubjectName("subject_name");
        dto.setHeadFullName("head_hull_name");
        dto.setLegalAddress("legal_address");
        dto.setKoatuu("koatuu");
        dto.setSubjectTelNumber("0123456789");
        dto.setSubjectEmail("http://test@mail.com");
        dto.setStatehoodSubjectId(1L);
        dto.setDeclarantId(2L);
        dto.setCreatedBy("created_by");
        dto.setExecutedBy("executed_by");

        return dto;
    }
}
