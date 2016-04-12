package org.jboss.examples.ticketmonster.cucumber.stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.examples.ticketmonster.rest.dto.VenueDTO;
import org.jboss.examples.ticketmonster.model.Address;
import org.jboss.examples.ticketmonster.model.Venue;
import org.jboss.examples.ticketmonster.rest.VenueService;
import org.jboss.examples.ticketmonster.rest.VenueEndpoint;
import org.junit.Rule;
import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;
import org.powermock.modules.junit4.rule.PowerMockRule;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class CucumberBaseSteps {
	
	Response searchResponse = null;
	
    @Rule
    public PowerMockRule rule = new PowerMockRule();
    
    private VenueService venueService = Mockito.mock(VenueService.class);
    
	@Given("^a list of venues$")
	public void a_list_of_venues() throws Throwable {
		Venue venue = new Venue();
		
		Address address = new Address();
		address.setStreet("4 Yawkey Way");
		address.setCountry("USA");
		address.setCity("Boston");
		
		venue.setAddress(address);
		venue.setName("Fenway Park");
		
		Mockito.when(venueService.findByCityName("Boston")).thenReturn(Response.ok(venue).build());
		Mockito.when(venueService.findByCityName(AdditionalMatchers.not(org.mockito.Matchers.eq("Boston")))).thenReturn(Response.status(404).build());

	}
	
	
	// This should actually set it up so that there are no venues for this city, meaning we actually erase them
	@Given("^\"([^\"]*)\" has no venues$")
	public void has_no_venues(String cityName) throws Throwable {
		Mockito.when(venueService.findByCityName(AdditionalMatchers.not(org.mockito.Matchers.eq("Boston")))).thenReturn(Response.status(404).build());

		Response cityWithNoVenues = venueService.findByCityName(cityName);
		assertNotNull(cityWithNoVenues);
        int responseStatus = cityWithNoVenues.getStatus();
        assertEquals("This city isn't supposed to have venues!", Status.NOT_FOUND.getStatusCode(), responseStatus);
	}
	
	@Given("^\"([^\"]*)\" has at least one venue$")
	public void has_at_least_one_venue(String arg1) throws Throwable {
	}

	@When("^I search for \"([^\"]*)\"$")
	public void i_search_for(String cityName) throws Throwable {
		searchResponse = venueService.findByCityName(cityName);
	}	

	@Then("^I should receive details about the \"([^\"]*)\" venues$")
	public void i_should_receive_details_about_the_venues(String cityName) throws Throwable {
		assertNotNull(searchResponse);
        Venue venue = (Venue) searchResponse.getEntity();
        assertNotNull(venue);
        assertEquals("Venue is in the wrong city!", cityName, venue.getAddress().getCity());
	}
	
	@Then("^I should receive no results$")
	public void i_should_receive_no_results() throws Throwable {
		assertNotNull(searchResponse);
        assertEquals("Expected no venues, but found one!", Status.NOT_FOUND.getStatusCode(), searchResponse.getStatus() );
	}

}
