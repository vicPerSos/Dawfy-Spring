package com.dawfy.persistence.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dawfy.persistence.entities.Usuario;

public interface UsuarioCrudRepository extends CrudRepository<Usuario, Integer> {
    @Query("SELECT u FROM Usuario u JOIN u.pais p WHERE p.codigo_iso = :nombre")
    List<Usuario> findByPais(@Param("nombre") String nombre);

    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findByFechaNacimiento(LocalDate fechaNacimiento);
    List<Usuario> findByNombreStartingWithIgnoreCase(String nombre);
}
