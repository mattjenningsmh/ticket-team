package edu.citadel.main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class EventsByPerformerTest extends RestApiApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private ResponseEntity<String> response;

    // Step 1: User selects or searches for a performer
    @When("^The user selects or searches for a specific perfomer (\\w+)$")
    public void the_user_selects_or_searches_for_a_specific_performer(String performer) throws Throwable {
          // Example performer
        String url = "/ticket-team/performers/" + performer;
        
        // Send GET request to fetch events for the performer
        response = testRestTemplate.getForEntity(url, String.class);
    }

    // Step 2: A request is sent to the backend for the performer
    @Then("^a request is sent for a list of events from that specific performer$")
    public void a_request_is_sent_for_a_list_of_events_from_that_specific_performer() throws Throwable {
        // Verify that the request status code is 200 (OK)
        Assertions.assertEquals(200, response.getStatusCode().value(), "Expected status code 200 but got: " + response.getStatusCode());
    }

    // Step 3: A list of events is displayed to the user for the performer
    @And("^a list of events is displayed to the user for the performer$")
    public void a_list_of_events_is_displayed_to_the_user_for_the_performer() throws Throwable {
        // Parse the response body into a JsonNode to verify the event data
        JsonNode responseBody = new ObjectMapper().readTree(response.getBody());

        // Assert that the response body is not null
        Assertions.assertNotNull(responseBody, "Response body should not be null");

        // Verify that the response is an array of events
        Assertions.assertTrue(responseBody.isArray(), "Response body should be an array of events");

        // Ensure that at least one event is returned for the performer
        Assertions.assertTrue(responseBody.size() > 0, "Response should contain at least one event");

        
    }
}
