package katalonDemo.steps;

import com.katalonDemo.pages.BasePage;
import com.katalonDemo.pages.CartPage;
import com.katalonDemo.pages.ShopPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.core.TestUtil;

import java.io.IOException;

public class StepDefinition extends BasePage {

    public StepDefinition(){
        super();
    }

    ShopPage shop = new ShopPage();
    CartPage cart = new CartPage();
	
	

	@Given("user opens the browser")
	public void user_opens_the_browser() throws IOException, InterruptedException {
        TestUtil.initialize();
        TestUtil.launchURL();
        String PageTitle = shop.getLandingPage();
        Assert.assertEquals("Shop", PageTitle);
	}

	@Given("I add four random items to my cart")
	public void i_add_four_random_items_to_my_cart() throws InterruptedException {
	    shop.addFourItemToCart();
	}

	@When("I view my cart")
	public void i_view_my_cart() throws InterruptedException {
	    cart.clickCart();
        cart.cartPageTitle();
	}

	@Then("I find total four items listed in my cart")
	public void i_find_total_four_items_listed_in_my_cart() throws InterruptedException {
        cart.countProducts();
        cart.getListOfProduct();
	}

	@When("I serach for lowest price item")
	public void i_serach_for_lowest_price_item() {
	    cart.getListOfProductPrice();

	}

	@When("I am able to remove the lowest price item from my cart")
	public void i_am_able_to_remove_the_lowest_price_item_from_my_cart() throws InterruptedException {
	   cart.RemoveItemFromCart();
	}

	@Then("I am able to verify three items in my cart")
	public void i_am_able_to_verify_three_items_in_my_cart() throws InterruptedException {
        cart.countProducts();
        cart.getListOfProduct();
	}


	public static void main(String[] args) throws IOException, InterruptedException {
		StepDefinition sd = new StepDefinition();
		sd.user_opens_the_browser();
		sd.i_add_four_random_items_to_my_cart();
		sd.i_view_my_cart();
		sd.i_find_total_four_items_listed_in_my_cart();
		sd.i_serach_for_lowest_price_item();
		sd.i_am_able_to_remove_the_lowest_price_item_from_my_cart();
		sd.i_am_able_to_verify_three_items_in_my_cart();
	}
}
