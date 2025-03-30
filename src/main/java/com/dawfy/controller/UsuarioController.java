package com.dawfy.controller;

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

import com.dawfy.controller.requestBody.UsuarioRequestBody;
import com.dawfy.persistence.entities.Usuario;
import com.dawfy.services.PaisService;
import com.dawfy.services.UsuarioService;
import com.dawfy.services.DTOs.UsuarioDTO;
import com.dawfy.services.Mappers.UsuarioDtoMapper;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PaisService paisService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> usuarios() {
        List<Usuario> usuarios = this.usuarioService.findAll();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            usuariosDTO.add(UsuarioDtoMapper.mapper(usuario));
        }
        
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> usuario(@PathVariable int idUsuario) {
        Optional<Usuario> usuario = this.usuarioService.findById(idUsuario);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(UsuarioDtoMapper.mapper(usuario.get()));
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
    public ResponseEntity<List<UsuarioDTO>> cumple(@PathVariable String dia) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fecha = LocalDate.parse(dia, formatter);
            List<Usuario> usuarios = this.usuarioService.usuarioPorCumple(fecha);
            List<UsuarioDTO> usuariosDTO = new ArrayList<>();
            for (Usuario usuario : usuarios) {
                usuariosDTO.add(UsuarioDtoMapper.mapper(usuario));
            }
            return ResponseEntity.ok(usuariosDTO);
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pais/{pais}")
    public ResponseEntity<List<UsuarioDTO>> usuarioPorPais(@PathVariable String pais) {
        if (pais.length() != 2) {
            return ResponseEntity.badRequest().build();
        }
        List<Usuario> usuarios = this.usuarioService.usuariosPorPais(pais.toUpperCase());
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuariosDTO.add(UsuarioDtoMapper.mapper(usuario));
        }
        return ResponseEntity.ok(usuariosDTO);
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
    public ResponseEntity<UsuarioDTO> correoDe(@PathVariable String correo) {
        Optional<Usuario> usuario = this.usuarioService.usuarioPorCorreo(correo);
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UsuarioDtoMapper.mapper(usuario.get()));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioRequestBody usuario) {
        Usuario respuesta = new Usuario();
        respuesta.setNombre(usuario.getNombre());
        respuesta.setCorreo(usuario.getCorreo());
        respuesta.setPais(this.paisService.findById(usuario.getPais()));
        respuesta.setFechaNacimiento(usuario.getFechaNacimiento());
        return ResponseEntity.ok(UsuarioDtoMapper.mapper(this.usuarioService.create(respuesta)));
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable int idUsuario, @RequestBody UsuarioRequestBody usuario) {
        if (!this.usuarioService.exists(idUsuario)) {
            return ResponseEntity.notFound().build();
        }
        if (!(usuario.getId() == idUsuario)) {
            return ResponseEntity.badRequest().build();

        }
        Usuario actualizable = this.usuarioService.findById(idUsuario).get();

        actualizable.setNombre(usuario.getNombre());
        actualizable.setCorreo(usuario.getCorreo());
        actualizable.setPais(this.paisService.findById(usuario.getPais()));
        actualizable.setFechaNacimiento(usuario.getFechaNacimiento());

        return ResponseEntity.ok(UsuarioDtoMapper.mapper(this.usuarioService.save(actualizable)));
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> borrar(int idUsuario) {
        if (this.usuarioService.delete(idUsuario)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
