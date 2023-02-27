package com.idevhub.tapas.service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idevhub.tapas.service.dto.SignForApprove;
import com.idevhub.tapas.service.feign.RemoteCryptographyServiceClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ua.idevhub.dto.SignerInfo;
import ua.idevhub.dto.VerifyByDataDTO;

import java.time.Instant;
import java.util.Base64;

@Slf4j
public final class CryptoUtils {
    private CryptoUtils() {
    }




    @SneakyThrows
    public static String makeBase64ForSign(String code, String name, String action){
        SignForApprove signForApprove = new SignForApprove();
        signForApprove.setAction(action);
        signForApprove.setLegalEntityCode(code);
        signForApprove.setLegalEntityName(name);
        signForApprove.setInitiationTimestamp(Instant.now());
        ObjectMapper objectMapper = new ObjectMapper();
        String signForApproveJson = objectMapper.writeValueAsString(signForApprove);
        String signForApproveBase64 = Base64.getEncoder().encodeToString(signForApproveJson.getBytes());
        return signForApproveBase64;
    }


    public static SignerInfo verifyByData(String rawDataBase64, String signedBase64, RemoteCryptographyServiceClient remoteCryptographyServiceClient) {
        VerifyByDataDTO verifyByDataDTO = new VerifyByDataDTO();
        verifyByDataDTO.setDataBase64(rawDataBase64);
        verifyByDataDTO.setDigitalSignatureBase64(signedBase64);
        try {
            SignerInfo signerInfo = remoteCryptographyServiceClient.verifyByData(verifyByDataDTO);
            return signerInfo;
        } catch (Exception e) {
            log.error("Verify Sinature error = {}", e.getMessage());
            throw new RuntimeException("Verify Sinature error");
        }
    }
}
