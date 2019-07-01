package com.tfg.exceptions;

import org.springframework.http.HttpStatus;

public class ElasticsearchConnectException extends RestException {

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = 4787082954349658426L;

	public ElasticsearchConnectException() {
		super("Connection with Elasticsearch server refused", HttpStatus.FORBIDDEN);
	}

}
