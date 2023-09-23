package RestApiTesting.RestAssuredAssignment.Utilities;

import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClientWrapper {

	public String resource;
	public String baseUrl;
	private RequestSpecification request;
	private Response restResponse;
	
	public RestClientWrapper(String baseUrl,RequestSpecification request) {
		this.request = request;
		this.request.baseUri(baseUrl);	
	}
	
	public Response get(String resource)throws Exception{
		restResponse = request
							 .queryParam("key", ReadingPropertiesFile.getProperty("Key"))
							 .queryParam("token",ReadingPropertiesFile.getProperty("Token"))
							 .header("Accept","application/json")
							 .when()
							 .get(resource);
							
		return restResponse;
	}
	//create checklist post
		public Response postcheckList(String resource,String bodyString, String idCard)
	    {    
	       restResponse = request.
	                queryParam("key",ReadingPropertiesFile.getProperty("Key")).
	                queryParam("token",ReadingPropertiesFile.getProperty("Token")).
	                queryParam("idCard",idCard).body(bodyString).
	                header("Accept","application/json")
	                .when()
	                .post(resource);         
	        return restResponse;            
	    }
	
	public Response post(String resource, String bodyString)throws Exception{
		restResponse = request.
							   queryParam("key", ReadingPropertiesFile.getProperty("Key"))
							  .queryParam("token",ReadingPropertiesFile.getProperty("Token"))
							  .queryParam("name",ReadingPropertiesFile.getProperty("Name"))
							  .header("Accept","application/json")
							  .body(bodyString)
							  .when()
							  .post(resource);
		
		return restResponse;
	}
	
	public Response postWithIdBoard(String resource, String bodyString,String Idbaord)throws Exception{
		restResponse = request.
							   queryParam("key", ReadingPropertiesFile.getProperty("Key"))
							  .queryParam("token",ReadingPropertiesFile.getProperty("Token"))
							  .queryParam("name",ReadingPropertiesFile.getProperty("Name"))
							  .queryParam("color",ReadingPropertiesFile.getProperty("Color"))
							  .queryParam("idBoard",Idbaord)
							  .body(bodyString)
							  .when()
							  .post(resource);
		
		return restResponse;
	}

	public Response postHeaders(String resource, String bodyString, Header header)throws Exception{
		restResponse = request.header(header)
				.body(bodyString)
				.when()
				.get(resource);
		
		return restResponse;
	}
	
	public Response postWithHeaders(String resource) {
		
		restResponse = request.
							   queryParam("key", ReadingPropertiesFile.getProperty("Key"))
							  .queryParam("token",ReadingPropertiesFile.getProperty("Token"))
							  .header("Accept","application/json")
							  .when()
							  .post(resource);        
		
		return restResponse;
	}
	
	public Response postAuthorization(String resource, String bodyString, String auth)throws Exception{
		restResponse = request.header("Authorization",auth)
							  .body(bodyString)
							  .when()
							  .get(resource);
		
		return restResponse;
	}
	
	public Response postList(String resource,String bodyString, String idBoard)
    {    
       restResponse = request.
                queryParam("key",ReadingPropertiesFile.getProperty("Key")).
                queryParam("token",ReadingPropertiesFile.getProperty("Token")).
                queryParam("name",ReadingPropertiesFile.getProperty("Name")).
                queryParam("idBoard",idBoard)
                .body(bodyString)
                .when()
                .post(resource);
       
        return restResponse;            
    }
   
   //create card with idList
   public Response postCard(String resource,String bodyString, String idList)
    {    
       restResponse = request.
                queryParam("key",ReadingPropertiesFile.getProperty("Key")).
                queryParam("token",ReadingPropertiesFile.getProperty("Token")).
                queryParam("name",ReadingPropertiesFile.getProperty("Name")).
                queryParam("idList",idList)
                .body(bodyString)
                .when()
                .post(resource);
       
        return restResponse;            
    }
	
	public Response update(String resource, String bodyString) {
		
		restResponse = request.log().all().
							 header("Content-Type","application/json")
						    .queryParam("key", ReadingPropertiesFile.getProperty("Key"))
						    .queryParam("token",ReadingPropertiesFile.getProperty("Token"))
						    .body(bodyString)
						    .when()
						    .put(resource);
		
		return restResponse;
	}
	
	public Response delete(String resource) {
			
			restResponse = request.
							     queryParam("key", ReadingPropertiesFile.getProperty("Key"))
							    .queryParam("token",ReadingPropertiesFile.getProperty("Token"))
							    .when()
							    .delete(resource);
			
			return restResponse;
		}
	
}
