package com.tfg.validators.fields;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.tfg.exceptions.FieldFormatException;
import com.tfg.validators.Validator;

public class DateValidator implements Validator {

	public final static String default_data_format="dd/mm/yyyy";
	
	/**
	 * Checks if the format of the field matches default_data_format.
	 * 
	 * @throws FieldFormatException
	 */
	@Override
	public boolean isValid(String texto) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(default_data_format);
		try{
			dateFormat.setLenient(false);
			dateFormat.parse(texto);			
			return true;
		} catch (ParseException pe) {			
			try {
				throw new FieldFormatException("[Field format error] Change date format from " + texto + " to "+default_data_format);
			} catch (FieldFormatException e) {
			}
		}
		return false;
	}

}
