package com.idevhub.tapas.service.validation.validators;


import com.idevhub.tapas.service.validation.ValidEdrpou;

import javax.annotation.processing.Generated;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidEdrpouValidator implements ConstraintValidator<ValidEdrpou, String> {
    private final Pattern itnRegexp;

    @Generated(value = "This is generate a sample java function")
    public ValidEdrpouValidator() {
        String regexp = "[0-9]{7,10}";
        itnRegexp = Pattern.compile(regexp);
    }

    @Override
    @Generated(value = "This is generate a sample java function")
    public boolean isValid(String s,
                           ConstraintValidatorContext constraintValidatorContext) {

        if (s == null)
            return true;

        Matcher matcher = itnRegexp.matcher(s);
        return matcher.matches();
    }
}
