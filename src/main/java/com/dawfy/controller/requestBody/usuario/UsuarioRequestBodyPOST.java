package com.dawfy.controller.requestBody.usuario;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioRequestBodyPOST {
    private String nombre;
    private String correo;

    private LocalDate fechaNacimiento;
    private String pais;
    private String foto;
}
