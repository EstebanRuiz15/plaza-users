package com.restaurant.users.infraestructur.util;

public final class InfraConstants {
    private InfraConstants(){
        throw new IllegalStateException("Utility class");
    }
    public static final String CREATE_EMPLOYEE_SUCCES="Create new employee Successfull";
    public static final String CREATE_OWNER_SUCCES="Create new owner Successfull";
    public static final String ERROR_MESSAGE_UNAUTHORIZED="Invalid email or password";
    public static final String ACCES_DENIED_MESSAGE="{\"error\": \"Access Denied: ";
    public static final String USER_NOT_FOUND="User not found";
    public static final String AUTHORIZATIOn="Authorization";
    public static final String BEARER="Bearer ";
    public static final String ERROR_PASSWORD_NULL="The password cannot be null";
    public static final String ERROR_EMAIL_NULL="The email cannot be null";
    public static final String URL_AUTH_LOGIN="/auth/login";
    public static final String AUTHORIZATION="Authorization";
    public static final Integer SEVEN=7;

}
