package com.dawfy.controller.requestBody;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ClienteRequestBody {
    private int id;
    private String nombre;
    private String correo;

    private LocalDate fechaNacimiento;
    private String pais;

}
