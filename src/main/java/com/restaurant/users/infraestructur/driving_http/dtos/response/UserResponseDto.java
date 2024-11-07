package com.restaurant.users.infraestructur.driving_http.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponseDto {
    private String name;

    private String lastName;

    private Integer document;

    private String celPhone;

    private Date birthDay;

    private String email;

    private String password;
    private String rol;


}
