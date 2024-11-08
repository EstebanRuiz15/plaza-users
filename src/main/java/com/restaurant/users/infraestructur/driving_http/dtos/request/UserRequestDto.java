package com.restaurant.users.infraestructur.driving_http.dtos.request;

import com.restaurant.users.domain.utils.ConstantsDomain;
import jakarta.validation.constraints.*;

import java.util.Date;
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

    public UserRequestDto() {
    }

    public UserRequestDto(String name, String lastName, Integer document, String celPhone, Date birthDay, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.document = document;
        this.celPhone = celPhone;
        this.birthDay = birthDay;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getDocument() {
        return document;
    }

    public void setDocument(Integer document) {
        this.document = document;
    }

    public String getCelPhone() {
        return celPhone;
    }

    public void setCelPhone(String celPhone) {
        this.celPhone = celPhone;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
