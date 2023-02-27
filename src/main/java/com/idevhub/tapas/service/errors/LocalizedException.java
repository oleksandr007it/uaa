package com.idevhub.tapas.service.errors;

import com.idevhub.tapas.config.Constants;
import org.springframework.context.i18n.LocaleContextHolder;
import org.thymeleaf.util.ArrayUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class LocalizedException extends RuntimeException {

    private final String messagePath;
    private String[] params;

    public LocalizedException(LocalizedException ex) {
        super(ex.messagePath);
        this.messagePath = ex.messagePath;
        this.params = ex.params;
    }

    public LocalizedException(String messagePath) {
        super(messagePath);
        this.messagePath = messagePath;
    }

    public LocalizedException(String messagePath, String... params) {
        super(messagePath);
        this.messagePath = messagePath;
        this.params = params;
    }

    public LocalizedException(String messagePath, Throwable cause) {
        super(messagePath, cause);
        this.messagePath = messagePath;
    }

    public LocalizedException(String messagePath, Throwable cause, String... params) {
        super(messagePath, cause);
        this.messagePath = messagePath;
        this.params = params;
    }

    public static String getLocalizedString(String messagePath, String... params) {
        ResourceBundle localizedErrorMessages =
            ResourceBundle.getBundle(
                Constants.I18N_BUNDLE_RESOURCES_PATH,
                LocaleContextHolder.getLocale()
            );

        Object[] parameters;
        if (null == params)
            parameters = new Object[]{};
        else
            parameters = ArrayUtils.toArray(params);

        String localMessageFormatString = localizedErrorMessages.getString(messagePath);
        String localMessageFormatStringUTF8;
        localMessageFormatStringUTF8 = new String(localMessageFormatString.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

        return MessageFormat.format(localMessageFormatStringUTF8, parameters);
    }

    public String getLocalizedMessage() {
        return getLocalizedString(this.messagePath, params);
    }

    @Override
    public String toString() {
        return getLocalizedMessage();
    }

}

