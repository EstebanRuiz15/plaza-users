package com.restaurant.users.infraestructur.driven_rp.entity;

import com.restaurant.users.domain.model.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name= "Users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="nombre", nullable = false, unique = true)
    private String name;

    @Column (name="apellido",nullable=false, unique = true)
    private String lastName;

    @Column (name= "Document", nullable = false)
    private Integer document;

    @Column (name="celular", nullable = false)
    private String celPhone;

    @Column (name = "fecha_nacimiento", nullable = false)
    private Date birthDay;

    @Column (name = "correo", nullable = false, unique = true)
    private String email;

    @Column (name = "clave", nullable = false)
    private String password;

    @Column(name = "rol", nullable = false)
    private Enum<RoleEnum> rol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Enum<RoleEnum> getRol() {
        return rol;
    }

    public void setRol(Enum<RoleEnum> rol) {
        this.rol = rol;
    }
}
