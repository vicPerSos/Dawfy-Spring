package com.dawfy.persistence.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dawfy.persistence.entities.Cliente;

public interface ClienteCrudRepository extends CrudRepository<Cliente, Integer> {
    @Query("SELECT c FROM Cliente c JOIN c.pais p WHERE p.codigo_iso = :nombre")
    List<Cliente> findByPais(@Param("nombre") String nombre);

    Optional<Cliente> findByCorreo(String correo);

    List<Cliente> findByFechaNacimiento(LocalDate fechaNacimiento);
}
