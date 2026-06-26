package org.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.resources.ExtentReporterNG;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Listeners extends BaseTest implements ITestListener{
	ExtentTest test;
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>() {
		@Override
		public ExtentTest get() {
			return ExtentReporterNG.getTest();
		}
		@Override
		public void set(ExtentTest value) {
			ExtentReporterNG.setTest(value);
		}
		@Override
		public void remove() {
			ExtentReporterNG.removeTest();
		}
	};

	@Override
	public void onTestStart(ITestResult result) {
		log.info("=========================================================================");
		log.info("STARTING TEST CASE: " + result.getMethod().getMethodName());
		log.info("=========================================================================");
		if (ExtentReporterNG.getTest() == null) {
			test = ExtentReporterNG.createTest(result.getMethod().getMethodName());
		} else {
			test = ExtentReporterNG.getTest();
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		log.info("=========================================================================");
		log.info("TEST CASE PASSED: " + result.getMethod().getMethodName());
		log.info("=========================================================================");
		extentTest.get().log(Status.PASS, "Test Passed");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		log.error("=========================================================================");
		log.error("TEST CASE FAILED: " + result.getMethod().getMethodName());
		log.error("Exception Message: " + result.getThrowable().getMessage());
		log.error("=========================================================================");
		extentTest.get().fail(result.getThrowable());//
		
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());
			
		} catch (Exception e1) {
			log.error("Failed to retrieve driver instance dynamically", e1);
		}
		
		
		
		String filePath = null;
		try {
			
			filePath = getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			log.error("Failed to capture screenshot on test failure", e);
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		
		
		//Screenshot, Attach to report
		
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		log.warn("=========================================================================");
		log.warn("TEST CASE SKIPPED: " + result.getMethod().getMethodName());
		log.warn("=========================================================================");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		log.info("======================== TEST SUITE STARTED ========================");
	}

	@Override
	public void onFinish(ITestContext context) {
		log.info("======================== TEST SUITE FINISHED. Flushing extent report ========================");
		ExtentReporterNG.getReportObject().flush();
		
	}

}
