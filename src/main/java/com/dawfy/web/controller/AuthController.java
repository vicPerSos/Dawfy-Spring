package com.dawfy.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dawfy.domain.dto.LoginDto;
import com.dawfy.domain.dto.RegisterDto;
import com.dawfy.persistence.entities.Artista;
import com.dawfy.services.SpotifyService;
import com.dawfy.services.UserSecurityService;
import com.dawfy.web.config.JwtUtils;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private SpotifyService spotifyService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),
                loginDto.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(login);
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            String jwt = this.jwtUtils.create(loginDto.getUsername(), role);
            return ResponseEntity.ok(jwt);

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto request) {
        System.out.println("Register request: " + request);
        String result = userSecurityService.register(request);
        if (result.equals("Usuario registrado correctamente")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @PostMapping("/artistInfo")
    public ResponseEntity<JsonNode> getArtistInfo(@RequestBody String artistName) {
        JsonNode artistInfo = spotifyService.searchArtista(artistName);
        if (artistInfo != null && !artistInfo.isEmpty()) {
            return ResponseEntity.ok(artistInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
