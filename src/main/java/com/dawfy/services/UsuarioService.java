package com.dawfy.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawfy.persistence.entities.Usuario;
import com.dawfy.persistence.repositories.UsuarioCrudRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioCrudRepository usuarioCrudRepository;

    public List<Usuario> findAll() {
        return (List<Usuario>) this.usuarioCrudRepository.findAll();
    }

    public Optional<Usuario> findById(int id) {
        return this.usuarioCrudRepository.findById(id);
    }

    public Usuario create(Usuario usuario) {
        return this.usuarioCrudRepository.save(usuario);
    }

    public Usuario update(int id, Usuario usuario) {
        return this.usuarioCrudRepository.save(usuario);
    }

    public boolean exists(int id) {
        return this.usuarioCrudRepository.existsById(id);
    }

    public Usuario save(Usuario usuario) {
        return this.usuarioCrudRepository.save(usuario);
    }

    public boolean delete(int id) {
        if (this.usuarioCrudRepository.existsById(id)) {
            this.usuarioCrudRepository.deleteById(id);
            return true;
        }
        return false;
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

    public Usuario actualizarUsuario(int id, String correo) {
        Usuario usuario = this.usuarioCrudRepository.findById(id).get();
        usuario.setCorreo(correo);
        usuario = this.usuarioCrudRepository.save(usuario);
        return usuario;
    }
}
