package com.dawfy.persistence.repositories;

import com.dawfy.persistence.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UsuarioCrudRepository extends CrudRepository<Usuario,Integer> {
    List<Usuario> findByPais(String pais);
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByFechaNacimiento(LocalDate fechaNacimiento);
}
