package com.josefigueroa.soccerfieldmanager.DTOs;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistrerDTO {
    private String id;
    @NotBlank(message = "el correo es obligatorio")
    @Email(message = "ingrese un correo valido")
    private String email;
    @NotBlank(message = "la contraseña es obligatorio")
    private String password;
    @NotBlank(message = "el usuario es obligatorio")
    private String username;
    private String name;
    private String surname;
}
