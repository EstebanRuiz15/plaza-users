package com.restaurant.users.aplication.exception;

public class ErrorNotAuthentication extends RuntimeException {
    public ErrorNotAuthentication(String message){
        super(message);
    }
}