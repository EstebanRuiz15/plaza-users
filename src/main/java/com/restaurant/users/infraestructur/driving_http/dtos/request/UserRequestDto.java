package com.restaurant.users.infraestructur.driving_http.dtos.request;

import com.restaurant.users.domain.utils.ConstantsDomain;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = ConstantsDomain.NAME_EMPTY)
    @Pattern(regexp = ConstantsDomain.ONLY_LETTERS, message = ConstantsDomain.NAME_INVALID)
    private String name;

    @NotBlank(message = ConstantsDomain.LAST_NAME_EMPTY)
    @Pattern(regexp = ConstantsDomain.ONLY_LETTERS, message = ConstantsDomain.LAST_NAME_INVALID)
    private String lastName;

    @NotNull(message = ConstantsDomain.DOCUMENT_EMPTY)
    @Positive(message = ConstantsDomain.DOCUMENT_INVALID)
    private Integer document;

    @NotBlank(message = ConstantsDomain.CEL_PHONE_EMPTY)
    @Pattern(regexp = ConstantsDomain.CEL_PHONE_REGEX, message = ConstantsDomain.CEL_PHONE_INVALID)
    private String celPhone;

    @NotNull(message = ConstantsDomain.BIRTHDAY_EMPTY)
    @Past(message = ConstantsDomain.BIRTHDAY_INVALID)
    private Date birthDay;

    @NotBlank(message = ConstantsDomain.EMAIL_EMPTY)
    @Email(message = ConstantsDomain.EMAIL_INVALID)
    private String email;

    @NotBlank(message = ConstantsDomain.PASSWORD_EMPTY)
    @Size(min = 8, message =ConstantsDomain.PASSWORD_INVALID)
    private String password;


}
