package org.ably.farm_management.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MonthAllowedValidator implements ConstraintValidator<MonthAllowed, LocalDate> {
    private List<Month> allowedMonths;

    @Override
    public void initialize(MonthAllowed constraintAnnotation) {
        allowedMonths = Arrays.asList(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return false;
        }
        return allowedMonths.contains(date.getMonth());
    }
}