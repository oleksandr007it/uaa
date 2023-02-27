package com.idevhub.tapas.service.validation.validators;


import com.idevhub.tapas.service.validation.ValidNames;

import javax.annotation.processing.Generated;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidNamesValidator implements ConstraintValidator<ValidNames, String> {
    private final Pattern itnRegexp;

    @Generated(value = "This is generate a sample java function")
    public ValidNamesValidator() {
        String regexp = "^\\p{L}[\\p{L} '-]+$";
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
