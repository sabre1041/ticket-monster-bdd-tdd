package org.jboss.examples.ticketmonster.cucumber.stepdefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.examples.ticketmonster.model.Venue;
import org.jboss.examples.ticketmonster.rest.VenueService;
import org.jboss.examples.ticketmonster.test.rest.RESTDeployment;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@RunWith(Arquillian.class)
public class CucumberBaseSteps {
	
	Response searchResponse = null;
    
    @Deployment
    public static WebArchive deployment() {
        return RESTDeployment.deployment();
    }
   
    @Inject
    private VenueService venueService;

	@Given("^a list of venues$")
	public void a_list_of_venues() throws Throwable { 
	}
	
	
	// This should actually set it up so that there are no venues for this city, meaning we actually erase them
	@Given("^\"([^\"]*)\" has no venues$")
	public void has_no_venues(String cityName) throws Throwable {
		Response cityWithNoVenues = venueService.findByCityName(cityName);
		assertNotNull(searchResponse);
        int responseStatus = searchResponse.getStatus();
        assertEquals("This city isn't supposed to have venues!", Status.NOT_FOUND, responseStatus);
	}

	@When("^I search for \"([^\"]*)\"$")
	public void i_search_for(String cityName) throws Throwable {
		searchResponse = venueService.findByCityName(cityName);
	}
	
	@When("^I add \"([^\"]*)\" to \"([^\"]*)\"$")
	public void i_add_to(String venueName, String cityName) throws Throwable {
	     // This will hit the service to actually add the venue
		Response addToCityResponse = venueService.addVenueToCity(cityName, venueName);
		assertNotNull(addToCityResponse);
		assertEquals("Could not add the venue to the city!", Status.CREATED, addToCityResponse.getStatus() );
		
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
        assertEquals("Expected no venues, but found one!", Status.NOT_FOUND, searchResponse.getStatus() );
	}

}
