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


public class CardsApi extends BasePage {
  private static String id = "";
  private static String name = "";
  private static String idBoard = "";
  private static String idList = "";
  public static Logger logger = LogManager.getLogger(CardsApi.class);

  
    // How to create board with default list
  
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
		id = js.get("id");		
		String cardIdList = js.get("idList");
		String cardIdBoard = js.get("idBoard");		
		Assert.assertEquals(cardIdList, idList);		
		Assert.assertEquals(cardIdBoard, idBoard);		
		Assert.assertEquals(serverResponse.statusCode(), 200);	
	}

	// How to update a card
	
	@Test(priority = 3)
	public void updateCard() throws Exception {
		
		String bodyString = Utils.generateStringFromResource("/TestData/updateCard.json");		
		Response serverResponse = restClient.update(EndPoints.cardEndPoint + id, bodyString)
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
		
		String desc = js.get("desc");
		String jsName = js.get("name");	
		String bodyDesc = bodyJs.get("desc");
		String bodyName = bodyJs.get("name");
		
		Assert.assertEquals(desc, bodyDesc);		
		Assert.assertEquals(jsName, bodyName);	
		Assert.assertEquals(serverResponse.statusCode(), 200);
	}
	

	// How to get a card
	@Test(priority = 4)
	public void getCard() throws Exception {
			
		Response serverResponse = restClient.get(EndPoints.cardEndPoint + id)
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
	

	// How to delete a card
	@Test(priority = 5)
	public void deleteCard() throws Exception {
				
		Response serverResponse = restClient.delete(EndPoints.cardEndPoint + id)
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
	

	// How to delete a board
	
	@Test(priority = 6)
	public void deleteBoard() throws Exception {	

		Response serverResponse = restClient.delete(EndPoints.createBoardEndPoint + idBoard)
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