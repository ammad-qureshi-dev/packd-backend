package com.packd.server_api.exceptions;


import com.packd.server_api.controllers.dto.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(ApiErrorResponse.builder().errorCode(ex.getExceptionCode())
                .severityType(ex.getExceptionType()).status(ex.getStatus()).message(ex.getMessage()).build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ApiErrorResponse>> handleConstraintViolation(ConstraintViolationException ex) {

        var errors = ex.getConstraintViolations()
                .stream()
                .map(cv -> ApiErrorResponse.builder()
                        .errorCode(ExceptionCode.VALIDATION_FAILED)
                        .severityType(ExceptionType.ERROR)
                        .message(cv.getMessage())
                        .build())
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}

