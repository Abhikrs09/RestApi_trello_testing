package RestApiTesting.RestAssuredAssignment.Utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
	
	public static String generateStringFromResource(String relativePath) throws IOException{
		relativePath = System.getProperty("user.dir") + "/src/test/java/" +relativePath;
		return new String(Files.readAllBytes(Paths.get(relativePath)));
	}
}



