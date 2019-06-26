package com.tfg.factory;

import com.tfg.exceptions.EntityNotFoundException;
import com.tfg.exceptions.RestException;
import com.tfg.exceptions.UniqueCodeException;

public class ExceptionFactory {
	
	public static enum Errors{
		UNIQUE_CODE,
		ENTITY_NOT_FOUND
	}
	
	private ExceptionFactory() {
		
	}
	
	public static RestException getError(Errors error) {
		switch (error) {
		case UNIQUE_CODE:
			return new UniqueCodeException();
		case ENTITY_NOT_FOUND:
			return new EntityNotFoundException();
		}
		return null;
	}
	

}
