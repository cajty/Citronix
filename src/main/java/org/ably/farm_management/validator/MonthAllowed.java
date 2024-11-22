package org.ably.farm_management.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Month;


@Constraint(validatedBy = MonthAllowedValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MonthAllowed {
    Month[] value() default {Month.MARCH, Month.MAY}; // Default allowed months

    String message() default " not allowed in this month";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
