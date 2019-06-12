package com.tfg;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ContextManager {
	
	private static ContextManager cm = null;
	private Properties prop;
	
	private ContextManager() {
		readProperties();
	}
	
	public static ContextManager getInstance() {
		if(cm == null) {
			cm = new ContextManager();
		}
		return cm;
	}

	private void readProperties() {
		try (InputStream input = ContextManager.class.getClassLoader().getResourceAsStream("azure.properties")) {

			prop = new Properties();

			if (input == null) {
				System.out.println("Sorry, unable to find azure.properties");
				return;
			}
			//load a properties file from class path, inside static method
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public String getProperty(String key) {
		return prop.getProperty(key);
	}	

}
