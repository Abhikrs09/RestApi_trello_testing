package RestApiTesting.RestAssuredAssignment.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadingPropertiesFile {

	public static Properties prop = new Properties();
    public static File file = null;
    public static FileInputStream fis = null;
	
	
	static {
			
			try {
				
				File file = new File(System.getProperty("user.dir")+"\\Resources\\config.properties");
				fis = new FileInputStream(file);
				
			}catch (FileNotFoundException e){
				
				e.printStackTrace();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				
				prop.load(fis);
				
			}catch(IOException e) {
				
				e.printStackTrace();
			}
		}
	
	 public static String getProperty(String key) {
	        return prop.getProperty(key);
	    }

}
