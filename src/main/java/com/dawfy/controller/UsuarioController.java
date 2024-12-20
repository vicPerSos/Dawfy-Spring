package com.dawfy.controller;

import com.dawfy.persistence.entities.Usuario;
import com.dawfy.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> usuarios() {
        return ResponseEntity.ok(this.usuarioService.finAll());
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Optional<Usuario>> usuario(@RequestParam int idUsuario) {
        Optional<Usuario> usuario = this.usuarioService.findById(idUsuario);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/fecha/{idUsuario}")
    public ResponseEntity<String> fechaNacimiento(@RequestParam int idUsuario) {
        Optional<Usuario> usuario = this.usuarioService.findById(idUsuario);
        if (usuario.isPresent()) {
            if (usuario.get().getFechaNacimiento() != null) {
                return ResponseEntity.ok(usuario.get().getFechaNacimiento().toString());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/correo/{idUsuario}")
    public ResponseEntity<String> correoDeUsuario(@RequestParam int idUsuario) {
        Optional<Usuario> usuario = this.usuarioService.findById(idUsuario);
        if (usuario.isPresent()) {
            if (usuario.get().getCorreo() != null) {
                return ResponseEntity.ok(usuario.get().getCorreo());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/correoDe/{correo}")
    public ResponseEntity<Usuario> correoDe(@RequestParam String correo) {
        Optional<Usuario> usuario = this.usuarioService.usuarioPorCorreo(correo);
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario.get());
    }

    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestParam Usuario usuario) {
        return new ResponseEntity<Usuario>(this.usuarioService.create(usuario), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Usuario> actualizar(@PathVariable int idUsuario, @RequestParam Usuario usuario) {
        if (!this.usuarioService.exists(idUsuario)) {
            return ResponseEntity.notFound().build();
        }
        if (usuario.getId() != idUsuario) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.usuarioService.save(usuario));
    }

    @DeleteMapping
    public ResponseEntity<Usuario> borrar(int idUsuario) {
        if (this.usuarioService.delete(idUsuario)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
