package com.dawfy.services.Mappers;

import com.dawfy.persistence.entities.Album;
import com.dawfy.services.DTOs.AlbumDTO;

public class AlbumDTOMapper {
    public static AlbumDTO toDTO(Album album) {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setNombre(album.getNombre());
        albumDTO.setFechaLanzamiento(album.getFechaLanzamiento());
        albumDTO.setArtista(album.getArtista().getNombre());
        return albumDTO;
    }

}
