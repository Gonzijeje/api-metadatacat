package com.tfg.exceptions;

import com.tfg.exceptions.errors.AssociationMetadataNoExistException;
import com.tfg.exceptions.errors.ElasticsearchConnectException;
import com.tfg.exceptions.errors.EntityNotFoundException;
import com.tfg.exceptions.errors.HttpClientErrorException;
import com.tfg.exceptions.errors.RestException;
import com.tfg.exceptions.errors.UniqueCodeException;
import com.tfg.exceptions.errors.ValueWithNoKeyException;

public class ExceptionFactory {
	
	public static enum Errors{
		UNIQUE_CODE,
		ENTITY_NOT_FOUND,
		VALUE_WITH_NO_KEY,
		ELASTICSEARCH_CONNECT,
		ASSOCIATIONS_NO_EXIST,
		HTTP_CLIENT_ERROR
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
		case ASSOCIATIONS_NO_EXIST:
			return new AssociationMetadataNoExistException();
		case HTTP_CLIENT_ERROR:
			return new HttpClientErrorException();
		}
		return null;
	}
	

}
