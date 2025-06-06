package com.dawfy.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawfy.enums.Roles;
import com.dawfy.persistence.entities.Artista;
import com.dawfy.persistence.repositories.ArtistaCrudRepository;

@Service
public class ArtistaService {
    @Autowired
    private ArtistaCrudRepository artistaCrudRepository;

    public List<Artista> getAllArtistas() {
        return (List<Artista>) this.artistaCrudRepository.findAll();
    }

    public Optional<Artista> getArtistaById(int id) {
        return this.artistaCrudRepository.findById(id);
    }

    public List<Artista> getArtistasByNombre(String nombre) {
        return this.artistaCrudRepository.findByNombreStartingWithIgnoreCase(nombre);
    }

    public Artista createArtista(Artista artista) {
        try {
            if (artista.getUsername() == null || artista.getUsername().trim().isEmpty()) {
                throw new Exception("Username requerido");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Optional<Artista> existingUser = this.artistaCrudRepository.findByUsername(artista.getUsername());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con el username: " + artista.getUsername());
        }
        artista.setRoll(Roles.ARTISTA.toString());
        artista.setCuentaExpirada(false);
        artista.setCuentaBloqueada(false);
        artista.setCredencialExpirada(false);
        artista.setHabilitada(true);
        return this.artistaCrudRepository.save(artista);
    }

    public Artista updateArtista(int id, Artista artista) {
        Artista artistaExistente = this.artistaCrudRepository.findById(id).get();

        artistaExistente.setNombre(artista.getNombre());
        artistaExistente.setCorreo(artista.getCorreo());
        artistaExistente.setFechaNacimiento(artista.getFechaNacimiento());
        artistaExistente.setPais(artista.getPais());
        artistaExistente.setFoto(artista.getFoto());
        artistaExistente.setPassword(artista.getPassword());
        artistaExistente.setUsername(artista.getUsername());

        return this.artistaCrudRepository.save(artistaExistente);
    }

    public boolean existsArtista(int id) {
        return this.artistaCrudRepository.existsById(id);
    }

    public boolean deleteArtista(int id) {
        if (this.artistaCrudRepository.existsById(id)) {
            this.artistaCrudRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public String paisDeArtista(int idArtista) {
        try {
            Optional<Artista> artista = this.artistaCrudRepository.findById(idArtista);
            if (artista.isEmpty()) {
                throw new Exception("No existe el artista");
            }
            return artista.get().getPais().getNombre();
        } catch (Exception e) {
            return null;
        }
    }

    public Optional<Artista> artistaPorCorreo(String correo) {
        return this.artistaCrudRepository.findByCorreo(correo);
    }

    public List<Artista> artistasPorPais(String codigoIso) {
        return this.artistaCrudRepository.findByPais(codigoIso);
    }

    public List<Artista> artistasPorCumple(LocalDate fechaNacimiento) {
        return this.artistaCrudRepository.findByFechaNacimiento(fechaNacimiento);
    }

    public String correoArtista(int id) {
        return this.artistaCrudRepository.findById(id).get().getCorreo();
    }

    public LocalDate fechaArtista(int id) {
        return this.artistaCrudRepository.findById(id).get().getFechaNacimiento();
    }

    public Artista artistaByUsername(String username) {
        return this.artistaCrudRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("No existe un artista con el username: " + username));
    }

}
