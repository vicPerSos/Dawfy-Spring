package com.dawfy.controller.requestBody;

import java.time.LocalDate;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UsuarioRequestBody {

    private String nombre;
    private String correo;

    private LocalDate fechaNacimiento;
    private String pais;

}
