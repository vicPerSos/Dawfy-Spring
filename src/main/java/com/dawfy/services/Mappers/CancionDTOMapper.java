package com.dawfy.services.Mappers;

import com.dawfy.persistence.entities.Cancion;
import com.dawfy.services.DTOs.CancionDTO;

public class CancionDTOMapper {

    public static CancionDTO toDTO(Cancion cancion) {
        CancionDTO cancionDTO = new CancionDTO();
        cancionDTO.setNombre(cancion.getNombre());
        cancionDTO.setDuracion(cancion.getDuracion());
        cancionDTO.setImagen(cancion.getImagen());
        cancionDTO.setUrl(cancion.getUrl());
        return cancionDTO;
    }

}
