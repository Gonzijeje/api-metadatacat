package com.tfg.factory;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RestException extends RuntimeException{

	/**
	 * Serializable.
	 */
	private static final long serialVersionUID = 3717551148332878094L;
	
	/* LO DE CULTIVARUNTIMEEXCEPTION de la que heredaba la RestException y ServiceRuntimeException
	
	public CultivaRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}


	public CultivaRuntimeException(final String message) {
		super(message);
	}

	public CultivaRuntimeException(final Throwable cause) {
		super(cause);
	}
	 */
	
	/**
	 * Timestamp of the error.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date errorTime = new Date();
	
	/**
	 * HTTP status code that should be returned to the client by the web service.
	 */
	private HttpStatus httpStatus;
	
	private String message;
	
    private String debugMessage;

	public RestException(HttpStatus httpStatus, Throwable ex) {
		super(ex);
		this.httpStatus = httpStatus;
	}
    
    
	
	

}
