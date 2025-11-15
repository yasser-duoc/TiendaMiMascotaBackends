package com.tiendamascota.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RutValidatorConstraint.class)
@Documented
public @interface ValidRut {
    String message() default "RUT inválido. Debe estar en formato XX.XXX.XXX-X";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
