package com.idevhub.tapas.service.validation.validators;


import com.idevhub.tapas.service.validation.ValidPhoneNumber;

import javax.annotation.processing.Generated;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidPhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private final Pattern phoneNumberPattern;

    @Generated(value = "This is generate a sample java function")
    public ValidPhoneNumberValidator() {
        //  String regexp = "^\\+[\\d]{9,15}";
        String regexp = "^[\\d]{9,15}";
        phoneNumberPattern = Pattern.compile(regexp);
    }


    @Override
    @Generated(value = "This is generate a sample java function")
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null)
            return true;

        Matcher matcher = phoneNumberPattern.matcher(s);
        return matcher.matches();
    }


}
