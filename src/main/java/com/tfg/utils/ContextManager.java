package com.tfg.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpStatus;

import com.tfg.exceptions.ExceptionFactory;
import com.tfg.exceptions.ExceptionFactory.Errors;
import com.tfg.exceptions.errors.RestException;

/**
 * Clase que instancia el patrón Singleton para tener una única instancia que se usa en todo el sistema
 * y evitar múltiples instancias de una misma clase.
 * @author gcollada
 *
 */
public class ContextManager {
	
	/**
	 * Instancia de la clase
	 */
	private static ContextManager cm = null;
	/**
	 * Fichero de propiedades relacionado con azure.properties
	 */
	private Properties prop;
	/**
	 * Cliente HTTP de Apache
	 */
	private HttpClient httpclient;
	
	private ContextManager() {
		readProperties();
		createHttpClient();
	}
	
	/**
	 * Método que obtiene la instancia de la clase ya creada, o crea una nueva si no existe
	 * @return La instancia de ContextManager
	 */
	public static ContextManager getInstance() {
		if(cm == null) {
			cm = new ContextManager();
		}
		return cm;
	}

	/**
	 * Load a properties file from class path, inside static method --> prop.load
	 */
	@SuppressWarnings("serial")
	private void readProperties() {
		try (InputStream input = ContextManager.class.getClassLoader().getResourceAsStream("azure.properties")) {
			prop = new Properties();
			if (input == null) {
				throw ExceptionFactory.getError(Errors.AZURE_PROPERTIES);
			}
			prop.load(input);
			
		} catch (Exception ex) {
			throw new RestException(ex,HttpStatus.INTERNAL_SERVER_ERROR) {
			};
		}
	}
	
	/**
	 * Create http client for http requests
	 */
	private void createHttpClient() {
		httpclient = HttpClients.createDefault();
	}
	
	/**
	 * Obtiene el valor de una propiedad del fichero de propiedades
	 * en base a su clave
	 * @param key La clave asociada al valor
	 * @return El valor de la propiedad
	 */
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	/**
	 * Obtiene el cliente HTTP de Apache
	 * @return El cliente HTTP
	 */
	public HttpClient getHttpClient() {
		return httpclient;
	}
	
	/**
	 * Cierra la conexión con el cliente Http de Apache
	 * @throws IOException
	 */
	public void closeHttpClient() throws IOException {
		((Closeable) httpclient).close();
	}

}
