package com.tfg.validators.fields;

import com.tfg.exceptions.FieldFormatException;

/**
 * STRATEGY to check if the fields have the expected format. Each concrete
 * strategy checks one(or, in the case of the composite strategy, many) aspect/s
 * that needs to be fulfilled.
 * 
 * @author acotorei
 *
 */
public interface FieldValidator {

	/**
	 * Method to check if the given field matches the expected format and is,
	 * therefore, valid.
	 * 
	 * @param field_name
	 * @param string 
	 * @return field with its corresponding type if valid, null otherwise.
	 * @throws FieldFormatException in case the field does not have the expected format
	 */
	Object isValid(String field_name, String field_value) throws FieldFormatException;

}
