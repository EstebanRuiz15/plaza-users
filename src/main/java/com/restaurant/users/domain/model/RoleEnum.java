package com.restaurant.users.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoleEnum {
    OWNER;
    @JsonCreator
    public static RoleEnum fromString(String value) {
        return RoleEnum.valueOf(value.toUpperCase());
    }
}
