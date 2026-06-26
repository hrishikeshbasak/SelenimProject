package org.pageobjects;

import java.util.List;

import org.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class CartPage extends AbstractComponent {
	WebDriver driver;

	@FindBy(css = ".totalRow button")
	WebElement checkoutEle;

	@FindBy(css = ".cartSection h3")
	private List<WebElement> cartProducts;

	By cartProductsBy = By.cssSelector(".cartSection h3");

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	public Boolean VerifyProductDisplay(String productName) {
		logInfo("Verifying product display in cart for product: " + productName);
		waitForElementToAppear(cartProductsBy);
		Boolean match = cartProducts.stream()
				.anyMatch(product -> product.getText().trim().equalsIgnoreCase(productName));
		logInfo("Product display verification result: " + match);
		return match;

	}

	public CheckoutPage goToCheckout() {
		logInfo("Navigating to Checkout Page");
		checkoutEle.click();
		return new CheckoutPage(driver);
	}

}
