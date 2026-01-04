package com.zenith.catalog.catalog_service.exception;

import com.zenith.catalog.catalog_service.dto.ErroeResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroeResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request){
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() +" : " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation Failed", errorMessage, request);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErroeResponse> handleProductNotFound(ProductNotFoundException ex, HttpServletRequest request){
        return buildResponse(HttpStatus.NOT_FOUND, "Product Not Found", ex.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroeResponse> handleGlobalException(Exception ex, HttpServletRequest request){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error occurred, Please try again later.", request);
    }

    private ResponseEntity<ErroeResponse> buildResponse(HttpStatus status, String error, String message, HttpServletRequest request){
        ErroeResponse response = new ErroeResponse(message, LocalDateTime.now(), status.value(), error, request.getRequestURI());
        return new ResponseEntity<>(response, status);
    }
}
