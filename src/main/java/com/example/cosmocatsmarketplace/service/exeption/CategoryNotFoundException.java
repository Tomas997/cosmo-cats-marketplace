package com.example.cosmocatsmarketplace.service.exeption;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(long id) {
        super(String.format("Category With ID - %s Not Found", id));
    }
}
