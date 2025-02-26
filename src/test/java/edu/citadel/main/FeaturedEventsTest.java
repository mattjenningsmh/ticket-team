package edu.citadel.main;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FeaturedEventsTest extends RestApiApplicationTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseEntity<String> response;

     @When("^the user searches featured events$")
    public void the_front_end_makes_a_get_request() throws Throwable {
        String url = "/ticket-team/events/featured";

        response = testRestTemplate.getForEntity(url, String.class); 
    }

    @Then("^our appliction receives their request$")
    public void our_application_can_receive_the_popular_categories_request() throws Throwable {
        // Verify the HTTP response status code
        Assertions.assertNotNull(response, "Response should not be null");
        HttpStatusCode currentStatusCode = response.getStatusCode();
        Assertions.assertEquals(200, currentStatusCode.value(), 
        "Expected status code 200, but got: " + currentStatusCode);
    }

    @And("^send featured events data back to the front end$")
    public void send_featured_events_data_back_to_the_front_end() throws Throwable {
         // Verify the response body
        String responseBody = response.getBody();
        Assertions.assertNotNull(responseBody, "Response body should not be null");

        // Parse the response JSON
        JsonNode jsonResponse = objectMapper.readTree(responseBody);
        Assertions.assertTrue(jsonResponse.isArray(), "Expected response to be an array");

        // Ensure the array is not empty
        Assertions.assertTrue(jsonResponse.size() > 0, "Expected at least one category in the response");
    }
    

}
