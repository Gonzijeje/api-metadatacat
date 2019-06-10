package com.tfg.exceptions;

public class FieldFormatException extends Exception{

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = 2660074672299616639L;
	
	private final ErrorCode code;
	
	public FieldFormatException() {
		super();
	}
	
	public FieldFormatException(String message) {
		super(message);
	}
	

}
