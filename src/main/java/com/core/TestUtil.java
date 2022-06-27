package com.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


public class TestUtil {
	
	
	protected static WebDriver driver = null;
	protected static WebDriverWait wait = null;
	static Properties properties = null;
	final static Logger logger = Logger.getLogger(TestUtil.class);
//    public static LocalDate ExplicitTimeOut = LocalDate.now().wait(Long.parseLong(properties.getProperty("DefaultExplictWait")));


  
	public TestUtil() {
 
        }

	
	public static WebDriver getDriver() {
		return driver;
	}
	
	
	public static WebDriver initialize() throws InterruptedException {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
	        System.setProperty("currentDateTime", dateFormat.format(new Date()));
			properties = readPropertiesFile();
			if (properties.getProperty("browserType").equalsIgnoreCase("chrome")) {
				driver = initChromeDriver();
			} else if(properties.getProperty("browserType").equalsIgnoreCase("fireFox")) {
				driver = initFirefoxDriver();
			}else
                driver = initChromeDriver();
				logger.info("Browser : " + properties.getProperty("browserType")+ " is invalid, Launching Chrome as browser of choice..");
		} catch (FileNotFoundException e) {
			logger.error("Exception while setting Driver - - > > " + e.getMessage()+": StackTrace : "+e.getStackTrace());
		} catch (IOException e) {
			logger.error("Exception while setting Driver - - > > " + e.getMessage()+": Stacktrace : "+e.getStackTrace());
		}
		return driver;
	}
	
	private static WebDriver initChromeDriver() throws InterruptedException {
		logger.info("Launching Chrome Browser...,");
		System.setProperty("webdriver.chrome.driver", properties.getProperty("driverPath"));
        ChromeOptions chromeOption = new ChromeOptions();
        chromeOption.addArguments("--start-maximized");
        chromeOption.getLogLevel();
        WebDriver driver = new ChromeDriver(chromeOption);
        driver.manage().timeouts().implicitlyWait(Long.parseLong(properties.getProperty("DefaultExplictWait")), TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
		return driver;
	}

	private static WebDriver initFirefoxDriver() {
		//System.out.println("Launching Firefox browser..");
		logger.info("Launching Firefox Browser...,");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		return driver;
	}
	
	
	public static void launchURL() {
		String URL = properties.getProperty("katalon.url");
		try {
			driver.get(URL);
			logger.info("Launching Katalon Demo Application: "+URL);
		} catch (Exception e) {
			logger.error("Failed to Launch Katalon Demo Application : "+URL+"  : "+e.getMessage() +" : Stack Trace : "+e.getStackTrace());
			e.getStackTrace();
		}
	}


	public static WebElement FindElement(By by) throws InterruptedException {
        WebElement webElement = null;
        try{
            webElement = driver.findElement(by);

        }catch (TimeoutException e){
            e.printStackTrace();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        highlightElement(driver, webElement);
        return webElement;
    }


    public static List<WebElement> FindElements(By by){
        List<WebElement> element = null;
        element = driver.findElements(by);
        return element;
    }


    public static void click(By by) {
        waitForElementToBeClickable(by).click();
    }

    public static WebElement waitForElementToBeClickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public static String getElementText(WebElement element) {
        return waitForElementVisibility(element).getText();
    }

    public static WebElement waitForElementVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }


    public static void click(WebElement element) {
        waitForElementToBeClickable(element).click();
    }

    public static WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static Boolean waitUntilElementIsPresent(WebDriver driver, int timeoutInSeconds, WebElement element){
        Boolean AlertDisplayed = false;
        try{
            if(timeoutInSeconds > 0){
                wait = new WebDriverWait(driver,Duration.ofSeconds(timeoutInSeconds));
                wait.until(ExpectedConditions.visibilityOf(element));
            }
        }catch (ElementClickInterceptedException e){
            e.printStackTrace();
        }
        return AlertDisplayed;
    }


    public static WebElement waitForElement(WebDriver driver, int timeoutInSeconds, WebElement element) {
        wait = new WebDriverWait(driver,Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    public static Boolean waitUntilAlertIsPresent(int timeoutInSeconds){
        Boolean AlertDisplayed = false;
        try{
            if(timeoutInSeconds > 0){
                wait = new WebDriverWait(driver,Duration.ofSeconds(timeoutInSeconds));
                wait.until(ExpectedConditions.alertIsPresent());
            }
        }catch (ElementClickInterceptedException e){
            e.printStackTrace();
        }
       return AlertDisplayed;
    }


    public static Boolean IsAlertPresent(){
        try {
            driver.manage().timeouts().getImplicitWaitTimeout();
            driver.switchTo().alert();
            return true;
        }catch (NoAlertPresentException e){
            return false;
        }
    }


    public static void ClickOKOnAlert(){
        try {
            if (!IsAlertPresent()){
                System.out.println("Alert is not Present");
                return;
            }else{
                driver.manage().timeouts().getImplicitWaitTimeout();
                driver.switchTo().alert().accept();
            }
        }catch (NoAlertPresentException e){
            e.printStackTrace();
        }
    }

    public static void CancelAlert(){
        try {
            if (!IsAlertPresent()){
                System.out.println("Alert is not Present");
                return;
            }else{
                driver.manage().timeouts().getImplicitWaitTimeout();
                driver.switchTo().alert().dismiss();
            }
        }catch (NoAlertPresentException e){
            e.printStackTrace();
        }
    }


    public static void SendKeys(String strText){
        try {
            if (!IsAlertPresent()){
                System.out.println("Alert is not Present");
                return;
            }else{
                driver.manage().timeouts().getImplicitWaitTimeout();
                driver.switchTo().alert().sendKeys(strText);
            }
        }catch (NoAlertPresentException e){
            e.printStackTrace();
        }
    }


    public static String GetAlertText()
    {
        try {
            if (!IsAlertPresent())
                return " ";
            else
                return driver.switchTo().alert().getText();
        }catch (Exception e){
            e.printStackTrace();
        }
        return driver.switchTo().alert().getText();
    }


	public static Properties readPropertiesFile() throws IOException {
	    FileInputStream fis = null;
	    Properties prop = null;
	    try{
            String fileName = "src/main/resources/application.properties";
	        fis = new FileInputStream(fileName);
	        prop = new Properties();
	        prop.load(fis);

        }catch (FileNotFoundException ex){
	        ex.printStackTrace();
        }catch (IOException ioe){
	        ioe.printStackTrace();
        }finally {
	        fis.close();
        }
        return prop;
    }

    public static void takescreenshot()
    {
        int a=0;
        TakesScreenshot shot=(TakesScreenshot) driver;
        File f=shot.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(f, new File(properties.getProperty("screenShotPath")+a+".jpg"));
            a++;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public static void closeBrowser() {
        try{
            driver.close();
            logger.info("browser closed");
        }catch (Exception e){
            driver.quit();
        }
    }



    public static void getScreenShot(String name) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            String reportDirectory = new File(System.getProperty("user.dir")).getAbsolutePath() + properties.getProperty("screenShotPath");
            File destFile = new File((String) reportDirectory + name + "_" + formater.format(calendar.getTime()) + ".png");
            FileUtils.copyFile(scrFile, destFile);
            // This will help us to link the screen shot in testNG report
            Reporter.log("<a href='" + destFile.getAbsolutePath() + "'> <img src='" + destFile.getAbsolutePath() + "' height='100' width='100'/> </a>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String highlightElement(WebDriver driver, WebElement element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Execute javascript
        js.executeScript("arguments[0].style.border='4px solid yellow'", element);
        driver.manage().timeouts().pageLoadTimeout(Long.parseLong(properties.getProperty("DefaultTimeOut")), TimeUnit.SECONDS);
        js.executeScript("arguments[0].style.border=''", element);
        return null;
    }


	public static void main(String[] args) throws InterruptedException {
//        initialize();
//        launchURL();


    }

}
