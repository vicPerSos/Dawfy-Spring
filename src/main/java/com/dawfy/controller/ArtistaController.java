package com.dawfy.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawfy.persistence.entities.Artista;
import com.dawfy.services.ArtistaService;

@RestController
@RequestMapping("/artista")
public class ArtistaController {
    @Autowired
    private ArtistaService artistaService;

    @GetMapping("/artistas")
    public ResponseEntity<List<Artista>> getArtistas() {
        return ResponseEntity.ok(this.artistaService.getAllArtistas());
    }

    @GetMapping("/{idArtista}")
    public ResponseEntity<Optional<Artista>> getArtista(@PathVariable int idArtista) {
        Optional<Artista>result=this.artistaService.getArtistaById(idArtista);
        if(result.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(result);
    }
}
