package com.dawfy.controller.requestBody.cliente;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ClienteRequestBodyPOST {
    private String nombre;
    private String correo;

    private LocalDate fechaNacimiento;
    private String pais;
    private String foto;
    private String password;
}
