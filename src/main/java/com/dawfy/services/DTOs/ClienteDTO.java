package com.dawfy.services.DTOs;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO {
    private String nombre;
    private String correo;
    private LocalDate fechaNacimiento;
    private String pais;
}
