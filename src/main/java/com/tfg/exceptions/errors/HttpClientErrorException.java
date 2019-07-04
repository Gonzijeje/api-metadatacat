package com.tfg.exceptions.errors;

import org.springframework.http.HttpStatus;

public class HttpClientErrorException extends RestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3386455486238218003L;

	public HttpClientErrorException() {
		super("Protocolo Http Client has suffered an error, and connection with Azure"
				+ "could not be completed", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
