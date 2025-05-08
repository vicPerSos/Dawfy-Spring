package com.dawfy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class SpotifyService {

    @Autowired
    private RestTemplate restTemplate;

    public JsonNode search(String query) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.spotify.com/v1/search")
                .queryParam("q", query)
                .queryParam("type", "track,album,artist")
                .toUriString();
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode getTrack(String trackId) {
        String url = "https://api.spotify.com/v1/tracks/" + trackId;
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode getAlbum(String albumId) {
        String url = "https://api.spotify.com/v1/albums/" + albumId;
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode getArtist(String artistId) {
        String url = "https://api.spotify.com/v1/artists/" + artistId;
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode getAlbumsByArtist(String artistId) {
        String url = "https://api.spotify.com/v1/artists/" + artistId + "/albums";
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode getTracksByAlbum(String albumId) {
        String url = "https://api.spotify.com/v1/albums/" + albumId + "/tracks";
        return restTemplate.getForObject(url, JsonNode.class);
    }

}
