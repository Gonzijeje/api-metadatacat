package com.tfg.exceptions;

import org.springframework.http.HttpStatus;

public class AssociationMetadataNoExistException extends RestException{

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = -3598450346683253626L;

	public AssociationMetadataNoExistException() {
		super("Digital resource does not match given metadata associations",HttpStatus.NOT_FOUND);
	}

}
