package com.example.Personal.Expense.Tracker.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.example.Personal.Expense.Tracker.validator.DobValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstrain {
    String message() default "Invalid date of birth";

    int min();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
