package com.dawfy.web.config;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String SECREY_KEY;

    private Algorithm ALGORITHM;

    @PostConstruct
    public void init() {
        this.ALGORITHM = Algorithm.HMAC256(SECREY_KEY);
    }

    public String create(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer("dawfy")
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
}
