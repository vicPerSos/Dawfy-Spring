package com.dawfy.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dawfy.persistence.entities.Artista;

public interface ArtistaCrudRepository extends CrudRepository<Artista, Integer> {
    List<Artista> findByNombreStartingWith(String nombre);
}
