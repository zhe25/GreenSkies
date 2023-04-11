package controller.analysis;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * The class which runs the cucumber tests for the analysis package.
 *
 * @version 1.0
 * @since 04/10/2020
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "html:target/cucumber.html"},
    snippets = CucumberOptions.SnippetType.CAMELCASE)
public class RunCucumberForAnalysis {}
