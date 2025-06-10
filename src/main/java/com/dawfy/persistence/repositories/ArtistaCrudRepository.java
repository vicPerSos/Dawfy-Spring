package com.dawfy.persistence.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dawfy.persistence.entities.Artista;

public interface ArtistaCrudRepository extends CrudRepository<Artista, Integer> {
    @Query("SELECT a FROM Artista a JOIN a.pais p WHERE p.codigo_iso = :codigoIso")
    List<Artista> findByPais(@Param("codigoIso") String codigoIso);

    Optional<Artista> findByCorreo(String correo);

    List<Artista> findByFechaNacimiento(LocalDate fechaNacimiento);

    List<Artista> findByNombreStartingWithIgnoreCase(String nombre);

    @Query("""
            SELECT DISTINCT a
            FROM Artista a
            JOIN a.generos g
            JOIN g.categoria c
            WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombreCategoria, '%'))
            """)
    List<Artista> findArtistasByCategoriaNombre(@Param("nombreCategoria") String nombreCategoria);

    Optional<Artista> findByUsername(String username);
}
