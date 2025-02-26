Feature: view events by performer
    Scenario: the application displays a list of performers and events for a given name
        When The user selects or searches for a specific perfomer eden 
        Then a request is sent for a list of events from that specific performer 
        And a list of events is displayed to the user for the performer