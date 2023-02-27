package com.idevhub.tapas.service.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CompareUtils {
    private CompareUtils() {
    }

    private static final String NOT_EQUALS_EDORPOU = "Request Code  not Equals Edrpou or Rnokpp   from  sign";
    private static final String NOT_EQUALS_RNOKPP = "Declarant full Name or Rnokpp not Equals data  from  sign";

    public static void compareRnokppAndFullName(String rnokppFromSign, String declarantRnokpp, String fullNameFromSign, String declarantFullName) {

        boolean isRnokppEquals = rnokppFromSign.equals(declarantRnokpp);
        boolean isFullNameEquals = fullNameFromSign.equals(declarantFullName);

        if (!(isRnokppEquals) || !(isFullNameEquals)) {
            log.error(NOT_EQUALS_RNOKPP);
            throw new RuntimeException(NOT_EQUALS_RNOKPP);
        }
    }


    public static void compareRnokppAndeEdrpouStatehoodSubject(String rnokppFromSign, String edrpouStatehoodSubject, String edrpouFromSign) {
        boolean isEdrpouEquals = edrpouStatehoodSubject.equals(edrpouFromSign);
        boolean isRnokppEqualsEdrpouStatehoodSubject = edrpouStatehoodSubject.equals(rnokppFromSign);
        if (edrpouFromSign != null && !edrpouFromSign.trim().isEmpty() && !isEdrpouEquals) {
            log.error(NOT_EQUALS_EDORPOU);
            throw new RuntimeException(NOT_EQUALS_EDORPOU);
        }
        if ((edrpouFromSign == null || edrpouFromSign.trim().isEmpty()) && !isRnokppEqualsEdrpouStatehoodSubject) {
            log.error(NOT_EQUALS_EDORPOU);
            throw new RuntimeException(NOT_EQUALS_EDORPOU);
        }
    }
}
