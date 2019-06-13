package com.tfg;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

public class ContextManager {
	
	private static ContextManager cm = null;
	private Properties prop;
	private HttpClient httpclient;
	
	private ContextManager() {
		readProperties();
		createHttpClient();
	}
	
	public static ContextManager getInstance() {
		if(cm == null) {
			cm = new ContextManager();
		}
		return cm;
	}

	/**
	 * load a properties file from class path, inside static method --> prop.load
	 */
	private void readProperties() {
		try (InputStream input = ContextManager.class.getClassLoader().getResourceAsStream("azure.properties")) {
			prop = new Properties();
			if (input == null) {
				System.out.println("Sorry, unable to find azure.properties");
				return;
			}
			prop.load(input);
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * create http client for http requests
	 */
	private void createHttpClient() {
		httpclient = HttpClients.createDefault();
	}
	
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	public HttpClient getHttpClient() {
		return httpclient;
	}
	
	public void closeHttpClient() throws IOException {
		((Closeable) httpclient).close();
	}

}
