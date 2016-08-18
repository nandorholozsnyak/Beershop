package hu.hnk.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format={"pretty",
		  "html:target/test-report",
			"json:target/test-report.json",
			"junit:target/test-report.xml"},features = "src/test/resources/features")
public class CucumberTest {

}
