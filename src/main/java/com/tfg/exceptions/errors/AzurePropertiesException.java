package com.tfg.exceptions.errors;

import org.springframework.http.HttpStatus;

/**
 * Excepción propia relacionada con que el fichero de propiedades de Azure no existe o es incorrecto
 * @author gcollada
 *
 */
public class AzurePropertiesException extends RestException {

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = 4787082954349658426L;

	public AzurePropertiesException() {
		super("Imposible encontrar el fichero azure.properties", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
