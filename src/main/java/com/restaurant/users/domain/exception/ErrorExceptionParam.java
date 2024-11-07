package com.restaurant.users.domain.exception;

public class ErrorExceptionParam extends RuntimeException {
    public ErrorExceptionParam(String message) {
        super(message);
    }
}
