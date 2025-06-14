package com.dawfy.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawfy.persistence.entities.Album;
import com.dawfy.persistence.entities.Artista;
import com.dawfy.services.AlbumService;
import com.dawfy.services.ArtistaService;
import com.dawfy.services.SpotifyService;
import com.dawfy.services.UserSecurityService;
import com.dawfy.services.DTOs.AlbumDTO;
import com.dawfy.services.Mappers.AlbumDTOMapper;
import com.dawfy.web.requestBody.album.AlbumRequestBodyPOST;
import com.dawfy.web.requestBody.album.AlbumRequestBodyPUT;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ArtistaService artistaService;
    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private SpotifyService spotifyService;

    @Operation(summary = "Obtener todos los álbumes", description = "Retorna una lista de todos los álbumes disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de álbumes obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getAllAlbums() {
        List<Album> albums = this.albumService.getAllAlbums();
        List<AlbumDTO> albumDTOs = new ArrayList<>();
        for (Album album : albums) {
            albumDTOs.add(AlbumDTOMapper.toDTO(album));
        }
        return ResponseEntity.ok(albumDTOs);
    }

    @GetMapping("/artista")
    public ResponseEntity<JsonNode> getAlbumsByArtista(Authentication authentication) {
        String username = authentication.getName();
        Artista artista = this.artistaService.artistaByUsername(username);

        JsonNode albums = this.spotifyService.getAllAlbumsByArtist(artista.getIdArtistaSpoti());
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/artistaUsername")
    public ResponseEntity<List<AlbumDTO>> getAlbumsByUsername(Authentication authentication) {
        String username = authentication.getName();
        Artista artista = this.artistaService.artistaByUsername(username);

        List<Album> albumes = this.albumService.getAlbumsByArtistaUsername(artista.getUsername());
        List<AlbumDTO> albums = new ArrayList<>();
        for (Album album : albumes) {
            albums.add(AlbumDTOMapper.toDTO(album));
        }
        return ResponseEntity.ok(albums);
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
    public ResponseEntity<AlbumDTO> createAlbum(@RequestBody AlbumRequestBodyPOST albumRequestBody) {
        Album album = new Album();
        album.setNombre(albumRequestBody.getNombre());
        album.setFechaLanzamiento(albumRequestBody.getFechaLanzamiento());
        try {
            if (!this.artistaService.existsArtista(albumRequestBody.getArtista())) {
                throw new Exception("Artista no existe");
            }
            album.setIdArtista(albumRequestBody.getArtista());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        album.setImagen(albumRequestBody.getImagen());
        Album nuevoAlbum = this.albumService.createAlbum(album);
        return ResponseEntity.ok(AlbumDTOMapper.toDTO(nuevoAlbum));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDTO> updateAlbum(@PathVariable int id,
            @RequestBody AlbumRequestBodyPUT albumRequestBody) {
        Album album = new Album();
        album.setId(id);
        album.setNombre(albumRequestBody.getNombre());
        album.setFechaLanzamiento(albumRequestBody.getFechaLanzamiento());
        try {
            if (!this.artistaService.existsArtista(albumRequestBody.getArtista())) {
                throw new Exception("Artista no existe");
            }
            album.setIdArtista(albumRequestBody.getArtista());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        album.setImagen(albumRequestBody.getImagen());
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
