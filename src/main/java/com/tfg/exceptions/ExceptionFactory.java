package com.tfg.exceptions;

import com.tfg.exceptions.errors.AssociationMetadataNoExistException;
import com.tfg.exceptions.errors.AzurePropertiesException;
import com.tfg.exceptions.errors.ElasticsearchConnectException;
import com.tfg.exceptions.errors.EntityNotFoundException;
import com.tfg.exceptions.errors.HttpClientErrorException;
import com.tfg.exceptions.errors.RestException;
import com.tfg.exceptions.errors.UniqueCodeException;
import com.tfg.exceptions.errors.ValueWithNoKeyException;
/**
 * Clase que sigue el patrón Abstract Factory y funciona como una factoría
 * de creación de excepciones propias en la aplicación.
 * @author gcollada
 *
 */
public class ExceptionFactory {
	
	/**
	 * Lista con todas las excepciones propias que existen en la aplicación
	 * @author gcollada
	 *
	 */
	public static enum Errors{
		UNIQUE_CODE,
		ENTITY_NOT_FOUND,
		VALUE_WITH_NO_KEY,
		ELASTICSEARCH_CONNECT,
		ASSOCIATIONS_NO_EXIST,
		HTTP_CLIENT_ERROR,
		AZURE_PROPERTIES
	}
	
	private ExceptionFactory() {
		
	}
	
	/**
	 * Método que obtiene el código de error y devuelve la excepción adecuada en cada caso
	 * @param error El código de error con el que se invoca la creación de la excepción
	 * @return La excepción correspondiente al código
	 */
	public static RestException getError(Errors error) {
		switch (error) {
		case UNIQUE_CODE:
			return new UniqueCodeException();
		case ENTITY_NOT_FOUND:
			return new EntityNotFoundException();
		case VALUE_WITH_NO_KEY:
			return new ValueWithNoKeyException();
		case ELASTICSEARCH_CONNECT:
			return new ElasticsearchConnectException();
		case ASSOCIATIONS_NO_EXIST:
			return new AssociationMetadataNoExistException();
		case HTTP_CLIENT_ERROR:
			return new HttpClientErrorException();
		case AZURE_PROPERTIES:
			return new AzurePropertiesException();
		}
		return null;
	}
	

}
