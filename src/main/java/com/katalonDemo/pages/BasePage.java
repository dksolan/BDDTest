package com.katalonDemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.core.TestUtil;

import io.cucumber.java.After;
import io.cucumber.java.Before;


import org.openqa.selenium.support.ui.WebDriverWait;


public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
	public int TimeoutValue = 50;



    public BasePage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
        PageFactory.initElements(driver, this);
	}

    public BasePage() {
    }


    @Before
	public static void BeforeTestRun() throws InterruptedException {
		System.out.println("Test execution Started");
		TestUtil.initialize();

	}

	
	
	@After
	public static void AfterTest() throws InterruptedException{
		TestUtil.closeBrowser();
        System.out.println("Test execution Completed");
	}
        
        

	
}
