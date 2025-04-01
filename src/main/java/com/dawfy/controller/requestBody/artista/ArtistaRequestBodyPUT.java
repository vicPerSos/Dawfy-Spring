package com.dawfy.controller.requestBody.artista;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ArtistaRequestBodyPUT {
    private int id;
    private String nombre;
    private String correo;

    private LocalDate fechaNacimiento;
    private String pais;
    private String foto;

}
