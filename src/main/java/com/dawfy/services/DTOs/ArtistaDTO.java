package com.dawfy.services.DTOs;

import java.time.LocalDate;
import java.util.Set;

import lombok.Data;

@Data
public class ArtistaDTO {
    private int id;
    private String nombre;
    private String correo;
    private LocalDate fechaNacimiento;
    private String pais;
    private String foto;
    private String idArtistaSpoti;
    private Set<String> categorias;
}
