package com.dawfy.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dawfy.persistence.entities.Cancion;

public interface CancionCrudRepository extends CrudRepository<Cancion, Integer> {
    @Query("SELECT DISTINCT c FROM Cancion c LEFT JOIN FETCH c.colaboradores WHERE c.nombre LIKE %:nombre%")
    List<Cancion> findByNombreWithColaboradores(@Param("nombre") String nombre);

    @Query("SELECT DISTINCT c FROM Cancion c LEFT JOIN FETCH c.colaboradores WHERE c.id = :id")
    Optional<Cancion> findByIdWithColaboradores(@Param("id") Integer id);

    @Query("SELECT DISTINCT c FROM Cancion c LEFT JOIN FETCH c.colaboradores")
    List<Cancion> findAllWithCategoriaAndColaboradores();

    List<Cancion> findByNombreStartingWithIgnoreCase(String nombre);
}
