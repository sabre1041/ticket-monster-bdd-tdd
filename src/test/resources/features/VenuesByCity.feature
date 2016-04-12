
Feature: Search and Add Venues by City

Scenario: Search for all venues of a given city
Given a list of venues
And "Boston" has at least one venue
When I search for "Boston"
Then I should receive details about the "Boston" venues


Scenario: Search for venues in a city with no venues
Given a list of venues
And "Charlotte" has no venues
When I search for "Charlotte"
Then I should receive no results

