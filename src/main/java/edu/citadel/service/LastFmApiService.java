package edu.citadel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Service
public class LastFmApiService {
    @Value("${lastfm.api.base-url}")
    private String baseUrl;

    @Value("${lastfm.api.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public LastFmApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getArtistInfo(String artist) {
        String url = baseUrl + "?method=artist.getinfo&artist=" + artist + "&api_key=" + apiKey + "&format=json";

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "csci-602-ticket-team");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}