package com.dawfy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class SpotifyService {

    @Autowired
    private RestTemplate restTemplate;

    public JsonNode search(String query) {
        @SuppressWarnings("deprecation")
        String url = UriComponentsBuilder.fromHttpUrl("https://api.spotify.com/v1/search")
                .queryParam("q", query)
                .queryParam("type", "track,album,artist")
                .toUriString();
        return restTemplate.getForObject(url, JsonNode.class);
    }

    public JsonNode searchArtista(String query) {
        @SuppressWarnings("deprecation")
        String url = UriComponentsBuilder.fromHttpUrl("https://api.spotify.com/v1/search")
                .queryParam("q", query)
                .queryParam("type", "artist")
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

    public JsonNode getAllAlbumsByArtist(String artistId) {
        String baseUrl = "https://api.spotify.com/v1/artists/" + artistId + "/albums";
        int limit = 50;
        int offset = 0;
        boolean more = true;

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode allAlbums = mapper.createArrayNode();

        while (more) {
            String url = baseUrl + "?limit=" + limit + "&offset=" + offset;
            JsonNode response = restTemplate.getForObject(url, JsonNode.class);

            if (response != null && response.has("items")) {
                ArrayNode items = (ArrayNode) response.get("items");
                for (JsonNode album : items) {
                    JsonNode artists = album.get("artists");
                    if (artists != null && artists.isArray() && artists.size() > 0) {
                        String mainArtistId = artists.get(0).get("id").asText();
                        if (artistId.equals(mainArtistId)) {
                            allAlbums.add(album);
                        }
                    }
                }
                if (response.has("next") && !response.get("next").isNull()) {
                    offset += limit;
                } else {
                    more = false;
                }
            } else {
                more = false;
            }
        }

        ObjectNode result = mapper.createObjectNode();
        result.set("albums", allAlbums);
        return result;
    }

    public JsonNode getTracksByAlbum(String albumId) {
        String url = "https://api.spotify.com/v1/albums/" + albumId + "/tracks";
        return restTemplate.getForObject(url, JsonNode.class);
    }

}
