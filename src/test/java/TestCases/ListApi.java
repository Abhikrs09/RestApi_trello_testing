package TestCases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import RestApiTesting.RestAssuredAssignment.Base.BasePage;
import RestApiTesting.RestAssuredAssignment.Utilities.EndPoints;
import RestApiTesting.RestAssuredAssignment.Utilities.ReadingPropertiesFile;
import RestApiTesting.RestAssuredAssignment.Utilities.Utils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class ListApi extends BasePage {

  private static String id = "";
  private static String idBoard = "";
  private static String name = "";
  public static Logger logger = LogManager.getLogger(ListApi.class);
  
  //function to create board with default list
  	@Test(priority = 0)
	public void createBoard() throws Exception {
		
		logger.info("======================= Board Post ======================");
		
		String bodyString = Utils.generateStringFromResource("/TestData/boardCreateData.json");
	
		Response serverResponse = restClient.post(EndPoints.createBoardEndPoint,bodyString)
											.then().assertThat().statusCode(200)
											.extract().response();
		serverResponse.prettyPeek();
		String rawRespo = serverResponse.asString();
		JsonPath js = new JsonPath(rawRespo);
		
		idBoard = js.get("id");	
		name = js.get("name");
		
		Assert.assertEquals(name,ReadingPropertiesFile.getProperty("Name"));
		
		logger.info("======================= End of Board Post ======================");
	}

  // How to create a list
  	
  @Test(priority = 1)
  public void createList() throws Exception {
	  
	  	logger.info("=======================Create List ======================");
	  
		String bodyString = Utils.generateStringFromResource("/TestData/listData.json");
		Response serverResponse = restClient.postList(EndPoints.createListEndPoint, bodyString, idBoard)
											.then()
											.log()
											.all()
											.assertThat()
											.statusCode(200)
											.extract()
											.response();
		
		String strResponse = serverResponse.asString();
		JsonPath js = new JsonPath(strResponse);
		id = js.get("id");
		Assert.assertEquals(serverResponse.statusCode(), 200);
		
		logger.info("=======================End Of Create List ======================");
  }

	// How to update a list[-ve Test Case]
  
	@Test(priority = 2)
	public void NegativeTestCase_updateList() throws Exception {
		
		logger.info("=======================NegativeTestCase_updateList ======================");
		
		String bodyString = Utils.generateStringFromResource("/TestData/negativeUpdateList.json");
		Response serverResponse = restClient.update(EndPoints.listEndPoint + id, bodyString)
											.then()
											.log()
											.all()
											.assertThat()
											.statusCode(400)
											.extract()
											.response();
		
		Assert.assertEquals(serverResponse.statusCode(), 400);
		
		logger.info("=======================End Of NegativeTestCase_updateList ======================");
	}
  
	@Test(priority = 3)
	public void updateList() throws Exception {
		
		logger.info("=======================Update List Method======================");
		
		String bodyString = Utils.generateStringFromResource("/TestData/updateList.json");	
		Response serverResponse = restClient.update(EndPoints.listEndPoint + id, bodyString)
											.then()
											.log()
											.all()
											.assertThat()
											.statusCode(200)
											.extract()
											.response();
		
		Assert.assertEquals(serverResponse.statusCode(), 200);
		
		logger.info("=======================End Of Update List ======================");
	}

	// function to get a list
	@Test(priority = 4)
	public void getList() throws Exception {
		
		logger.info("=======================Get List ======================");
		
		Response serverResponse = restClient.get(EndPoints.listEndPoint + id)
											.then()
											.log()
											.all()
											.assertThat()
											.statusCode(200)
											.extract()
											.response();
		
		
		Assert.assertEquals(serverResponse.statusCode(), 200);

		logger.info("=======================End Of Get List ======================");
	}

	 //How to delete the board
  	 @Test(priority = 5)
	 public void deleteBoard() throws Exception {
		 
		 logger.info("==========================Board Delete ============================");
		 
		 Response serverResponse = restClient.delete(EndPoints.createBoardEndPoint+idBoard)
											 .then()
											 .log()
											 .all()
											 .assertThat()
											 .statusCode(200)
											 .extract()
											 .response(); 
		 
		 serverResponse.prettyPeek();
		 
		 logger.info("==========================End Of Board Delete ============================");
	 }
}
