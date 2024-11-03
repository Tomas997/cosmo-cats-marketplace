package com.example.cosmocatsmarketplace.controller;

import com.example.cosmocatsmarketplace.common.CustomErrorResponse;
import com.example.cosmocatsmarketplace.util.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.List;

@RestControllerAdvice
public class GlobalExceptionalHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        CustomErrorResponse errorResponse = ValidationUtils.getErrorResponseOfFieldErrors(fieldErrors, request);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}

