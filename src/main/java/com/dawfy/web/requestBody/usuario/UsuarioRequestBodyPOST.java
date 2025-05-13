package com.dawfy.web.requestBody.usuario;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UsuarioRequestBodyPOST {
    private String nombre;
    private String correo;
    private LocalDate fechaNacimiento;
    private String pais;
    private String foto;
    private String password;
    private String username;

}
