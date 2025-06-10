package com.dawfy.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawfy.enums.Roles;
import com.dawfy.persistence.entities.Artista;
import com.dawfy.persistence.entities.Categoria;
import com.dawfy.persistence.entities.Genero;
import com.dawfy.persistence.repositories.ArtistaCrudRepository;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class ArtistaService {
    @Autowired
    private final SpotifyService spotifyService;
    @Autowired
    private ArtistaCrudRepository artistaCrudRepository;
    @Autowired
    private CategoriaService categoriaService;

    ArtistaService(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

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
        JsonNode artistaSpoti = this.spotifyService.getArtist(artista.getIdArtistaSpoti());
        String[] genres = artistaSpoti.get("genres").asText().split(",");
        List<Categoria> generos = new ArrayList();
        for (String g : genres) {
            if (!this.categoriaService.existsByNombre(g)) {
                Categoria c = new Categoria();
                c.setNombre(g);
                generos.add(this.categoriaService.createCategoria(c));
            } else {
                generos.add(this.categoriaService.getCategoriasByNombre(g).get(0));
            }

        }
        List<Genero> listaGenero = new ArrayList<>();
        for (Categoria cat : generos) {
            Genero genero = new Genero();
            genero.setCategoria(cat);
            genero.setArtista(artista);
            listaGenero.add(genero);
        }
        artista.setGeneros(listaGenero);
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
        artistaExistente.setIdArtistaSpoti(artista.getIdArtistaSpoti());
        artistaExistente.setGeneros(artista.getGeneros());

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
