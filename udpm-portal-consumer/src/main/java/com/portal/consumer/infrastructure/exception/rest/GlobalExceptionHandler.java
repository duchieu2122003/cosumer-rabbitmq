package com.portal.consumer.infrastructure.exception.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @author thangncph26123
 */
@RestControllerAdvice
public final class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerException(Exception ex) throws IOException {
        if (ex instanceof RestApiException) {
            ApiError apiError = new ApiError(ex.getMessage());
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
