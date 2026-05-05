package com.depositcorex.customer_onboarding_service.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Helper Method to keep the response format consistent

    private ResponseEntity<Object> buildResponse(HttpStatus status, String error, Object message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    /**
     * 1. Handles Validation Errors (e.g., Name is null, CIF is too short)
     * This is triggered by @Valid in the Controller and @NotBlank/@Size in the Model.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrors.put(error.getField(), error.getDefaultMessage()));

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation Failed", fieldErrors);
    }

    /**
     * 2. Handles Bad JSON Format
     * Triggered if the external team sends broken JSON (missing quotes, commas, etc.)
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleInvalidJson(HttpMessageNotReadableException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Malformed JSON", "The request body has an invalixd format.");
    }


     // 3. Handles Duplicate CIF

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleDuplicateError(IllegalStateException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Duplicate Entry", ex.getMessage());
    }

//      //4. Handles Database Connection Issues
//
//    @ExceptionHandler(DataAccessException.class)
//    public ResponseEntity<Object> handleDatabaseError(DataAccessException ex) {
//        return buildResponse(HttpStatus.SERVICE_UNAVAILABLE, "Database Error", "The banking database is unreachable.");
//    }
//

     //5. Handles "Not Found" for GET requests

    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(jakarta.persistence.EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Data Not Found", ex.getMessage());
    }
}


