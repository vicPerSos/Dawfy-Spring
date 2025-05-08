package com.dawfy.web.config;


import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class SpotifyTokenManager {
@Value("${spotify.client-id}")
    private String clientId;

    @Value("${spotify.client-secret}")
    private String clientSecret;

    private String accessToken;
    private Instant expirationTime;
    private final RestTemplate restTemplate = new RestTemplate();

    public synchronized String getValidToken() {
        if (accessToken == null || Instant.now().isAfter(expirationTime)) {
            renewToken();
        }
        return accessToken;
    }

    private void renewToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                "https://accounts.spotify.com/api/token",
                new HttpEntity<>(body, headers),
                JsonNode.class);

        this.accessToken = response.getBody().get("access_token").asText();
        this.expirationTime = Instant.now().plusSeconds(3600); // 1 hora de validez
    }
}

