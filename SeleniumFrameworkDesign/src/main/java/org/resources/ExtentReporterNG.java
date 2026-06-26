package org.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

	private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	private static ExtentReports extent;

	public static ExtentTest getTest() {
		return extentTest.get();
	}

	public static void setTest(ExtentTest test) {
		extentTest.set(test);
	}

	public static void removeTest() {
		extentTest.remove();
	}

	public static ExtentTest createTest(String testName) {
		ExtentTest test = getReportObject().createTest(testName);
		setTest(test);
		return test;
	}
	
	public static ExtentReports getReportObject()
	{
		if (extent == null) {
			String path = System.getProperty("user.dir") + "//reports//index.html";
			ExtentSparkReporter reporter = new ExtentSparkReporter(path);
			reporter.config().setReportName("Web Automation Results");
			reporter.config().setDocumentTitle("Test Results");
			
			extent = new ExtentReports();
			extent.attachReporter(reporter);
			extent.setSystemInfo("Tester", "Hrishikesh Basak");
		}
		return extent;
	}
}
