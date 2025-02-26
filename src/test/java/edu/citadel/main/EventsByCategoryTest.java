package edu.citadel.main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatusCode;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class EventsByCategoryTest extends RestApiApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseEntity<String> response;

       // When step: The user selects the "music" category
       @When("^the user selects the category (\\w+)$")
       public void the_user_selects_the_category(String category) throws Throwable {
           String url = "/ticket-team/category/" + category; // Endpoint for getting events by category
           response = testRestTemplate.getForEntity(url, String.class); // Make GET request to API
       }
       @Then("^a list of events matching our category is displayed$")
    public void a_list_of_events_matching_our_category_is_displayed() throws Throwable {
        // Verify the response status code
        HttpStatusCode currentStatusCode = response.getStatusCode();
        Assertions.assertEquals(200, currentStatusCode.value(), "Expected status code 200, but got: " + currentStatusCode.value());

        // Verify the response body contains events for the "music" category
        JsonNode responseBody = objectMapper.readTree(response.getBody());
        Assertions.assertNotNull(responseBody, "Response body should not be null");
        

        // Check if the response contains events (simplified check)
        Assertions.assertTrue(responseBody.size() > 0, "Response should contain at least one event");        
    }
}
