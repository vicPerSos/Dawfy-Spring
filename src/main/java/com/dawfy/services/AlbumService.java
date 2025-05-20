package com.dawfy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawfy.persistence.entities.Album;
import com.dawfy.persistence.repositories.AlbumRepository;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> getAllAlbums() {
        return (List<Album>) this.albumRepository.findAll();
    }

    public Album getAlbumById(int id) {
        return this.albumRepository.findById(id).orElse(null);
    }

    public List<Album> getAlbumsByNombre(String nombre) {
        return this.albumRepository.findByNombreStartingWithIgnoreCase(nombre);
    }

    public Album createAlbum(Album album) {
        return this.albumRepository.save(album);
    }

    public Album updateAlbum(int id, Album album) {
        Album albumExistente = this.albumRepository.findById(id).orElse(null);
        if (albumExistente != null) {
            albumExistente.setNombre(album.getNombre());
            albumExistente.setFechaLanzamiento(album.getFechaLanzamiento());
            albumExistente.setIdArtista(album.getIdArtista());
            albumExistente.setImagen(album.getImagen());
            return this.albumRepository.save(albumExistente);
        }
        return null;
    }

    public boolean existsAlbum(int id) {
        return this.albumRepository.existsById(id);
    }

    public boolean deleteAlbum(int id) {
        if (this.albumRepository.existsById(id)) {
            this.albumRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public String artistaDeAlbum(int idAlbum) {
        Album album = this.albumRepository.findById(idAlbum).orElse(null);
        if (album != null) {
            return album.getArtista().getNombre();
        }
        return null;
    }

    public List<Album> getAlbumsByArtistaId(int artistaId) {
        return this.albumRepository.findByArtistaId(artistaId);
    }

    public List<Album> getAlbumsByArtistaNombre(String artistaNombre) {
        return this.albumRepository.findByNombreStartingWithIgnoreCase(artistaNombre);
    }

}
