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
import io.cucumber.java.en.And;

public class PopularCategoriesTest extends RestApiApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private ResponseEntity<String> response;

    // Test for getting popular categories
    @When("^the front end makes a get request$")
    public void the_front_end_makes_a_get_request_to_get_popular_categories() throws Throwable {
        String url = "/ticket-team/categories/popular"; // Endpoint for popular categories
        response = testRestTemplate.getForEntity(url, String.class);
    }

    @Then("^our application can receive the popular categories request$")
    public void our_application_can_receive_the_popular_categories_request() throws Throwable {
        // Verify the response status code
        HttpStatusCode currentStatusCode = response.getStatusCode();
        Assertions.assertEquals(200, currentStatusCode.value(), "Expected status code 200, but got: " + currentStatusCode.value());
    }

    @And("^send popular categories data back to the front end$")
    public void send_popular_categories_data_back_to_the_front_end() throws Throwable {
        // Verify the response body contains popular categories (simplified check)
        JsonNode responseBody = objectMapper.readTree(response.getBody());
        Assertions.assertNotNull(responseBody, "Response body should not be null");
        Assertions.assertTrue(responseBody.isArray(), "Response body should contain a list of categories");

        // Optionally, check specific categories if known
        Assertions.assertTrue(responseBody.size() > 0, "Response should contain at least one category");
    }
}
