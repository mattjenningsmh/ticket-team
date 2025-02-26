Feature: View Featured events
    Scenario: Endpoint to view featured events
    When the user searches featured events
    Then our appliction receives their request 
    And send featured events data back to the front end