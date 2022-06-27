package com.katalonDemo.pages;

import com.core.TestUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ShopPage extends BasePage {

    final static Logger logger = Logger.getLogger(ShopPage.class);

    @FindBy(xpath="//li/a[@href='https://cms.demo.katalon.com/']")
    By ShopMenu;

    @FindBy(xpath="//h1[contains(text(),'Shop')]")
    WebElement ShopPageTitle;

    @FindBy(xpath = "//*[contains(@data-product_sku, 'POSTER-FLYING-NINJA') and contains(text(), 'Add to cart')]")
    WebElement Product_FlyingNinja;

    @FindBy(xpath = "//*[contains(@data-product_sku, 'T-SHIRT-HAPPY-NINJA') and contains(text(), 'Add to cart')]")
    WebElement Product_HappyNinja;

    @FindBy(xpath = "//*[contains(@data-product_sku, 'HOODIE-HAPPY-NINJA') and contains(text(), 'Add to cart')]")
    WebElement Product_HoodieHappyNinja;

    @FindBy(xpath = "//*[contains(@data-product_sku, 'T-SHIRT-NINJA-SILHOUETTE') and contains(text(), 'Add to cart')]")
    WebElement Product_NinjaSilhouette;

    @FindBy(xpath="//*[@id='shopify-section-collection-template']/div/div/div/a/img")
    List<WebElement> products;



    public ShopPage(WebDriver driver, WebDriverWait wait){
        super();
    }

    public ShopPage() {

    }


    public void clickOnNavigationMenu() throws InterruptedException {
        try{
            TestUtil.waitForElementToBeClickable(ShopMenu);
            TestUtil.click(ShopMenu);
            TestUtil.highlightElement(driver, ShopPageTitle);
            String menu = TestUtil.getElementText(ShopPageTitle);
            logger.info("clicked on:-"+menu+" navigation menu");
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    public String getLandingPage() throws InterruptedException {
        String PageTitle = null;
        PageTitle = TestUtil.FindElement(By.xpath("//h1[contains(text(),'Shop')]")).getText();
        logger.info("default page:-"+PageTitle);
        return PageTitle;
    }

    public void addItemToCart(String item) throws InterruptedException {
        driver.findElement(By.xpath("//*[contains(@data-product_sku, '"+item+"') and contains(text(), 'Add to cart')]")).click();
        logger.info(item+" has been selected");
    }


    public void addFourItemToCart() throws InterruptedException {
        TestUtil.scrollToElement(Product_FlyingNinja);
        TestUtil.waitForElementToBeClickable(Product_FlyingNinja);
        TestUtil.click(Product_FlyingNinja);
        TestUtil.scrollToElement(Product_HappyNinja);
        TestUtil.waitForElementToBeClickable(Product_HappyNinja);
        TestUtil.click(Product_HappyNinja);
        TestUtil.scrollToElement(Product_HoodieHappyNinja);
        TestUtil.waitForElementToBeClickable(Product_HoodieHappyNinja);
        TestUtil.click(Product_HoodieHappyNinja);
        TestUtil.scrollToElement(Product_NinjaSilhouette);
        TestUtil.waitForElementToBeClickable(Product_NinjaSilhouette);
        TestUtil.click(Product_NinjaSilhouette);
        logger.info("Four Items have been selected");
    }

    public List<WebElement> selectProduct(){
        List<WebElement> element = products;
        return element;
    }



    public static void main(String[] args) throws InterruptedException {

    }
}
