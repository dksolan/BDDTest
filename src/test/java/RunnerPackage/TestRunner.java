package RunnerPackage;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;


@CucumberOptions(plugin = {
        "html:target/cucumber/cucumber.html" ,
        "me.jvt.cucumber.report.PrettyReports:target/cucumber/"
        },
        glue = {"katalonDemo.steps"},
        features = "src/test/resources/features",
        tags = "@smoke")
public class TestRunner extends AbstractTestNGCucumberTests{


}
