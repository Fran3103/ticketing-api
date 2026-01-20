package com.fran.ticketing_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    record ApiError(
            Instant timestamp,
            int status,
            String error,
            String message,
            String path,
            List<FieldViolation> violation
    ){}

    record FieldViolation(String field, String message){}

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> notFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(404).body(new ApiError(
                Instant.now(), 404,"Not Found", ex.getMessage(), request.getRequestURI(), List.of()
        ));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<FieldViolation> violations = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new FieldViolation(fe.getField(), messageOrDefault(fe)))
                .toList();

        return ResponseEntity.badRequest().body(new ApiError(
                Instant.now(), 400, "Bad Request", "Validation failed", req.getRequestURI(), violations
        ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> badJson(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return ResponseEntity.badRequest().body(new ApiError(
                Instant.now(), 400, "Bad Request", "Malformed JSON or invalid enum value", req.getRequestURI(), List.of()
        ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> conflict(DataIntegrityViolationException ex, HttpServletRequest req) {
        // común: email duplicado por UNIQUE
        return ResponseEntity.status(409).body(new ApiError(
                Instant.now(), 409, "Conflict", "Constraint violation (e.g. email already exists)", req.getRequestURI(), List.of()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> generic(Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(500).body(new ApiError(
                Instant.now(), 500, "Internal Server Error", "Unexpected error", req.getRequestURI(), List.of()
        ));
    }

    private String messageOrDefault(FieldError fe) {
        return fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid value";
    }

}
