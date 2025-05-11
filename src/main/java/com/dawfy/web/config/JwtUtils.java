package com.dawfy.web.config;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

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
}
