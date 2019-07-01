package com.tfg.exceptions.errors;

import org.springframework.http.HttpStatus;

public class UniqueCodeException extends RestException {

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = 8399901854802824173L;

	public UniqueCodeException() {
		super("Code given already exists in the system", HttpStatus.BAD_REQUEST);
	}

}
