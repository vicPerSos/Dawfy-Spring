package com.dawfy.web.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.dawfy.persistence.entities.Album;
import com.dawfy.services.AlbumService;
import com.dawfy.services.DTOs.AlbumDTO;
import com.dawfy.services.Mappers.AlbumDTOMapper;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getAllAlbums() {
        List<Album> albums = this.albumService.getAllAlbums();
        List<AlbumDTO> albumDTOs = new ArrayList<>();
        for (Album album : albums) {
            albumDTOs.add(AlbumDTOMapper.toDTO(album));
        }
        return ResponseEntity.ok(albumDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getAlbumById(@PathVariable int id) {
        Album album = this.albumService.getAlbumById(id);
        if (album != null) {
            return ResponseEntity.ok(AlbumDTOMapper.toDTO(album));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<AlbumDTO>> getAlbumsByNombre(@PathVariable String nombre) {
        List<Album> albums = this.albumService.getAlbumsByNombre(nombre);
        if (!albums.isEmpty()) {
            List<AlbumDTO> albumDTOs = new ArrayList<>();
            for (Album album : albums) {
                albumDTOs.add(AlbumDTOMapper.toDTO(album));
            }
            return ResponseEntity.ok(albumDTOs);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<AlbumDTO> createAlbum(@RequestBody Album album) {
        Album nuevoAlbum = this.albumService.createAlbum(album);
        return ResponseEntity.ok(AlbumDTOMapper.toDTO(nuevoAlbum));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDTO> updateAlbum(@PathVariable int id, @RequestBody Album album) {
        Album albumActualizado = this.albumService.updateAlbum(id, album);
        if (albumActualizado != null) {
            return ResponseEntity.ok(AlbumDTOMapper.toDTO(albumActualizado));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable int id) {
        if (this.albumService.deleteAlbum(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/artista/{idAlbum}")
    public ResponseEntity<String> artistaDeAlbum(@PathVariable int idAlbum) {
        String artista = this.albumService.artistaDeAlbum(idAlbum);
        if (artista != null) {
            return ResponseEntity.ok(artista);
        }
        return ResponseEntity.notFound().build();
    }
}
