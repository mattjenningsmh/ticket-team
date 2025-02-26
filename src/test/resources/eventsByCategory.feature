Feature: view events by category
    Scenario: Check if user can view events by category
        When the user selects the category music
        Then a list of events matching our category is displayed 
