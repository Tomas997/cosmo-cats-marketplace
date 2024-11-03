package com.example.cosmocatsmarketplace.util;

import com.example.cosmocatsmarketplace.common.CustomErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;



public class ValidationUtils {
    public static CustomErrorResponse getErrorResponseOfFieldErrors(List<FieldError> fieldErrors, HttpServletRequest request) {
        return CustomErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(fieldErrors.stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining(", ")))
                .path(request.getRequestURI())
                .build();
    }
}
