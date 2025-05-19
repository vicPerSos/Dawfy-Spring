package com.dawfy.web.requestBody.album;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AlbumRequestBodyPOST {
    private String nombre;
    private LocalDate fechaLanzamiento;
    private int artista;
    private String imagen;
}
