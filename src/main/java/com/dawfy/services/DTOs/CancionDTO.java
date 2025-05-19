package com.dawfy.services.DTOs;

import com.dawfy.persistence.entities.Album;

import lombok.Data;

@Data
public class CancionDTO {
    private String nombre;
    private int duracion;

}
