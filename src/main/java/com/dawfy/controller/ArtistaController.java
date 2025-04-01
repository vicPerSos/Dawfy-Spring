package com.dawfy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawfy.controller.requestBody.artista.ArtistaRequestBodyPOST;
import com.dawfy.controller.requestBody.artista.ArtistaRequestBodyPUT;
import com.dawfy.persistence.entities.Artista;
import com.dawfy.services.ArtistaService;
import com.dawfy.services.PaisService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/artista")
public class ArtistaController {
    @Autowired
    private ArtistaService artistaService;

    @Autowired
    private PaisService paisService;

    @GetMapping
    public ResponseEntity<List<Artista>> getArtistas() {
        return ResponseEntity.ok(this.artistaService.getAllArtistas());
    }

    @GetMapping("/{idArtista}")
    public ResponseEntity<Optional<Artista>> getArtista(@PathVariable int idArtista) {
        Optional<Artista> result = this.artistaService.getArtistaById(idArtista);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Artista>> getArtistasByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(this.artistaService.getArtistasByNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<Artista> createArtista(@RequestBody ArtistaRequestBodyPOST artista) {
        Artista nuevoArtista = new Artista();
        nuevoArtista.setNombre(artista.getNombre());
        nuevoArtista.setCorreo(artista.getCorreo());
        nuevoArtista.setFechaNacimiento(artista.getFechaNacimiento());
        nuevoArtista.setPais(this.paisService.findById(artista.getPais()));
        return ResponseEntity.ok(this.artistaService.createArtista(nuevoArtista));
    }

    @PutMapping("/{idArtista}")
    public ResponseEntity<Artista> updateArtista(@PathVariable int idArtista,
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
        artistaNuevo.setFoto(artista.getFoto());

        return ResponseEntity.ok(this.artistaService.updateArtista(artistaNuevo));
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
