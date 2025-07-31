package com.borntocodeme.user.exception;

import com.borntocodeme.user.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ErrorResponse> handleUserValidationException(UserValidationException ex) {

        ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage(),
                ex.getField(), ex.getValue());
        // return ResponseEntity.badRequest().body(errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleValidationException.class)
    public ResponseEntity<ErrorResponse> handleRoleValidationException(RoleValidationException ex) {

        ErrorResponse errorResponse = new ErrorResponse("400", ex.getMessage(),
                ex.getField(), ex.getValue());
        // return ResponseEntity.badRequest().body(errorResponse);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
