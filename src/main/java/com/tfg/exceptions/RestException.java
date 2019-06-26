package com.tfg.exceptions;

import org.springframework.http.HttpStatus;

public abstract class RestException extends RuntimeException{

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = -7438291498474539649L;
	
	private HttpStatus httpStatus;

	public RestException(HttpStatus status) {
		super();
		this.httpStatus = status;
	}

	public RestException(String message, Throwable cause, HttpStatus status) {
		super(message, cause);
		this.httpStatus = status;
	}

	public RestException(String message, HttpStatus status) {
		super(message);
		this.httpStatus = status;
	}

	public RestException(Throwable cause, HttpStatus status) {
		super(cause);
		this.httpStatus = status;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}	

}
