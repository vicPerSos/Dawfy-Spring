package com.dawfy.services.DTOs;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AlbumDTO {

    private String nombre;

    private LocalDate fechaLanzamiento;

    private String artista;

}
