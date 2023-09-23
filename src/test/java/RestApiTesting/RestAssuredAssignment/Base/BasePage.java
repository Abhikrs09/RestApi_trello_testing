package RestApiTesting.RestAssuredAssignment.Base;

import java.lang.reflect.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import RestApiTesting.RestAssuredAssignment.Utilities.ReadingPropertiesFile;
import RestApiTesting.RestAssuredAssignment.Utilities.RestClientWrapper;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import RestApiTesting.RestAssuredAssignment.Utilities.ExtentManager;

public class BasePage {
	public static Logger logger = LogManager.getLogger(BasePage.class);
	public static RestClientWrapper restClient;
	protected static ReadingPropertiesFile readingPropertiesFile = new ReadingPropertiesFile();
	public static ExtentReports extent;
	public static String BaseUrl;
	public static ExtentTest test;
	protected static RequestSpecification httpRequest;
	
	
	@BeforeMethod
	public void startTest(Method method) {
		
		extent = ExtentManager.getInstance("Reports/ExtentReports.html");
		BaseUrl = ReadingPropertiesFile.getProperty("baseUrl");
		httpRequest = RestAssured.given();
		restClient = new RestClientWrapper(BaseUrl, httpRequest);
		test = extent.startTest(method.getName());
	}
	
	@AfterMethod
	public void status(ITestResult result) {
		if (result.getStatus()==ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Test case got failed");
			}
		else if(result.getStatus()==ITestResult.SKIP)
			test.log(LogStatus.SKIP, result.getThrowable());
		else 
			test.log(LogStatus.PASS, "Test case got passed");
		    extent.flush();
	}
}