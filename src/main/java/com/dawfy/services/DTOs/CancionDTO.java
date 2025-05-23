package com.dawfy.services.DTOs;

import java.util.List;

import lombok.Data;

@Data
public class CancionDTO {
    private String nombre;
    private int duracion;
    private String imagen;
    private String url;
    private List<String> categorias;
    private List<String> colaboradores;
}
