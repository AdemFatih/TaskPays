package com.pays.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-report.html"},
        features = "src/test/resources/features",
        glue = "com/pays/step_definitions",
        dryRun =false,
        tags = "@UI"
)
public class CukesRunner {
}