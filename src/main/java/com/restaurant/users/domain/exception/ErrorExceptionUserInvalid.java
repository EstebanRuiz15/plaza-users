package com.restaurant.users.domain.exception;

public class ErrorExceptionUserInvalid extends RuntimeException{
    public ErrorExceptionUserInvalid(String message) {
        super(message);
    }
}
