package com.dawfy.web.config;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String SECREY_KEY;

    private Algorithm ALGORITHM;


    @PostConstruct
    public void init() {
        this.ALGORITHM = Algorithm.HMAC256(SECREY_KEY);
    }

    public String create(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withIssuer("dawfy")
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
                .sign(ALGORITHM);
    }

    public boolean isValid(String jwt) {
        try {
            JWT.require(ALGORITHM).build().verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUsername(String jwt) {
        return JWT.require(ALGORITHM).build().verify(jwt).getSubject();
    }

    public SimpleGrantedAuthority getRoleFromToken(String token) {
    DecodedJWT decodedJWT = JWT.require(ALGORITHM).build().verify(token);
    String role = decodedJWT.getClaim("role").asString();

    return new SimpleGrantedAuthority(role != null ? role : "ROLE_CLIENTE");
}

}
