package com.dawfy.persistence.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.dawfy.persistence.entities.Pais;

public interface PaisCrudRepository extends CrudRepository<Pais, String> {

    Optional<Pais> findByNombre(String nombre);

}
