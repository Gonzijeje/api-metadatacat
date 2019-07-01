package com.tfg.exceptions.errors;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends RestException {

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = -771988118499163930L;

	public EntityNotFoundException() {
		super("Entity with given code couldn't be found", HttpStatus.NOT_FOUND);
	}

}
