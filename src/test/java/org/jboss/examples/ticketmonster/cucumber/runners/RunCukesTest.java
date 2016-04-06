package org.jboss.examples.ticketmonster.cucumber.runners;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"org.jboss.examples.ticketmonster.cucumber.stepdefinitions"}, tags = {"@HelloWorld"}, features = "src/test/resources/features", strict = true )
public class RunCukesTest {

}
