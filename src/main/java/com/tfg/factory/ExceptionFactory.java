package com.tfg.factory;

import com.tfg.exceptions.ElasticsearchConnectException;
import com.tfg.exceptions.EntityNotFoundException;
import com.tfg.exceptions.RestException;
import com.tfg.exceptions.UniqueCodeException;
import com.tfg.exceptions.ValueWithNoKeyException;

public class ExceptionFactory {
	
	public static enum Errors{
		UNIQUE_CODE,
		ENTITY_NOT_FOUND,
		VALUE_WITH_NO_KEY,
		ELASTICSEARCH_CONNECT
	}
	
	private ExceptionFactory() {
		
	}
	
	public static RestException getError(Errors error) {
		switch (error) {
		case UNIQUE_CODE:
			return new UniqueCodeException();
		case ENTITY_NOT_FOUND:
			return new EntityNotFoundException();
		case VALUE_WITH_NO_KEY:
			return new ValueWithNoKeyException();
		case ELASTICSEARCH_CONNECT:
			return new ElasticsearchConnectException();
		}
		return null;
	}
	

}
