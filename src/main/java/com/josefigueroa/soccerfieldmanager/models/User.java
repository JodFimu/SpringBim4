package com.josefigueroa.soccerfieldmanager.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
@Table(name="user")
public class User implements Serializable{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "correo no valido")
    @Column(unique = true, name = "email")
    private String email;

    @Column(unique = true)
    private String username;
    private String name;
    private String surname;
    private String password;
    private String urlProfilePhoto;






}
 