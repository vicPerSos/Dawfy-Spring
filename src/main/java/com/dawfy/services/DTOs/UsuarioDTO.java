package com.dawfy.services.DTOs;

import java.time.LocalDate;

import com.dawfy.persistence.entities.Pais;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {
    private String nombre;
    private String correo;
    private LocalDate fechaNacimiento;
    private String pais;
}
