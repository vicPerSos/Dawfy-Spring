package com.dawfy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dawfy.domain.dto.RegisterDto;
import com.dawfy.enums.Roles;
import com.dawfy.persistence.entities.Artista;
import com.dawfy.persistence.entities.Cliente;
import com.dawfy.persistence.entities.Usuario;
import com.dawfy.persistence.repositories.ArtistaCrudRepository;
import com.dawfy.persistence.repositories.ClienteCrudRepository;
import com.dawfy.persistence.repositories.UsuarioCrudRepository;

@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UsuarioCrudRepository usuarioCrudRepository;
    @Autowired
    private ArtistaCrudRepository artistaCrudRepository;
    @Autowired
    private ClienteCrudRepository clienteCrudRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PaisService paisService;

    public String register(RegisterDto request) {
        if (usuarioCrudRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Nombre de usuario ya en uso";
        }
        if (request.getRoll().equals(Roles.ARTISTA.name())) {
            Artista user = new Artista();
            user.setNombre(request.getNombre());
            user.setCorreo(request.getCorreo());
            user.setFechaNacimiento(request.getFechaNacimiento());
            user.setPais(this.paisService.findById(request.getPais()));
            if (request.getFoto() != null) {
                user.setFoto(request.getFoto());
            } else {
                user.setFoto("http://i.scdn.co/image/ab6761610000517476b4b22f78593911c60e7193");
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            user.setHabilitada(true);
            user.setCuentaBloqueada(false);
            user.setCredencialExpirada(false);
            user.setCredencialExpirada(false);
            user.setRoll(Roles.ARTISTA.name());
            user.setUsername(request.getUsername());
            user.setIdArtistaSpoti(request.getSpotifyId());
            this.artistaCrudRepository.save(user);
        } else {
            Cliente user = new Cliente();
            user.setNombre(request.getNombre());
            user.setCorreo(request.getCorreo());
            user.setFechaNacimiento(request.getFechaNacimiento());
            user.setPais(this.paisService.findById(request.getPais()));
            if (request.getFoto() != null) {
                user.setFoto(request.getFoto());
            } else {
                user.setFoto("http://i.scdn.co/image/ab6761610000517476b4b22f78593911c60e7193");
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            user.setHabilitada(true);
            user.setCuentaBloqueada(false);
            user.setCredencialExpirada(false);
            user.setCredencialExpirada(false);
            user.setRoll(Roles.CLIENTE.name());
            user.setUsername(request.getUsername());
            this.clienteCrudRepository.save(user);
        }
        return "Usuario registrado correctamente";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioCrudRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        String roll = usuario.getRoll();
        String[] roles = { roll };

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .accountLocked(usuario.isCuentaBloqueada())
                .disabled(!usuario.isHabilitada())
                .authorities(roles)
                .build();

    }

}
