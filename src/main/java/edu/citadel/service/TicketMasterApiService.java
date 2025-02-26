package edu.citadel.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.http.ResponseEntity;

@Service
public class TicketMasterApiService {

    @Value("${ticketmaster.api.base-url}")
    private String baseUrl;

    @Value("${ticketmaster.api.api-key}")
    private String apiKey;

    public String getEventsByCity(String city) {
        String url = baseUrl + "events.json?apikey=" + apiKey + "&city=" + city;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String getEventDetailsById(String eventId) {
        String url = baseUrl + "events/" + eventId + ".json?apikey=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String getEventsByPerformer(String performer) {
        String url = baseUrl + "/events.json?keyword=" + performer + "&apikey=" + apiKey;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());

            // Parse and format response
            JsonNode events = root.path("_embedded").path("events");
            StringBuilder formattedResponse = new StringBuilder("[");
            for (JsonNode event : events) {
                String eventId = event.path("id").asText();
                String eventName = event.path("name").asText();
                String eventDate = event.path("dates").path("start").path("localDate").asText();
                String venue = event.path("_embedded").path("venues").get(0).path("name").asText();

                formattedResponse.append(String.format(
                    "{\"id\":\"%s\", \"name\":\"%s\", \"date\":\"%s\", \"venue\":\"%s\"},",
                    eventId, eventName, eventDate, venue
                ));
            }
            // Remove trailing comma and close JSON array
            if (formattedResponse.length() > 1) {
                formattedResponse.setLength(formattedResponse.length() - 1);
            }
            formattedResponse.append("]");

            return formattedResponse.toString();
        } catch (HttpClientErrorException ex) {
            return "{\"error\":\"" + ex.getMessage() + "\"}";
        } catch (Exception ex) {
            return "{\"error\":\"An unexpected error occurred\"}";
        }
    }

    public String getPopularCategories() {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Fetch all events
            String eventsUrl = baseUrl + "/events.json?apikey=" + apiKey;
            ResponseEntity<String> response = restTemplate.getForEntity(eventsUrl, String.class);
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode events = root.path("_embedded").path("events");

            if (events.isEmpty()) {
                return "{\"message\":\"No events found.\"}";
            }

            // Group events by category
            Map<String, List<JsonNode>> categoryMap = new HashMap<>();
            for (JsonNode event : events) {
                JsonNode classifications = event.path("classifications");
                if (!classifications.isMissingNode() && classifications.size() > 0) {
                    String category = classifications.get(0).path("segment").path("name").asText();
                    categoryMap.computeIfAbsent(category, k -> new ArrayList<>()).add(event);
                }
            }

            // Sort categories by number of events
            List<Map.Entry<String, List<JsonNode>>> sortedCategories = categoryMap.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(Collectors.toList());

            // Build the response
            List<ObjectNode> popularCategories = new ArrayList<>();
            for (Map.Entry<String, List<JsonNode>> entry : sortedCategories) {
                ObjectNode categoryNode = mapper.createObjectNode();
                categoryNode.put("name", entry.getKey());
                List<ObjectNode> eventNodes = entry.getValue().stream().map(event -> {
                    ObjectNode eventNode = mapper.createObjectNode();
                    eventNode.put("id", event.path("id").asText());
                    eventNode.put("name", event.path("name").asText());
                    eventNode.put("date", event.path("dates").path("start").path("localDate").asText());
                    eventNode.put("venue", event.path("_embedded").path("venues").get(0).path("name").asText());
                    return eventNode;
                }).collect(Collectors.toList());
                categoryNode.set("events", mapper.valueToTree(eventNodes));
                popularCategories.add(categoryNode);
            }

            return mapper.writeValueAsString(popularCategories);
        } catch (Exception ex) {
            return "{\"error\":\"An unexpected error occurred: " + ex.getMessage() + "\"}";
        }
    }

    public String getEventsByVenue(String venue) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
    
        try {
            // Encode the venue parameter to handle spaces and special characters
            String encodedVenue = URLEncoder.encode(venue, StandardCharsets.UTF_8.toString());
    
            // Fetch events by venue name directly
            String eventsUrl = baseUrl + "/events.json?apikey=" + apiKey + "&keyword=" + encodedVenue;
            ResponseEntity<String> eventsResponse = restTemplate.getForEntity(eventsUrl, String.class);
            JsonNode eventsRoot = mapper.readTree(eventsResponse.getBody());
            JsonNode events = eventsRoot.path("_embedded").path("events");
    
            if (events.isEmpty()) {
                return "{\"message\":\"No events found for venue: " + venue + "\"}";
            }
    
            // Map and format the response
            List<JsonNode> formattedEvents = new ArrayList<>();
            for (JsonNode event : events) {
                String id = event.path("id").asText();
                String name = event.path("name").asText();
                String date = event.path("dates").path("start").path("localDate").asText();
                String eventVenue = event.path("_embedded").path("venues").get(0).path("name").asText();
    
                formattedEvents.add(
                    mapper.createObjectNode()
                        .put("id", id)
                        .put("name", name)
                        .put("date", date)
                        .put("venue", eventVenue)
                );
            }
    
            return mapper.writeValueAsString(formattedEvents);
        } catch (Exception ex) {
            return "{\"error\":\"An unexpected error occurred: " + ex.getMessage() + "\"}";
        }
    }
    
    

    public String getFeaturedEvents() {
        String url = baseUrl + "/events.json?apikey=" + apiKey + "&sort=relevance,desc";
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode events = root.path("_embedded").path("events");

            // Map and format the response
            List<JsonNode> formattedEvents = new ArrayList<>();
            for (JsonNode event : events) {
                String id = event.path("id").asText();
                String name = event.path("name").asText();
                String date = event.path("dates").path("start").path("localDate").asText();
                String venue = event.path("_embedded").path("venues").get(0).path("name").asText();

                formattedEvents.add(
                    mapper.createObjectNode()
                        .put("id", id)
                        .put("name", name)
                        .put("date", date)
                        .put("venue", venue)
                );
            }

            return mapper.writeValueAsString(formattedEvents);
        } catch (Exception ex) {
            return "{\"error\":\"An unexpected error occurred: " + ex.getMessage() + "\"}";
        }
    }

    public String getEventsByCategory(String category) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Encode category to handle special characters and spaces
            String encodedCategory = URLEncoder.encode(category, StandardCharsets.UTF_8.toString());

            // Fetch events by category
            String eventsUrl = baseUrl + "/events.json?apikey=" + apiKey + "&classificationName=" + encodedCategory;
            ResponseEntity<String> response = restTemplate.getForEntity(eventsUrl, String.class);
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode events = root.path("_embedded").path("events");

            if (events.isEmpty()) {
                return "{\"message\":\"No events found for category: " + category + "\"}";
            }

            // Map and format the response
            List<JsonNode> formattedEvents = new ArrayList<>();
            for (JsonNode event : events) {
                String id = event.path("id").asText();
                String name = event.path("name").asText();
                String date = event.path("dates").path("start").path("localDate").asText();
                String venue = event.path("_embedded").path("venues").get(0).path("name").asText();

                formattedEvents.add(
                    mapper.createObjectNode()
                        .put("id", id)
                        .put("name", name)
                        .put("date", date)
                        .put("venue", venue)
                );
            }

            // Wrap in category object
            ObjectNode responseJson = mapper.createObjectNode();
            responseJson.put("name", category);
            responseJson.set("events", mapper.valueToTree(formattedEvents));

            return responseJson.toString();
        } catch (Exception ex) {
            return "{\"error\":\"An unexpected error occurred: " + ex.getMessage() + "\"}";
        }
    }
}
