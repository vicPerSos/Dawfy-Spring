package com.dawfy.services.Mappers;

import java.util.stream.Collectors;

import com.dawfy.persistence.entities.Artista;
import com.dawfy.persistence.entities.Cancion;
import com.dawfy.services.DTOs.CancionDTO;

public class CancionDTOMapper {
    public static CancionDTO toDTO(Cancion cancion) {
        CancionDTO cancionDto = new CancionDTO();
        cancionDto.setNombre(cancion.getNombre());
        cancionDto.setDuracion(cancion.getDuracion());
        cancionDto.setImagen(cancion.getImagen());
        cancionDto.setUrl(cancion.getUrl());

        // Mapear categorías

        // Mapear colaboradores
        if (cancion.getColaboradores() != null) {
            cancionDto.setColaboradores(
                    cancion.getColaboradores().stream()
                            .map(Artista::getNombre)
                            .collect(Collectors.toList()));
        }

        return cancionDto;
    }
}
