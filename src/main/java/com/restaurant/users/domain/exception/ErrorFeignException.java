package com.restaurant.users.domain.exception;

public class ErrorFeignException extends RuntimeException {
    public ErrorFeignException(String message){
        super (message);
    }
}
