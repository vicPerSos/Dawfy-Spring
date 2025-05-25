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

import com.dawfy.persistence.entities.Usuario;
import com.dawfy.services.PaisService;
import com.dawfy.services.UsuarioService;
import com.dawfy.services.DTOs.UsuarioDTO;
import com.dawfy.services.Mappers.UsuarioDtoMapper;
import com.dawfy.web.requestBody.usuario.UsuarioRequestBodyPOST;
import com.dawfy.web.requestBody.usuario.UsuarioRequestBodyPUT;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PaisService paisService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> usuarios() {
        List<Usuario> usuarios = this.usuarioService.getAllUsuarios();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuariosDTO.add(UsuarioDtoMapper.mapper(usuario));
        }
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> usuario(@PathVariable int idUsuario) {
        Optional<Usuario> usuario = this.usuarioService.getUsuarioById(idUsuario);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(UsuarioDtoMapper.mapper(usuario.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<UsuarioDTO>> usuarioPorNombre(@PathVariable String nombre) {
        List<Usuario> usuarios = this.usuarioService.usuarioPorNombre(nombre);
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuariosDTO.add(UsuarioDtoMapper.mapper(usuario));
        }
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/fecha/{idUsuario}")
    public ResponseEntity<String> fechaNacimiento(@PathVariable int idUsuario) {
        Optional<Usuario> usuario = this.usuarioService.getUsuarioById(idUsuario);
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
        Optional<Usuario> usuario = this.usuarioService.getUsuarioById(idUsuario);
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
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioRequestBodyPOST usuario) {
        try {
            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setNombre(usuario.getNombre());
            usuarioNuevo.setCorreo(usuario.getCorreo());
            usuarioNuevo.setPais(this.paisService.findById(usuario.getPais()));

            usuarioNuevo.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioNuevo.setFoto(usuario.getFoto() != null ? usuario.getFoto()
                    : "http://i.scdn.co/image/ab6761610000517476b4b22f78593911c60e7193");
            if (usuario.getPassword() == null) {
                return ResponseEntity.badRequest().build();
            }
            usuarioNuevo.setPassword(usuario.getPassword());
            if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            usuarioNuevo.setUsername(usuario.getUsername());
            return ResponseEntity.ok(UsuarioDtoMapper.mapper(this.usuarioService.saveUsuario(usuarioNuevo)));
        } catch (Exception e) {
            System.out.println("La peticion no realizó correctamente. Error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable int idUsuario,
            @RequestBody UsuarioRequestBodyPUT usuario) {
        if (!this.usuarioService.existsUsuario(idUsuario)) {
            return ResponseEntity.notFound().build();
        }
        if (!(usuario.getId() == idUsuario)) {
            return ResponseEntity.badRequest().build();
        }
        Usuario usuarioNuevo = this.usuarioService.getUsuarioById(idUsuario).get();

        usuarioNuevo.setNombre(usuario.getNombre());
        usuarioNuevo.setCorreo(usuario.getCorreo());
        usuarioNuevo.setPais(this.paisService.findById(usuario.getPais()));
        usuarioNuevo.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioNuevo.setFoto(usuario.getFoto() != null ? usuario.getFoto() : this.usuarioService.getUsuarioById(idUsuario).get().getFoto());
        if (usuario.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }
        usuarioNuevo.setPassword(usuario.getPassword());

        return ResponseEntity.ok(UsuarioDtoMapper.mapper(this.usuarioService.updateUsuario(idUsuario, usuarioNuevo)));
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> borrar(int idUsuario) {
        if (this.usuarioService.deleteUsuario(idUsuario)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
