package org.tests;

import org.testng.annotations.Test;
import java.io.IOException;
import java.util.List;

import org.TestComponents.BaseTest;
import org.TestComponents.Retry;
import org.openqa.selenium.WebElement;
import org.pageobjects.CartPage;
import org.pageobjects.ProductCatalogue;
import org.testng.Assert;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups = { "ErrorHandling" }, retryAnalyzer = Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException {
		landingPage.loginApplication("basakhrishi@gmail.com", "Testing@1234");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}

	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException {

		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("basakhrishi@gmail.com", "Testing@123");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
	}
}
