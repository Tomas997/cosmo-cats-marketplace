package com.example.cosmocatsmarketplace.controller.exeption;

import com.example.cosmocatsmarketplace.dto.ConstraintViolationProblemDetails;
import com.example.cosmocatsmarketplace.featuretoggle.exeption.FeatureToggleNotEnabledException;
import com.example.cosmocatsmarketplace.service.exeption.CategoryNotFoundException;
import com.example.cosmocatsmarketplace.service.exeption.OrderNotFoundException;
import com.example.cosmocatsmarketplace.service.exeption.ProductNotFoundException;
import com.example.cosmocatsmarketplace.service.exeption.ProductStatusIsInCorrectException;
import jakarta.persistence.PersistenceException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

import static java.net.URI.create;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;


@RestControllerAdvice
public class GlobalExceptionalHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail handleProductNotFoundException(ProductNotFoundException ex) {
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("product-not-found"));
        problemDetail.setTitle("Product Not Found");
        return problemDetail;
    }


    @ExceptionHandler(CategoryNotFoundException.class)
    public ProblemDetail handleCategoryNotFoundException(CategoryNotFoundException ex) {
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("category-not-found"));
        problemDetail.setTitle("Category Not Found");
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ConstraintViolationProblemDetails.of(e));
    }

    @ExceptionHandler(FeatureToggleNotEnabledException.class)
    ProblemDetail handleFeatureToggleNotEnabledException(FeatureToggleNotEnabledException ex) {
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(create("feature-disabled"));
        problemDetail.setTitle("Feature is disabled");
        return problemDetail;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ProblemDetail handleOrderNotFoundException(OrderNotFoundException ex) {
        ProblemDetail problemDetail = forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("order-not-found"));
        problemDetail.setTitle("Order Not Found");
        return problemDetail;
    }

    @ExceptionHandler(ProductStatusIsInCorrectException.class)
    public ProblemDetail handleProductStatusException(ProductStatusIsInCorrectException ex) {
        ProblemDetail problemDetail = forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setType(URI.create("product-status-incorrect"));
        problemDetail.setTitle("Product Status Incorrect");
        return problemDetail;
    }

    @ExceptionHandler(PersistenceException.class)
    public ProblemDetail handlePersistenceException(PersistenceException ex) {
        ProblemDetail problemDetail = forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setType(URI.create("persistence-error"));
        problemDetail.setTitle("Persistence Error");
        return problemDetail;
    }
}

