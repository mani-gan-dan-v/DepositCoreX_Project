package com.depositcorex.Main.Exception; // Move to the Exception package

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handle Resource Not Found (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 2. Handle Business Logic Errors (400)
    @ExceptionHandler(InvalidTransactionException.class)
    public ResponseEntity<Object> handleInvalidTransaction(InvalidTransactionException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 3. Fallback for any other unexpected RuntimeExceptions (500)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleGeneral(RuntimeException ex) {
        return buildResponse("An unexpected error occurred: " + ex.getMessage(), 
                             HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper method to keep the code DRY (Don't Repeat Yourself)
    private ResponseEntity<Object> buildResponse(String message, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        
        return new ResponseEntity<>(body, status);
    }
}