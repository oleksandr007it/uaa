package com.idevhub.tapas.service;

import com.idevhub.tapas.service.dto.*;

import java.util.List;
import java.util.Set;

public interface AdminServiceRequestService {
    AdminServiceRequestFullRespDTO getFullRespDTO(Long declarantId, Long statehoodSubjectId);

    AdminServiceRequestFullRespDTO getCurrentUserFullRespDTO(Long statehoodSubjectId);

    AdminServiceRequestBrokerageCreateDTO getASRBrokerageCreateData();

    AdminServiceRequestWarehouseDTO getASRWarehouseCreateData();

    List<AdminServiceRequestBrokerageUpdateDTO> getASRBrokerageUpdateData(Set<Long> subjectsIds);

    List<AdminServiceRequestWarehouseDTO> getASRWarehouseUpdateData(Set<Long> subjectsIds);

    DataToApproveRequestDTO getDataToApproveRequest();

    DataToAppointRequestDTO getDataToAppointRequest(Long executantId);

    Long getOrgHeadId();
}
