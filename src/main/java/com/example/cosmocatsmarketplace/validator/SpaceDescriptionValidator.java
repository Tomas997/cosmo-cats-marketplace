package com.example.cosmocatsmarketplace.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class SpaceDescriptionValidator implements ConstraintValidator<ValidSpaceDescription, String> {


    private static final String TITLE_KEYWORD_PATTERN = "(?i)\\b(star|galaxy|comet|nebula|planet|black hole|universe)\\b";

    private static final Pattern pattern = Pattern.compile(TITLE_KEYWORD_PATTERN);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && pattern.matcher(value).find();
    }
}
