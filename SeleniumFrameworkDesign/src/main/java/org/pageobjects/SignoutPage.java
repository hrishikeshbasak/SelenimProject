package org.pageobjects;

import org.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignoutPage extends AbstractComponent {
	WebDriver driver;
	
	@FindBy(css = ".fa-sign-out")
	WebElement signoutButton;

	public SignoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void signOut() {
		logInfo("Clicking on Sign Out button");
		signoutButton.click();
		logInfo("Sign Out button clicked");
	}
}
