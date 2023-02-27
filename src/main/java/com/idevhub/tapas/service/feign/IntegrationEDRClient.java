package com.idevhub.tapas.service.feign;

import com.idevhub.tapas.client.AuthorizedUserFeignClient;
import com.idevhub.tapas.service.dto.SubjectDTO;
import com.idevhub.tapas.service.dto.SubjectMainInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Optional;

@AuthorizedUserFeignClient(name = "integration", url = "integration")
public interface IntegrationEDRClient {

    @GetMapping("/api/trembita/edr/subjects/active/{codeOrPassport}")
    Optional<SubjectMainInfo> getActiveSubjectInfoByCode(@PathVariable("codeOrPassport") @NotBlank String codeOrPassport);

    @GetMapping("/api/trembita/edr/subject/datails/{id}")
    Optional<SubjectDTO> getSubjectDTODetails(@PathVariable("id") @NotNull BigInteger id);
}
