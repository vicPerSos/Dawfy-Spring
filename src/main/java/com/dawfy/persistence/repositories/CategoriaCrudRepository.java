package com.dawfy.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dawfy.persistence.entities.Categoria;

public interface CategoriaCrudRepository extends CrudRepository<Categoria, Integer> {
    public List<Categoria> findByNombre(String nombre);

    public Boolean existsByNombre(String nombre);

}
