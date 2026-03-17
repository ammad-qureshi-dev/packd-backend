package com.packd.server_api.validations.annotations;

import com.packd.server_api.validations.OverlappingDatesValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OverlappingDatesValidation.class)
@Documented
public @interface OverlappingDates {
    String message() default "Dates overlap";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
