package com.tfg.validators.fields;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.tfg.exceptions.FieldFormatException;

public class DateValidator extends AbstractFieldValidator {

	public final static String default_data_format="dd/mm/yyyy";
	
	/**
	 * Checks if the format of the field matches default_data_format.
	 * 
	 * @throws FieldFormatException
	 */

	@Override
	public Object doConcreteValidation(String field_value) throws FieldFormatException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(default_data_format);
		try {
			return dateFormat.parse(field_value);
		} catch (ParseException pe) {
			throw new FieldFormatException("[Field format error] Change date format from " + field_value + " to "+default_data_format);
		}
	}

}
