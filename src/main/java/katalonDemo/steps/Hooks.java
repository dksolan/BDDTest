package katalonDemo.steps;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.core.TestUtil;

import java.util.concurrent.TimeUnit;

public class Hooks {

    public static WebDriver webDriver;

    @Before
    public void setup() throws Exception {
    	
    	TestUtil.initialize();
        
    }
    
    @After
    public void teardown(Scenario scenario) {
        
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "");
            } catch (WebDriverException noSupportScreenshot) {
                System.err.println(noSupportScreenshot.getMessage());
            }
        }
        webDriver.quit();
    }
}