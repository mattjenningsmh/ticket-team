package edu.citadel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class EventBriteApiService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${eventbrite.api.base-url}")
    private String baseUrl;

    @Value("${eventbrite.api.api-key}")
    private String apiKey;

    public String getEventsByCity(String city) {
        String url = baseUrl + "/events/search/?location.address=" + city + "&token=" + apiKey;
        return fetchDataFromApi(url);
    }

    public String getEventsByKeyword(String keyword) {
        String url = baseUrl + "/events/search/?q=" + keyword + "&token=" + apiKey;
        return fetchDataFromApi(url);
    }

    public String getEventDetailsById(String id) {
        String url = baseUrl + "/events/" + id + "/?token=" + apiKey;
        return fetchDataFromApi(url);
    }

    public String getEventsByOrganizer(String organizerId) {
        String url = baseUrl + "/organizers/" + organizerId + "/events/?token=" + apiKey;
        return fetchDataFromApi(url);
    }

    public String getEventsByVenue(String venueId) {
        String url = baseUrl + "/venues/" + venueId + "/events/?token=" + apiKey;
        return fetchDataFromApi(url);
    }

    private String fetchDataFromApi(String url) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching data from EventBrite API: " + e.getMessage(), e);
        }
    }
}