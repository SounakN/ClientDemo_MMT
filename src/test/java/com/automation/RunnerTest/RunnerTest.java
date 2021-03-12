package com.automation.RunnerTest;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;



@RunWith(Cucumber.class)
@CucumberOptions(

		features = {"src/test/resources/features_Chrome"},
        plugin = { "pretty","html:target/cucumber-html-report", "json:target/cucumber.json" },
        glue = {"com/automation/StepDefinitions" },
         dryRun = false, monochrome = true)
				 


public class RunnerTest {

}
