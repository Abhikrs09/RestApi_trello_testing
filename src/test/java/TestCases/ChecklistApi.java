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

public class ChecklistApi extends BasePage {
	
	public static Logger logger = LogManager.getLogger(ChecklistApi.class);
	private static String idBoard = "";
	private static String name = "";
	private static String idList = "";
	private static String idCard ="";
	private static String idChecklist ="";
	
	@Test(priority = 0)
	public void createBoard() throws Exception {
		
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
		idBoard = js.get("id");
		name = js.get("name");
		Assert.assertEquals(name,ReadingPropertiesFile.getProperty("Name"));
	}

    // How to create a list
	
	@Test(priority = 1)
	public void createListForCard() throws Exception {

		String bodyString = Utils.generateStringFromResource("/TestData/cardData.json");
		
		Response serverResponse = restClient.postList(EndPoints.createListEndPoint, bodyString, idBoard)
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
		
		String listIdBoard = js.get("idBoard");
		idList = js.get("id");

		String listName = js.get("name");
		
		Assert.assertEquals(listIdBoard, idBoard);
		
		Assert.assertEquals(listName, ReadingPropertiesFile.getProperty("Name"));	
		Assert.assertEquals(serverResponse.statusCode(), 200);

	}

	// How to create a card
	@Test(priority = 2)
	public void createCard() throws Exception {
		
		logger.info("====================Create Card =================================");
		
		String bodyString = Utils.generateStringFromResource("/TestData/cardData.json");
		Response serverResponse = restClient.postCard(EndPoints.createCardEndPoint, bodyString, idList)
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
		
		idCard = js.get("id");
		
		String cardIdList = js.get("idList");
		String cardIdBoard = js.get("idBoard");
		
		Assert.assertEquals(cardIdList, idList);
		Assert.assertEquals(cardIdBoard, idBoard);
		Assert.assertEquals(serverResponse.statusCode(), 200);

		logger.info("====================End Of Create Card =================================");
	}
	
	@Test(priority = 3)
	  public void createChecklist() throws Exception {
		  
		logger.info("====================Create Checklist=================================");
		
			String bodyString = Utils.generateStringFromResource("/TestData/checklistData.json");
			
			Response serverResponse = restClient.postcheckList(EndPoints.checkListEndPoint, bodyString, idCard)
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
			idChecklist = js.get("id");

			Assert.assertEquals(serverResponse.statusCode(), 200);
			logger.info("====================End of Create Checklist=================================");
	  }
	
	@Test(priority = 4)
	public void updateChecklistist() throws Exception {
		
		logger.info("=======================Update CheckList ======================");
		
		String bodyString = Utils.generateStringFromResource("/TestData/updateChecklist.json");	
		Response serverResponse = restClient.update(EndPoints.getCheckListEndPoint + idChecklist, bodyString)
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
	
	@Test(priority = 5)
	public void getChecklist() throws Exception {
		
		logger.info("=======================Get CheckList ======================");
		
		Response serverResponse = restClient.get(EndPoints.getCheckListEndPoint + idChecklist)
											.then()
											.log()
											.all()
											.assertThat()
											.statusCode(200)
											.extract()
											.response();
		
		Assert.assertEquals(serverResponse.statusCode(), 200);

		logger.info("=======================End Of Get CheckList ======================");
	}
	
	
	@Test(priority = 6)
	public void deleteCard() throws Exception {
			
		Response serverResponse = restClient.delete(EndPoints.cardEndPoint + idCard)
											.then()
											.log()
											.all()
											.assertThat()
											.statusCode(200)
											.extract()
											.response();	
		
		serverResponse.prettyPeek();		
		Assert.assertEquals(serverResponse.statusCode(), 200);
	}
	
	@Test(priority = 7)
	 public void deleteBoard() throws Exception {
		 
		 Response serverResponse = restClient.delete(EndPoints.createBoardEndPoint+idBoard)
											 .then()
											 .log()
											 .all()
											 .assertThat()
											 .statusCode(200)
											 .extract()
											 .response();
		 
		 serverResponse.prettyPeek();	 	 
	 }
}
