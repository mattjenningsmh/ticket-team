package edu.citadel.main;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class EventDetailsTest extends RestApiApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseEntity<String> response;

    @When("^the client selects an event with id ([^\"\\\"]+)$")
    public void the_client_selects_an_event_with_id(String eventId) throws Throwable {
        String url = "/ticket-team/events/"+eventId; 
        response = testRestTemplate.getForEntity(url, String.class);
    }

    @Then("^the application displays event details to the client$")
    public void the_application_displays_event_details_to_the_client() throws Throwable {
        // Verify that the status code returned is 200 (OK)
        Assertions.assertEquals(200, response.getStatusCode(), "Expected status code 200");

        // Optionally, check the response body (e.g., ensure it contains specific event details)
        JsonNode responseBody = new ObjectMapper().readTree(response.getBody());
        
        // Ensure the response body is not null
        Assertions.assertNotNull(responseBody, "Response body should not be null");

        // Example of checking for a specific field, e.g., event title
        Assertions.assertTrue(responseBody.has("eventTitle"), "Response should contain eventTitle field");
        
        // Optionally check if the event details contain expected information, like the event ID
        Assertions.assertEquals("17AYvOG6GbfH7QD", responseBody.get("eventId").asText(), "Event ID should match");
    }

    
}
