package com.dawfy.web.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawfy.persistence.entities.Artista;
import com.dawfy.services.ArtistaService;
import com.dawfy.services.PaisService;
import com.dawfy.services.DTOs.ArtistaDTO;
import com.dawfy.services.Mappers.ArtistaDTOMapper;
import com.dawfy.web.requestBody.artista.ArtistaRequestBodyPOST;
import com.dawfy.web.requestBody.artista.ArtistaRequestBodyPUT;

@RestController
@RequestMapping("/artista")
public class ArtistaController {
    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private PaisService paisService;

    @GetMapping
    public ResponseEntity<List<ArtistaDTO>> getArtistas() {
        List<Artista> artistas = this.artistaService.getAllArtistas();
        List<ArtistaDTO> artistasDTO = new ArrayList<>();
        for (Artista artista : artistas) {
            artistasDTO.add(ArtistaDTOMapper.mapper(artista));
        }

        return ResponseEntity.ok(artistasDTO);
    }

    @GetMapping("/{idArtista}")
    public ResponseEntity<ArtistaDTO> getArtista(@PathVariable int idArtista) {
        Optional<Artista> artista = this.artistaService.getArtistaById(idArtista);
        if (artista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ArtistaDTOMapper.mapper(artista.get()));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ArtistaDTO>> getArtistasByNombre(@PathVariable String nombre) {
        List<Artista> artistas = this.artistaService.getArtistasByNombre(nombre);
        List<ArtistaDTO> artistasDTO = new ArrayList<>();
        for (Artista artista : artistas) {
            artistasDTO.add(ArtistaDTOMapper.mapper(artista));
        }
        return ResponseEntity.ok(artistasDTO);
    }

    @GetMapping("/fecha/{idArtista}")
    public ResponseEntity<String> fechaNacimiento(@PathVariable int idArtista) {
        Optional<Artista> artista = this.artistaService.getArtistaById(idArtista);
        if (artista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        LocalDate fecha = artista.get().getFechaNacimiento();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaFormateada = fecha.format(formatter);
        return ResponseEntity.ok(fechaFormateada);
    }

    @GetMapping("/cumple/{dia}")
    public ResponseEntity<List<ArtistaDTO>> getArtistasCumple(@PathVariable String dia) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fecha = LocalDate.parse(dia, formatter);
            List<Artista> artistas = this.artistaService.artistasPorCumple(fecha);
            List<ArtistaDTO> artistasDTO = new ArrayList<>();
            for (Artista artista : artistas) {
                artistasDTO.add(ArtistaDTOMapper.mapper(artista));
            }
            return ResponseEntity.ok(artistasDTO);
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pais/{pais}")
    public ResponseEntity<List<ArtistaDTO>> artistaPorPais(@PathVariable String pais) {
        if (pais.length() != 2) {
            return ResponseEntity.badRequest().build();
        }
        List<Artista> artistas = this.artistaService.artistasPorPais(pais);
        List<ArtistaDTO> artistasDTO = new ArrayList<>();
        for (Artista artista : artistas) {
            artistasDTO.add(ArtistaDTOMapper.mapper(artista));
        }
        return ResponseEntity.ok(artistasDTO);
    }

    @GetMapping("paisDe/{idArtista}")
    public ResponseEntity<String> paisDeArtista(@PathVariable int idArtista) {
        String respuesta = this.artistaService.paisDeArtista(idArtista);
        if (respuesta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/correo/{idArtista}")
    public ResponseEntity<String> correoDeArtista(@PathVariable int idArtista) {
        Optional<Artista> artista = this.artistaService.getArtistaById(idArtista);
        if (artista.isPresent()) {
            return ResponseEntity.ok(artista.get().getCorreo());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<String> correoDe(@PathVariable String correo) {
        Optional<Artista> artista = this.artistaService.artistaPorCorreo(correo);
        if (artista.isPresent()) {
            return ResponseEntity.ok(artista.get().getCorreo());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ArtistaDTO> createArtista(@RequestBody ArtistaRequestBodyPOST artista) {
        try {
            Artista artistaNuevo = new Artista();
            artistaNuevo.setNombre(artista.getNombre());
            artistaNuevo.setCorreo(artista.getCorreo());
            artistaNuevo.setFechaNacimiento(artista.getFechaNacimiento());
            artistaNuevo.setPais(this.paisService.findById(artista.getPais()));
            artistaNuevo.setFoto(artista.getFoto() != null ? artista.getFoto()
                    : "http://i.scdn.co/image/ab6761610000517476b4b22f78593911c60e7193");
            if (artista.getPassword() == null) {
                return ResponseEntity.badRequest().build();
            }
            artistaNuevo.setPassword(artista.getPassword());
            return ResponseEntity.ok(ArtistaDTOMapper.mapper(this.artistaService.createArtista(artistaNuevo)));
        } catch (Exception e) {
            System.out.println("La peticion no realizó correctamente. Error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{idArtista}")
    public ResponseEntity<ArtistaDTO> updateArtista(@PathVariable int idArtista,
            @RequestBody ArtistaRequestBodyPUT artista) {
        if (!this.artistaService.existsArtista(idArtista)) {
            return ResponseEntity.notFound().build();
        }
        if (idArtista != artista.getId()) {
            return ResponseEntity.badRequest().build();
        }
        Artista artistaNuevo = new Artista();
        artistaNuevo.setNombre(artista.getNombre());
        artistaNuevo.setCorreo(artista.getCorreo());
        artistaNuevo.setFechaNacimiento(artista.getFechaNacimiento());
        artistaNuevo.setPais(this.paisService.findById(artista.getPais()));
        artistaNuevo.setFoto(artista.getFoto() != null ? artista.getFoto()
                : this.artistaService.getArtistaById(idArtista).get().getFoto());
        if (artista.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }
        artistaNuevo.setPassword(artista.getPassword());

        return ResponseEntity.ok(ArtistaDTOMapper.mapper(this.artistaService.updateArtista(idArtista, artistaNuevo)));
    }

    @DeleteMapping("/{idArtista}")
    public ResponseEntity<Void> deleteArtista(@PathVariable int idArtista) {
        if (!this.artistaService.existsArtista(idArtista)) {
            return ResponseEntity.notFound().build();
        }
        this.artistaService.deleteArtista(idArtista);
        return ResponseEntity.ok().build();
    }
}
