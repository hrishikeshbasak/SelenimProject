package org.AbstractComponents;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.pageobjects.CartPage;
import org.pageobjects.OrderPage;
import org.resources.ExtentReporterNG;

public class AbstractComponent {
	
	WebDriver driver;
	protected final Logger log = LogManager.getLogger(this.getClass());

	protected void logInfo(String message) {
		log.info(message);
		if (ExtentReporterNG.getTest() != null) {
			ExtentReporterNG.getTest().info(message);
		}
	}

	protected void logWarn(String message) {
		log.warn(message);
		if (ExtentReporterNG.getTest() != null) {
			ExtentReporterNG.getTest().warning(message);
		}
	}

	public AbstractComponent(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	@FindBy(css = "[routerlink*='cart']")
	WebElement cartHeader;
	
	@FindBy(css = "[routerlink*='myorders']")
	WebElement orderHeader;


	public void waitForElementToAppear(By findBy) {
		logInfo("Waiting for element to appear: " + findBy);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));

	}
	
	public void waitForWebElementToAppear(WebElement findBy) {
		logInfo("Waiting for web element to appear: " + findBy);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(findBy));

	}

	public void waitForElementToDisappear(By findBy) {
		logInfo("Waiting for element to disappear: " + findBy);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));

	}
	
	public CartPage goToCartPage()
	{
		logInfo("Navigating to Cart Page");
		cartHeader.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	
	public OrderPage goToOrdersPage()
	{
		logInfo("Navigating to Orders Page");
		orderHeader.click();
		OrderPage orderPage = new OrderPage(driver);
		return orderPage;
	}
	public void waitForElementToDisappear(WebElement ele)
	{
		logInfo("Waiting for web element to disappear: " + ele);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOf(ele));

	}

}
