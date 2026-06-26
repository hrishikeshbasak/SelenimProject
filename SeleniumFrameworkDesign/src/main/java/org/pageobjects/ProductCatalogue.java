package org.pageobjects;

import java.util.List;

import org.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductCatalogue extends AbstractComponent {

	WebDriver driver;

	public ProductCatalogue(WebDriver driver) {
		// initialization
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(css = ".mb-3")
	List<WebElement> products;
	
	@FindBy(css = ".ng-animating")
	WebElement spinner;

	By productsBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	By spinnerBy = By.cssSelector(".ng-animating");

	public List<WebElement> getProductList() {
		logInfo("Fetching products list from the catalogue");
		waitForElementToAppear(productsBy);
		logInfo("Total products found in catalogue: " + products.size());
		return products;
	}
	
	public WebElement getProductByName(String productName)
	{
		logInfo("Searching for product by name: " + productName);
		WebElement prod =	getProductList().stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		if (prod != null) {
			logInfo("Product found: " + productName);
		} else {
			logWarn("Product not found: " + productName);
		}
		return prod;
	}
	
	
	public void addProductToCart(String productName)
	{
		logInfo("Adding product to cart: " + productName);
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinnerBy);
		logInfo("Product added to cart successfully: " + productName);
	}
	
	
	
	
	

}
