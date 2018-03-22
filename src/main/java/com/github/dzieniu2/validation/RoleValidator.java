package com.github.dzieniu2.validation;

import com.github.dzieniu2.security.Role;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole,String> {

    @Override
    public void initialize(ValidRole constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String role, final ConstraintValidatorContext context) {
        try {
            Role.valueOf(role.toUpperCase());
            return true;
        }catch(IllegalArgumentException|NullPointerException e) {
            return false;
        }
    }
}
