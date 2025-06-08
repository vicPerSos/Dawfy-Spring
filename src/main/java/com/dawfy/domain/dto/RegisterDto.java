package com.dawfy.domain.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RegisterDto {
    private String nombre;
    private String correo;
    private LocalDate fechaNacimiento;
    private String pais;
    private String foto;
    private String password;
    private String username;
    private String roll;
    private String spotifyId;
}
