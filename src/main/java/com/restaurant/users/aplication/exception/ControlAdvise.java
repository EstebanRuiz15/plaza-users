package com.restaurant.users.aplication.exception;

import com.restaurant.users.domain.exception.ErrorExceptionParam;
import com.restaurant.users.domain.exception.ErrorExceptionUserInvalid;
import com.restaurant.users.domain.utils.ConstantsDomain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControlAdvise {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                ConstantsDomain.ERROR_VALIDATION,
                errors
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorExceptionParam.class)
    public ResponseEntity<?> resourceNotFoundException(ErrorExceptionParam ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put("error", ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                details
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorExceptionUserInvalid.class)
    public ResponseEntity<?> resourceNotFoundException(ErrorExceptionUserInvalid ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put("error", ex.getMessage());

        ExceptionResponse errorDetails = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                details
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErrorNotAuthentication.class)
    public ResponseEntity<?> handleAuthenticationException(RuntimeException ex,WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put("error", ex.getMessage());

        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                details
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
