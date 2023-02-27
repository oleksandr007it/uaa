package com.idevhub.tapas.service.utils;

public final class StringTransformUtils {
    private StringTransformUtils() {
    }


    public static String hideStr(String str) {
        return ("" + str).substring(0, 4) + "***********";
    }

    public static String removePunct(String str) {
        StringBuilder result = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isAlphabetic(c)) {
                result.append(c);
            }
        }
        return result.toString().toUpperCase();
    }
}
