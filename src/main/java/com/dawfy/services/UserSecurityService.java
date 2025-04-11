package com.dawfy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dawfy.persistence.entities.Usuario;
import com.dawfy.persistence.repositories.UsuarioCrudRepository;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UsuarioCrudRepository usuarioCrudRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioCrudRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.builder()
                .username(username)
                .password(usuario.getPassword())
                .roles(usuario.getRoll())
                .accountLocked(!usuario.isCuentaBloqueada())
                .disabled(!usuario.isEnabled())
                .build();
    }
}
