package com.dawfy.services.DTOs;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioDTO {
    private int id;
    private String nombre;
    private String correo;
    private LocalDate fechaNacimiento;
    private String pais;
    private String foto;
}
