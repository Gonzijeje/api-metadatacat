package com.tfg.validators.entities;

import java.util.Map;

public interface EntityValidator {

	/**
	 * This method is intended to check that the parameters of the specific entity it
	 * checks match the expected format, this is, the previously defined and
	 * established format. Each concrete strategy will check their corresponding
	 * entity.
	 * Catches FieldFormatException if some of the fields are not in the expected format.
	 * 
	 * @param json
	 * @return
	 */
	boolean checkValidFields(Map<String, Object> json);

}
