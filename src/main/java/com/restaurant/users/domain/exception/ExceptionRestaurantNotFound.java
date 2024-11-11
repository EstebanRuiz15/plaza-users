package com.restaurant.users.domain.exception;

public class ExceptionRestaurantNotFound extends RuntimeException{
    public ExceptionRestaurantNotFound(String message) {
        super(message);
    }
}