package com.katalonDemo.pages;

import com.core.TestUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.util.*;

public class CartPage {

    final static Logger logger = Logger.getLogger(CartPage.class);
	protected WebDriver driver;
	protected WebDriverWait wait;
	
	@FindBy(xpath = "//a[contains(text(),'Cart')]")
	By cartMenu;

    @FindBy(xpath = "//h1[contains(text(),'Cart')]")
    WebElement cartPageTitle;

    @FindBy(xpath = "//*[@id=\"post-8\"]/div/div/form/table/tbody")
    By CartItems;

    public CartPage() {
    }

	// Initializing the Page Objects:
		public CartPage(WebDriver driver, WebDriverWait wait) {
		    super();
		}



    public void clickCart() {
            TestUtil.waitForElementToBeClickable(cartMenu);
			TestUtil.click(cartMenu);
		}

		public String cartPageTitle(){
		    String title = TestUtil.getElementText(cartPageTitle);
            logger.info("clicked on:- "+title);
		    return title;
        }

    public int countProducts() throws InterruptedException {
        WebElement table = TestUtil.FindElement(CartItems);
        int numberOfRows = table.findElements(By.tagName("tr")).size();

        return numberOfRows;
    }


    public String getListOfProduct(){
		String product = null;
        List<WebElement> listOfItems = TestUtil.FindElements(By.xpath("//*[@id=\"post-8\"]/div/div/form/table/tbody/tr/td[3]"));
        for(WebElement item : listOfItems){
            TestUtil.waitForElement(driver,120, item);
            product = item.getText();
        }
        return product;
    }

    public BigDecimal getListOfProductPrice(){
		BigDecimal productPrice = null;
        List<WebElement> listOfItemsPrice = TestUtil.FindElements(By.xpath("//*[@id=\"post-8\"]/div/div/form/table/tbody/tr/td[4]"));
        for(WebElement itemPrice : listOfItemsPrice){
            TestUtil.waitForElement(driver,120, itemPrice);
            productPrice = new BigDecimal(itemPrice.getText());
        }
        return productPrice;
    }


    public void RemoveItemFromCart() throws InterruptedException {
		HashMap<BigDecimal, String> map = new HashMap<>();
		String itemName = getListOfProduct();
		BigDecimal itemPrice = getListOfProductPrice();

        map.put(itemPrice, itemName);
        TreeMap<BigDecimal, String> treeMap = new TreeMap<>();
        treeMap.putAll(map);
        Set<BigDecimal> priceKey = treeMap.keySet();
        ArrayList<BigDecimal> arrayListValue = new ArrayList<>(priceKey);
        Collections.sort(arrayListValue);

        BigDecimal lowestPrice = arrayListValue.get(0);

        if(treeMap.firstEntry().getValue().matches(itemName)){
            driver.findElement(By.xpath("//*[contains(@data-product_sku, '"+itemName+"') and contains(text(), '×')]")).click();
        }

    }

}
