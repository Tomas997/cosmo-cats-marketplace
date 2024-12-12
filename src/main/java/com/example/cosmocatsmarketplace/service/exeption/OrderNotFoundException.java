package com.example.cosmocatsmarketplace.service.exeption;

public class OrderNotFoundException extends RuntimeException {
    private static final String ORDER_NOT_FOUND_MESSAGE = "Order With ID - %s Not Found";

    public OrderNotFoundException(String id) {
        super(String.format(ORDER_NOT_FOUND_MESSAGE, id));
    }
}
