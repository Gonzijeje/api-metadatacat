package com.tfg.exceptions.errors;

import org.springframework.http.HttpStatus;

/**
 * Excepción propia relacionada con que ha ocurrido un problema al establecer la conexión con Elasticsearch
 * @author gcollada
 *
 */
public class ElasticsearchConnectException extends RestException {

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = 4787082954349658426L;

	public ElasticsearchConnectException() {
		super("Connection with Elasticsearch server refused", HttpStatus.FORBIDDEN);
	}

}
