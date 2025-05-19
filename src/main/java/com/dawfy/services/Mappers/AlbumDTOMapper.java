package com.dawfy.services.Mappers;

import com.dawfy.persistence.entities.Album;
import com.dawfy.services.DTOs.AlbumDTO;
import com.dawfy.services.DTOs.CancionDTO;

public class AlbumDTOMapper {
    public static AlbumDTO toDTO(Album album) {
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setNombre(album.getNombre());
        albumDTO.setFechaLanzamiento(album.getFechaLanzamiento());
        albumDTO.setArtista(album.getArtista().getNombre());
        for (int i = 0; i < album.getCancion().size(); i++) {
            CancionDTO cancionDTO = CancionDTOMapper.toDTO(album.getCancion().get(i));
            albumDTO.getCancion().add(cancionDTO);
        }

        return albumDTO;
    }

}
