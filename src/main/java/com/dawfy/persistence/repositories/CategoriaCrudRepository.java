package com.dawfy.persistence.repositories;

import org.springframework.data.repository.CrudRepository;

import com.dawfy.persistence.entities.Categoria;

public interface CategoriaCrudRepository extends CrudRepository<Categoria, Integer> {

}
