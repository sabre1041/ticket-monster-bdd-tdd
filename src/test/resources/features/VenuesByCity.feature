@not_implemented
Feature: Search and Add Venues by City

Scenario: Search for all venues of a given city
Given a list of venues
When I search for "Boston"
Then I should receive details about the "Boston" venues


Scenario: Search for venues in a city with no venues
Given a list of venues
When I search for "Charlotte"
Then I should receive no results

Scenario: Add a venue to a city with no venues
Given "Charlotte" has no venues
When I add "Time Warner Cable Arena" to "Charlotte"
And I search for "Charlotte"
Then I should receive details about the "Charlotte" venues