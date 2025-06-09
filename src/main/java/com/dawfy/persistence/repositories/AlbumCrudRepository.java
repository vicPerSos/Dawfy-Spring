package com.dawfy.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dawfy.persistence.entities.Album;

public interface AlbumCrudRepository extends CrudRepository<Album, Integer> {
    public List<Album> findByNombreStartingWithIgnoreCase(String nombre);

public List<Album> findByArtistaUsername(String artistaUsername);
    public List<Album> findByArtistaId(int artistaId);
}
