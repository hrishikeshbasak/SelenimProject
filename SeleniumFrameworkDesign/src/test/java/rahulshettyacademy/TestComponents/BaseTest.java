package rahulshettyacademy.TestComponents;

import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.resources.ExtentReporterNG;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;
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

	public WebDriver initializeDriver() throws IOException

	{
		logInfo("Initializing WebDriver configuration...");
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "//src//main//java//rahulshettyacademy//resources//GlobalData.properties");
		prop.load(fis);

		String browserName = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");
		browserName = browserName.toLowerCase();
		logInfo("Browser selected: " + browserName);

		if (browserName.contains("chrome")) {
			logInfo("Setting up ChromeDriver with options");
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-gpu");
			options.addArguments("--window-size=1440,900");
			options.addArguments("--no-first-run");
			options.addArguments("--no-default-browser-check");
			options.addArguments("--user-data-dir=" + System.getProperty("java.io.tmpdir") + File.separator
					+ "chrome-profile-" + UUID.randomUUID());
			if (browserName.contains("headless")) {
				logInfo("Running ChromeDriver in headless mode");
				options.addArguments("--headless=new");
			}
			driver = new ChromeDriver(options);

		} else if (browserName.equalsIgnoreCase("firefox")) {
			logInfo("Setting up FirefoxDriver");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			logInfo("Setting up EdgeDriver");
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		if (!browserName.contains("headless")) {
			driver.manage().window().maximize();
			logInfo("Browser window maximized");
		}
		logInfo("WebDriver initialization complete.");
		return driver;

	}

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		logInfo("Reading JSON data from file: " + filePath);
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		logInfo("JSON data successfully parsed to Map");
		return data;

	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		logInfo("Capturing screenshot for: " + testCaseName);
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		logInfo("Screenshot captured and saved to: " + file.getAbsolutePath());
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";

	}

	public LandingPage launchApplication() throws IOException {
		logInfo("Launching the application under test");
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;

	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication(java.lang.reflect.Method method) throws IOException {
		ExtentReporterNG.createTest(method.getName());
		return launchApplication();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			logInfo("Quitting WebDriver instance");
			driver.quit();
		}
		ExtentReporterNG.removeTest();
	}
}
