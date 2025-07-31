package com.borntocodeme.user.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class RoleValidationException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private final String field;
    private final String value;

    public RoleValidationException(String field, String value) {
        super("role validation failed for field: " + field + " with value: " + value);
        this.field = field;
        this.value = value;
    }

}
