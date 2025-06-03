package com.dawfy.services.Mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawfy.domain.dto.BusquedaDto;
import com.dawfy.persistence.entities.Album;
import com.dawfy.persistence.entities.Artista;
import com.dawfy.persistence.entities.Cancion;
import com.dawfy.persistence.repositories.AlbumCrudRepository;
import com.dawfy.persistence.repositories.ArtistaCrudRepository;
import com.dawfy.persistence.repositories.CancionCrudRepository;
import com.dawfy.services.DTOs.AlbumDTO;
import com.dawfy.services.DTOs.ArtistaDTO;
import com.dawfy.services.DTOs.CancionDTO;

@Service
public class SearchService {
    @Autowired
    private ArtistaCrudRepository artistaCrudRepository;

    @Autowired
    private CancionCrudRepository cancionCrudRepository;

    @Autowired
    private AlbumCrudRepository albumCrudRepository;

    public BusquedaDto busqueda(String query) {
        BusquedaDto busquedaDto = new BusquedaDto();
        List<Cancion> canciones = this.cancionCrudRepository.findByNombreStartingWithIgnoreCase(query);
        List<CancionDTO> cancionesDTO = new ArrayList<>();
        try {
            if (canciones.isEmpty()) {
                throw new Exception("No se han encontrado resultados para la búsqueda");
            }
            for (int i = 0; i < canciones.size(); i++) {
                Cancion cancion = canciones.get(i);
                CancionDTOMapper.toDTO(cancion);
                cancionesDTO.add(CancionDTOMapper.toDTO(cancion));
            }
        } catch (Exception e) {
            canciones = null;
        }

        List<Artista> artistas = this.artistaCrudRepository.findByNombreStartingWithIgnoreCase(query);
        List<ArtistaDTO> artistasDTO = new ArrayList<>();
        try {
            if (artistas.isEmpty()) {
                throw new Exception("No se han encontrado resultados para la búsqueda");
            }
            for (int i = 0; i < artistas.size(); i++) {
                Artista artista = artistas.get(i);
                artistasDTO.add(ArtistaDTOMapper.mapper(artista));
            }
        } catch (Exception e) {
            artistas = null;
        }
        List<Album> albums = this.albumCrudRepository.findByNombreStartingWithIgnoreCase(query);
        List<AlbumDTO> albumsDTO = new ArrayList<>();
        try {
            if (albums.isEmpty()) {
                throw new Exception("No se han encontrado resultados para la búsqueda");
            }
            for (int i = 0; i < albums.size(); i++) {
                Album album = albums.get(i);
                albumsDTO.add(AlbumDTOMapper.toDTO(album));
            }
        } catch (Exception e) {
            albums = null;
        }
        busquedaDto.setCanciones(cancionesDTO);
        busquedaDto.setArtistas(artistasDTO);
        busquedaDto.setAlbums(albumsDTO);
        return busquedaDto;

    }
}
