package com.idevhub.tapas.web.rest;

import com.idevhub.tapas.service.AdminServiceRequestService;
import com.idevhub.tapas.service.NotificationService;
import com.idevhub.tapas.service.dto.*;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class AdminServicesRequestResource {
    private final Logger log = LoggerFactory.getLogger(AdminServicesRequestResource.class);

    private final AdminServiceRequestService service;
    private final NotificationService notificationService;

    public AdminServicesRequestResource(AdminServiceRequestService service, NotificationService notificationService) {
        this.service = service;
        this.notificationService = notificationService;
    }

    @GetMapping("/admin-service-request/data")
    @Timed
    public AdminServiceRequestFullRespDTO getAdminServiceRequestData(@RequestParam("declarantId") Long declarantId,
                                                                     @RequestParam("statehoodSubjectId") Long statehoodSubjectId) {
        log.debug("REST request to get all AdminServiceRequest data");
        AdminServiceRequestFullRespDTO target = service.getFullRespDTO(declarantId, statehoodSubjectId);

        return target;
    }

    @GetMapping("/current-admin-service-request/data")
    @Timed
    public AdminServiceRequestFullRespDTO getCurrentUserAdminServiceRequestData(@RequestParam("statehoodSubjectId") Long statehoodSubjectId) {
        log.debug("REST request to get all AdminServiceRequest data");
        AdminServiceRequestFullRespDTO target = service.getCurrentUserFullRespDTO(statehoodSubjectId);

        return target;
    }

    @GetMapping("/admin-service-request/brokerage-create-data")
    @Timed
    public AdminServiceRequestBrokerageCreateDTO getASRBrokerageCreateData() {
        log.debug("REST request to get Brokerage create request data");
        AdminServiceRequestBrokerageCreateDTO target = service.getASRBrokerageCreateData();

        return target;
    }

    @GetMapping("/admin-service-request/warehouse-create-data")
    @Timed
    public AdminServiceRequestWarehouseDTO getASRWarehouseCreateData() {
        log.debug("REST request to get Warehouse create request data");
        AdminServiceRequestWarehouseDTO target = service.getASRWarehouseCreateData();

        return target;
    }

    @GetMapping("/admin-service-request/brokerage-update-data")
    @Timed
    public List<AdminServiceRequestBrokerageUpdateDTO> getASRBrokerageUpdateData(@RequestParam Set<Long> subjectsIds) {
        log.debug("REST request to get Brokerage update request data");
        List<AdminServiceRequestBrokerageUpdateDTO> target = service.getASRBrokerageUpdateData(subjectsIds);

        return target;
    }

    @GetMapping("/admin-service-request/warehouse-update-data")
    @Timed
    public List<AdminServiceRequestWarehouseDTO> getASRWarehouseUpdateData(@RequestParam Set<Long> subjectsIds) {
        log.debug("REST request to get Warehouse update request data");
        List<AdminServiceRequestWarehouseDTO> target = service.getASRWarehouseUpdateData(subjectsIds);

        return target;
    }

    @GetMapping("/statehood-subject-represents/data-to-approve-request")
    @Timed
    public DataToApproveRequestDTO getDataToApproveRequest() {
        log.debug("REST request to get Warehouse update request data");
        DataToApproveRequestDTO target = service.getDataToApproveRequest();

        return target;
    }

    @PostMapping("/asr/email/request-approve")
    @Timed
    public ResponseEntity<Void> sendRequestApproveEmail(@RequestBody RequestApproveOrReissueDTO input) {
        log.debug("REST request to send request approve email to applicant with id :{} ", input.getApplicantId());
        notificationService.saveAproveEmail(input, "email.applications.approve.title", "email.applications.approve.type");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/asr/email/request-reissue")
    @Timed
    public ResponseEntity<Void> sendRequestReissueEmail(@RequestBody RequestApproveOrReissueDTO input) {
        log.debug("REST request to send request reissue email to applicant with id :{} ", input.getApplicantId());
        notificationService.saveAproveEmail(input, "email.applications.reissue.title", "email.applications.reissue.type");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/asr/email/request-suspense")
    @Timed
    public ResponseEntity<Void> sendRequestSuspenseEmail(@RequestBody RequestSuspenseOrRevocationDTO input) {
        log.debug("REST request to send request suspense email to applicant with id :{} ", input.getApplicantId());
        notificationService.saveSuspenseEmail(input, "email.applications.suspense.title", "email.applications.suspense.type");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/asr/email/request-revocation")
    @Timed
    public ResponseEntity<Void> sendRequestRevocationEmail(@RequestBody RequestSuspenseOrRevocationDTO input) {
        log.debug("REST request to send request revocation email to applicant with id :{} ", input.getApplicantId());
        notificationService.saveSuspenseEmail(input, "email.applications.revocation.title", "email.applications.revocation.type");
        return ResponseEntity.ok().build();
    }

    @GetMapping("/asr/data-to-appoint-request")
    @Timed
    public DataToAppointRequestDTO getDataToAppointRequest(@RequestParam("executantId") Long executantId) {
        log.debug("REST request get data to appoint request by executant with id :{} ", executantId);

        DataToAppointRequestDTO target =
            service.getDataToAppointRequest(executantId);

        return target;
    }

    @GetMapping("/asr/org/head-id")
    @Timed
    public Long getOrgHeadId() {
        log.debug("REST request get current logged in user organization head id.");

        Long target = service.getOrgHeadId();

        return target;
    }
}
