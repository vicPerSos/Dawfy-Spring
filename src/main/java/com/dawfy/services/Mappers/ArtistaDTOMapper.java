package com.dawfy.services.Mappers;

import com.dawfy.persistence.entities.Artista;
import com.dawfy.services.DTOs.ArtistaDTO;

public class ArtistaDTOMapper {
    public static ArtistaDTO mapper(Artista artista) {
        if (artista == null) {
            return null;
        }
        ArtistaDTO artistaDto = new ArtistaDTO();
        artistaDto.setId(artista.getId());
        artistaDto.setNombre(artista.getNombre());
        artistaDto.setCorreo(artista.getCorreo());
        artistaDto.setFechaNacimiento(artista.getFechaNacimiento());
        artistaDto.setPais(artista.getPais().getNombre());
        artistaDto.setFoto(artista.getFoto());
        artistaDto.setIdArtistaSpoti(artista.getIdArtistaSpoti());
        if (artista.getGeneros() != null) {
            artistaDto.setCategorias(artista.getGeneros().stream()
                    .map(genero -> genero.getCategoria().getNombre())
                    .collect(java.util.stream.Collectors.toSet()));
        } else {
            artistaDto.setCategorias(java.util.Collections.emptySet());
        }
        return artistaDto;
    }

}
