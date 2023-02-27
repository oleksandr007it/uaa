package com.idevhub.tapas.service.validation.validators;


import com.idevhub.tapas.service.validation.ValidPassportNo;

import javax.annotation.processing.Generated;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class   ValidPassportNoValidator implements ConstraintValidator<ValidPassportNo, String> {


    @Override
    @Generated(value = "This is generate a sample java function")
    public boolean isValid(String s,
                           ConstraintValidatorContext constraintValidatorContext) {

        if (s == null)
            return true;
        return s.length() == 8 && s.substring(0, 2).matches("^\\p{Lu}\\p{IsCyrillic}${2}") && s.substring(2, 8).matches("\\d{6}") ||
            s.matches("\\d{9}");
    }


}
