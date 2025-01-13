package com.dawfy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawfy.persistence.repositories.ArtistaCrudRepository;
import com.dawfy.persistence.entities.Artista;
import com.dawfy.persistence.entities.Usuario;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {
    @Autowired
    private ArtistaCrudRepository artistaCrudRepository;

    public List<Artista> getAllArtistas() {
        return (List<Artista>) this.artistaCrudRepository.findAll();
    }

    public Optional<Artista> getArtistaById(int id) {
        return this.artistaCrudRepository.findById(id);
    }
    public Artista createArtista(Artista artista) {
        return this.artistaCrudRepository.save(artista);
    }

    public Artista updateArtista(int id, Artista artista) {
        return this.artistaCrudRepository.save(artista);
    }

    public boolean existsArtista(int id) {
        return this.artistaCrudRepository.existsById(id);
    }

    public Artista saveArtista(Artista artista) {
        return this.artistaCrudRepository.save(artista);
    }

    public boolean deleteArtista(int id) {
        if (this.artistaCrudRepository.existsById(id)) {
            this.artistaCrudRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
}
