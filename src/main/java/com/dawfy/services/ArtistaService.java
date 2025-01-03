package com.dawfy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawfy.persistence.repositories.ArtistaCrudRepository;
import com.dawfy.persistence.entities.Artista;
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
}
