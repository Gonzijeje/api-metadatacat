package com.tfg.exceptions.errors;

import org.springframework.http.HttpStatus;

public class ValueWithNoKeyException extends RestException {

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = 3904516547727962971L;

	public ValueWithNoKeyException() {
		super("Can´t search values of metadata with no code key", HttpStatus.BAD_REQUEST);
	}

}
