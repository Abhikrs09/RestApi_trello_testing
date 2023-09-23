package RestApiTesting.RestAssuredAssignment.Utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners implements ITestListener {
	
	public void  onTestStart(ITestResult result)
	{
		System.out.println("Before test start");
	}
	
	public void  onTestSuccess(ITestResult result)
	{
		System.out.println("Before test success");
	}
	
	public void  onTestFailure(ITestResult result)
	{
		System.out.println("Before test failure");
	}
	
	public void  onTestSkipped(ITestResult result)
	{
		System.out.println("On test skip");
	}
	
	public void  onStart(ITestContext context)
	{
		System.out.println("Before start");
	}
	
	public void  onTestFinish(ITestResult result)
	{
		System.out.println("On finish");
	}
	

		
}
