package com.example.cosmocatsmarketplace.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SpaceDescriptionValidator.class)
@Documented
public @interface ValidSpaceDescription {
    String MESSAGE = "Invalid Space Title: Space Title must contain: star | galaxy | comet | nebula | planet | black hole | universe";

    String message() default MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
