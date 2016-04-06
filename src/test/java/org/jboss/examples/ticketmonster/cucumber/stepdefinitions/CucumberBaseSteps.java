package org.jboss.examples.ticketmonster.cucumber.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CucumberBaseSteps {
	
	@Given("^you exsit$")
	public void you_exsit() throws Throwable {
	    // do nothing
	}

	@When("^you see the world$")
	public void you_see_the_world() throws Throwable {
	    // do nothing
	}

	@Then("^say \"([^\"]*)\"$")
	public void say(String arg1) throws Throwable {
	    // do nothing
	}

}
