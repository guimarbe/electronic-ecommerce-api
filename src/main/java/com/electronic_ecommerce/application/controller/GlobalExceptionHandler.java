package com.electronic_ecommerce.application.controller;

import com.electronic_ecommerce.application.Exceptions.DiscountApplicationException;
import com.electronic_ecommerce.application.Exceptions.InvalidSortFieldException;
import com.electronic_ecommerce.application.Exceptions.ProductApplicationException;
import com.electronic_ecommerce.application.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidSortFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidSortFieldException(InvalidSortFieldException e) {
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST, "Sort Field Exception", List.of(e.getMessage()));
    }

    @ExceptionHandler(ProductApplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleProductApplicationException(ProductApplicationException e) {
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Product Application Exception", List.of(e.getMessage()));
    }

    @ExceptionHandler(DiscountApplicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleDiscountApplicationException(DiscountApplicationException e) {
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Discount Application Exception", List.of(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleGenericException(Exception e) {
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", List.of(e.getMessage()));
    }

}
