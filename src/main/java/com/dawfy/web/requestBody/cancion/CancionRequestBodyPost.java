package com.dawfy.web.requestBody.cancion;

import java.util.List;

import lombok.Data;

@Data
public class CancionRequestBodyPost {
    private String nombre;
    private int duracion;
    private int album;
    private String imagen;
    private String url;

}
