package com.dawfy.persistence.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dawfy.persistence.entities.Cancion;

public interface CancionRepository extends CrudRepository<Cancion, Integer> {
    List<Cancion> findByNombre(String nombre);



}
