package com.dawfy.services.Mappers;

import java.util.ArrayList;

import com.dawfy.persistence.entities.Album;
import com.dawfy.services.DTOs.AlbumDTO;
import com.dawfy.services.DTOs.CancionDTO;

public class AlbumDTOMapper {
    public static AlbumDTO toDTO(Album album) {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setNombre(album.getNombre());
        albumDTO.setFechaLanzamiento(album.getFechaLanzamiento());

        if (album.getArtista() != null) {
            albumDTO.setArtista(album.getArtista().getNombre());
        } else {
            albumDTO.setArtista("Desconocido");
        }

        // Verificar si la lista de canciones es nula
        if (album.getCancion() != null) {
            for (int i = 0; i < album.getCancion().size(); i++) {
                CancionDTO cancionDTO = CancionDTOMapper.toDTO(album.getCancion().get(i));
                if (albumDTO.getCancion() == null) {
                    albumDTO.setCancion(new ArrayList<>());
                }
                albumDTO.getCancion().add(cancionDTO);
            }
        }

        return albumDTO;
    }

}
