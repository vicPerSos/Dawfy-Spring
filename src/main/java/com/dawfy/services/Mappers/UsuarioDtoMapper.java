package com.dawfy.services.Mappers;

import com.dawfy.persistence.entities.Usuario;
import com.dawfy.services.DTOs.UsuarioDTO;

public class UsuarioDtoMapper {
    public static UsuarioDTO mapper(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        UsuarioDTO usuarioDto = new UsuarioDTO();
        usuarioDto.setId(usuario.getId());
        usuarioDto.setNombre(usuario.getNombre());
        usuarioDto.setCorreo(usuario.getCorreo());
        usuarioDto.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioDto.setPais(usuario.getPais().getNombre());
        usuarioDto.setFoto(usuario.getFoto());
        return usuarioDto;
    }
}
