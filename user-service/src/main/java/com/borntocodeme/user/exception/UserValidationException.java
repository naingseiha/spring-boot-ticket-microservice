package com.borntocodeme.user.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class UserValidationException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final String field;
    private final String value;

    public UserValidationException(String field, String value) {
        super("Username validation failed for field: " + field + " with value: " + value);
        this.field = field;
        this.value = value;
    }

}
