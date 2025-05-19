package com.dawfy.web.requestBody.cancion;

import lombok.Data;

@Data
public class CancionRequestBodyPUT {
    private int id;
    private String nombre;
    private int duracion;
    private int album;
    private String imagen;
    private String url;

}
