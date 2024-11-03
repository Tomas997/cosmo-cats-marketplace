package com.example.cosmocatsmarketplace.validator;

import com.example.cosmocatsmarketplace.domain.Category;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class CategoryValidator implements ConstraintValidator<ValidCategory, List<Category>> {

    @Override
    public boolean isValid(List<Category> categories, ConstraintValidatorContext context) {
        if (categories == null || categories.isEmpty()) {
            return false;
        }


        for (Category category : categories) {
            if (category.getId() <= 0) {
                return false;
            }
            if (category.getName() == null || category.getName().trim().isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
