package com.tfg.validators.fields;

import com.tfg.exceptions.FieldFormatException;
import com.tfg.model.Field;

import utils.OptionalFieldsManager;

public abstract class AbstractFieldValidator implements FieldValidator {

	OptionalFieldsManager op = OptionalFieldsManager.getInstance();;

	@Override
	public Object isValid(String field_name, String field_value) throws FieldFormatException {
		if (!field_value.isEmpty()) {
			return doConcreteValidation(field_value);
		} else if (isOptional(field_name))
			return "";// empty, it is optional
		else
			throw new FieldFormatException("Field " + field_name + " cannot be null. It is a mandatory field");
	}

	/**
	 * Method to check if the field whose name is the given one is optional.
	 * 
	 * @param field_name
	 * @return
	 */
	protected boolean isOptional(String field_name) {
		for (Field f : op.getOptionalFields()) {
			if (f.getNombre().equals(field_name))
				return true;
		}
		return false;
	}

	/**
	 * Abstract method belonging to the template method that allows each concrete
	 * class return its concrete type.
	 * @param field_value 
	 * 
	 * @return
	 * @throws FieldFormatException 
	 */
	public abstract Object doConcreteValidation(String field_value) throws FieldFormatException;

}
