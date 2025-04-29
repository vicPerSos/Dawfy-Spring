package com.dawfy.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dawfy.persistence.entities.Album;

public interface AlmbumRepository extends CrudRepository<Album, Integer> {
    public List<Album> findByNombreStartingWithIgnoreCase(String nombre);
}
