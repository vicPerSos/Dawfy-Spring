package com.dawfy.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dawfy.persistence.repositories.UsuarioCrudRepository;

@Service
public class AuthService {
    @Value("${jwt.secret}")
    private String jwtSecret;
    private UsuarioCrudRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

}
