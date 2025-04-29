package com.dawfy.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Artista> artistasPorPais(String nombre) {
        return this.artistaCrudRepository.findByPais(nombre);
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

}
