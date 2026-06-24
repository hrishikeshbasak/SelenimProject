package rahulshettyacademy.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

	WebDriver driver;

	public LandingPage(WebDriver driver) {
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	// WebElement userEmails = driver.findElement(By.id("userEmail"));
	// PageFactory

	@FindBy(id = "userEmail")
	WebElement userEmail;

	@FindBy(id = "userPassword")
	WebElement passwordEle;

	@FindBy(id = "login")
	WebElement submit;
	@FindBy(css = "[class*='flyInOut']")
	WebElement errorMessage;

	public ProductCatalogue loginApplication(String email, String password) {
		log.info("Logging into application with username: " + email);
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}

	public String getErrorMessage() {
		log.info("Retrieving login error message");
		waitForWebElementToAppear(errorMessage);
		String errorMsg = errorMessage.getText();
		log.info("Error message retrieved: " + errorMsg);
		return errorMsg;
	}

	public void goTo() {
		log.info("Navigating to URL: https://rahulshettyacademy.com/client");
		driver.get("https://rahulshettyacademy.com/client");
	}

}
