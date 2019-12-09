package com.API;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(dryRun=false,
				 tags={"@apiTEST"},
				 monochrome=true,
				 plugin={"pretty","html:target/cucumber-html-report", "json:target/cucumber.json"},
				 glue="stepDefinition",
				 features="features"
				 )

public class CukeRunner {

}
