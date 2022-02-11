package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/TestCases",
        glue = {"StepDefs"},
        tags = {"@setup"},
        monochrome = true,
        strict = true
)
public class PreTestRunner {

}
