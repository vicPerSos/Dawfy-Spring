package com.dawfy.controller;

import com.dawfy.controller.requestBody.UsuarioRequestBody;
import com.dawfy.persistence.entities.Usuario;
import com.dawfy.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> usuarios() {
        return ResponseEntity.ok(this.usuarioService.findAll());
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Optional<Usuario>> usuario(@PathVariable int idUsuario) {
        Optional<Usuario> usuario = this.usuarioService.findById(idUsuario);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/fecha/{idUsuario}")
    public ResponseEntity<String> fechaNacimiento(@PathVariable int idUsuario) {
        Optional<Usuario> usuario = this.usuarioService.findById(idUsuario);
        if (usuario.isPresent()) {
            if (usuario.get().getFechaNacimiento() != null) {
                return ResponseEntity.ok(usuario.get().getFechaNacimiento().toString());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/cumple/{dia}")
    public ResponseEntity<List<Usuario>> cumple(@PathVariable String dia) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fecha = LocalDate.parse(dia, formatter);
            return ResponseEntity.ok(this.usuarioService.usuarioPorCumple(fecha));
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pais/{pais}")
    public ResponseEntity<List<Usuario>> usuarioPorPais(@PathVariable String pais) {
        if (pais.length() != 2) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.usuarioService.usuariosPorPais(pais.toUpperCase()));
    }

    @GetMapping("paisDe/{idUsuario}")
    public ResponseEntity<String> paisDeUsuario(@PathVariable int idUsuario) {
        String respuesta = this.usuarioService.paisDeUsuario(idUsuario);
        if (respuesta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/correo/{idUsuario}")
    public ResponseEntity<String> correoDeUsuario(@PathVariable int idUsuario) {
        Optional<Usuario> usuario = this.usuarioService.findById(idUsuario);
        if (usuario.isPresent()) {
            if (usuario.get().getCorreo() != null) {
                return ResponseEntity.ok(usuario.get().getCorreo());
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/correoDe/{correo}")
    public ResponseEntity<
    Usuario> correoDe(@PathVariable String correo) {
        Optional<Usuario> usuario = this.usuarioService.usuarioPorCorreo(correo);
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario.get());
    }

    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody UsuarioRequestBody usuario) {
        Usuario respuesta = new Usuario();
        respuesta.setNombre(usuario.getNombre());
        respuesta.setCorreo(usuario.getCorreo());
        respuesta.setPais(usuario.getPais());
        respuesta.setFechaNacimiento(usuario.getFechaNacimiento());

        Usuario usuarioCreado = this.usuarioService.create(respuesta);
        return new ResponseEntity<Usuario>(this.usuarioService.create(usuarioCreado), HttpStatus.CREATED);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Usuario> actualizar(@PathVariable int idUsuario, @RequestBody UsuarioRequestBody usuario) {
        if (!this.usuarioService.exists(idUsuario)) {
            return ResponseEntity.notFound().build();
        }
        Usuario actualizable = this.usuarioService.findById(idUsuario).get();

        actualizable.setNombre(usuario.getNombre());
        actualizable.setCorreo(usuario.getCorreo());
        actualizable.setPais(usuario.getPais());
        actualizable.setFechaNacimiento(usuario.getFechaNacimiento());

        return ResponseEntity.ok(this.usuarioService.save(actualizable));
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Usuario> borrar(int idUsuario) {
        if (this.usuarioService.delete(idUsuario)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
