package com.tiendamascota.validation;

import com.tiendamascota.util.RutValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RutValidatorConstraint implements ConstraintValidator<ValidRut, String> {
    
    @Override
    public void initialize(ValidRut constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return RutValidator.esValido(value);
    }
}
