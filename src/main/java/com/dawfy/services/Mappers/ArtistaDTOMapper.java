package com.dawfy.services.Mappers;

import com.dawfy.persistence.entities.Artista;
import com.dawfy.services.DTOs.ArtistaDTO;

public class ArtistaDTOMapper {
    public static ArtistaDTO map(Artista artista) {
        if (artista == null) {
            return null;
        }
        ArtistaDTO artistaDto = new ArtistaDTO();
        artistaDto.setNombre(artista.getNombre());
        artistaDto.setCorreo(artista.getCorreo());
        artistaDto.setFechaNacimiento(artista.getFechaNacimiento());
        artistaDto.setPais(artista.getPais().getNombre());


        return artistaDto;
    }

}
