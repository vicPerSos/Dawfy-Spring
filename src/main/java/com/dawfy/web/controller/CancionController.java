package com.dawfy.web.controller;

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

import com.dawfy.persistence.entities.Cancion;
import com.dawfy.services.AlbumService;
import com.dawfy.services.CancionService;
import com.dawfy.services.DTOs.CancionDTO;
import com.dawfy.services.Mappers.CancionDTOMapper;
import com.dawfy.web.requestBody.cancion.CancionRequestBodyPUT;
import com.dawfy.web.requestBody.cancion.CancionRequestBodyPost;

@RestController
@RequestMapping("/cancion")
public class CancionController {
    @Autowired
    private CancionService cancionService;

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<CancionDTO>> getAllCanciones() {
        List<Cancion> canciones = this.cancionService.getAllCanciones();
        List<CancionDTO> cancionDTOs = new ArrayList<>();
        for (Cancion cancion : canciones) {
            cancionDTOs.add(CancionDTOMapper.toDTO(cancion));
        }
        return ResponseEntity.ok(cancionDTOs);
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<CancionDTO>> findByNombre(String nombre) {
        List<Cancion> canciones = this.cancionService.findByNombre(nombre);
        List<CancionDTO> cancionDTOs = new ArrayList<>();
        for (Cancion cancion : canciones) {
            cancionDTOs.add(CancionDTOMapper.toDTO(cancion));
        }
        return ResponseEntity.ok(cancionDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CancionDTO>> findById(int id) {
        Optional<Cancion> cancion = this.cancionService.findById(id);
        if (cancion.isPresent()) {
            return ResponseEntity.ok(Optional.of(CancionDTOMapper.toDTO(cancion.get())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CancionDTO> create(@RequestBody CancionRequestBodyPost cancion) {
        Cancion cancionNueva = new Cancion();
        cancionNueva.setNombre(cancion.getNombre());
        cancionNueva.setDuracion(cancion.getDuracion());
        cancionNueva.setAlbum(this.albumService.getAlbumById(cancion.getAlbum()));
        this.cancionService.save(cancionNueva);
        return ResponseEntity.ok(CancionDTOMapper.toDTO(cancionNueva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CancionDTO> update(@PathVariable int id, @RequestBody CancionRequestBodyPUT cancion) {
        if (id != cancion.getId()) {
            return ResponseEntity.badRequest().build();
        }
        if (this.cancionService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Cancion cancionActualizada = new Cancion();
        cancionActualizada.setId(id);
        cancionActualizada.setNombre(cancion.getNombre());
        cancionActualizada.setDuracion(cancion.getDuracion());
        cancionActualizada.setAlbum(this.albumService.getAlbumById(cancion.getAlbum()));
        this.cancionService.update(cancionActualizada, id);
        return ResponseEntity.ok(CancionDTOMapper.toDTO(cancionActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Optional<Cancion> cancion = this.cancionService.findById(id);
        if (cancion.isPresent()) {
            this.cancionService.delete(cancion.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
