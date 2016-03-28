package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

	private final static String propertyPath = "/config.properties";
	private static Properties prop ;
	 static  {    
	        prop =  new  Properties();    
	        InputStream in = Object.class.getResourceAsStream(propertyPath);
	         try  {   
	        	 prop.load(in);
	        }  catch  (IOException e) {    
	            e.printStackTrace();    
	        }    
	    } 
	 
	public static String getValue(String paramName){
		if(paramName == null || paramName.length() == 0 )
			return null;
		String value = (String)prop.get(paramName);
		return value;
	}
}
