package com.dawfy.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dawfy.persistence.entities.Usuario;
import com.dawfy.persistence.repositories.UsuarioCrudRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioCrudRepository usuarioCrudRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> getAllUsuarios() {
        return (List<Usuario>) this.usuarioCrudRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(int id) {
        return this.usuarioCrudRepository.findById(id);
    }

    public Usuario saveUsuario(Usuario usuario) {
        usuario.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
        return this.usuarioCrudRepository.save(usuario);
    }

    public Usuario updateUsuario(int id, Usuario usuario) {
        Usuario usuarioExistente = this.usuarioCrudRepository.findById(id).get();

        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setCorreo(usuario.getCorreo());
        usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioExistente.setPais(usuario.getPais());
        usuarioExistente.setFoto(usuario.getFoto());
        usuarioExistente.setPassword(this.passwordEncoder.encode(usuario.getPassword()));

        return this.usuarioCrudRepository.save(usuario);
    }

    public boolean existsUsuario(int id) {
        return this.usuarioCrudRepository.existsById(id);
    }

    public boolean deleteUsuario(int id) {
        if (!this.usuarioCrudRepository.existsById(id)) {
            return false;
        }
        this.usuarioCrudRepository.deleteById(id);
        return true;
    }

    public List<Usuario> usuariosPorPais(String nombre) {
        return this.usuarioCrudRepository.findByPais(nombre);
    }

    public String paisDeUsuario(int idUsuario) {
        try {
            Optional<Usuario> usuario = this.usuarioCrudRepository.findById(idUsuario);
            if (usuario.isEmpty()) {
                throw new Exception("No existe el usuario");
            }
            return usuario.get().getPais().getNombre();

        } catch (Exception e) {
            return null;
        }
    }

    public Optional<Usuario> usuarioPorCorreo(String correo) {
        return this.usuarioCrudRepository.findByCorreo(correo);
    }

    public List<Usuario> usuarioPorCumple(LocalDate fechaNacimiento) {
        return this.usuarioCrudRepository.findByFechaNacimiento(fechaNacimiento);
    }

    public String correoUsuario(int id) {
        return this.usuarioCrudRepository.findById(id).get().getCorreo();
    }

    public LocalDate fechaUsuario(int id) {
        return this.usuarioCrudRepository.findById(id).get().getFechaNacimiento();
    }

    public List<Usuario> usuarioPorNombre(String nombre) {
        return this.usuarioCrudRepository.findByNombreStartingWithIgnoreCase(nombre);
    }
}