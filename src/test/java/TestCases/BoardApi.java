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


public class BoardApi extends BasePage{

	public static String id;
	public static String name;
	public static String desc;
	public static boolean boardStatus;
	public static Logger logger = LogManager.getLogger(BoardApi.class);
	
	@Test(priority = 1)
	public void createBoard() throws Exception {
		
		logger.info("****************** Board Post ******************");
		
		String bodyString = Utils.generateStringFromResource("/TestData/boardCreateData.json");		
		Response serverResponse = restClient.post(EndPoints.createBoardEndPoint,bodyString)
											.then()
											.assertThat()
											.statusCode(200)
											.extract()
											.response();
		
		serverResponse.prettyPeek();
		String rawRespo = serverResponse.asString();
		JsonPath js = new JsonPath(rawRespo);
		
		id = js.get("id");
		name = js.get("name");
		Assert.assertEquals(name,ReadingPropertiesFile.getProperty("Name"));
		
		logger.info("****************** End of Board Post ******************");
	}
	
	@Test(priority = 2)
	public void getBoard() throws Exception{
		
		logger.info("******************Board Get ******************");
		System.out.println("******************Board Get ******************");
		
		Response serverResponse = restClient.get(EndPoints.createBoardEndPoint+id)
										    .then()
										    .assertThat()
										    .statusCode(200)
										    .extract()
										    .response();	
		
		serverResponse.prettyPeek();		
		String rawRespo = serverResponse.asString();
		JsonPath js = new JsonPath(rawRespo);
		
		String getId = js.get("id");
		Assert.assertEquals(id, getId);
		String getName = js.get("name");
		Assert.assertEquals(name, getName);
		
		System.out.println("******************Get Method******************");
		
		logger.info("******************End Of Board Get ******************");
	}
	
	 @Test(priority = 3)
	 public void updateBoard() throws Exception
	    {
		 	logger.info("******************Board Update ******************");
		 
		 	String bodyString = Utils.generateStringFromResource("/TestData/updateBoard.json");    
	        Response serverResponse = restClient.update(EndPoints.createBoardEndPoint+id, bodyString)
	                							.then()
	                							.log()
	                							.all()
	                							.assertThat()
	                							.statusCode(200)
	                							.extract()
	                							.response();
	        
	        serverResponse.prettyPeek();
	        String strResponse = serverResponse.asString();		
	        JsonPath js = new JsonPath(strResponse);
	        JsonPath bodyJs = new JsonPath(bodyString);
			
	        desc = js.getString("desc");
	        String bodyDesc = bodyJs.get("desc");
	        
	        Assert.assertEquals(desc, bodyDesc);	
			boardStatus = js.get("closed");
			boolean bodyBoardStatus = bodyJs.get("closed");
			
			Assert.assertEquals(boardStatus, bodyBoardStatus);
	              
	        logger.info("******************End Of Board Update ******************");
	    }
	 
	 @Test(priority = 4)
	 public void deleteBoard() throws Exception {
		 
		 logger.info("******************Delete Board ******************");
		 
		 Response serverResponse = restClient.delete(EndPoints.createBoardEndPoint+id)
											 .then().log().all().assertThat().statusCode(200)
											 .extract().response();
		 serverResponse.prettyPeek();
		 logger.info("******************End Of Delete Board ******************");
	 }
}
