package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepDefinitions",
        plugin={"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome=true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
