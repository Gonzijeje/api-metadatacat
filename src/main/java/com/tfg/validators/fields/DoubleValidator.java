package com.tfg.validators.fields;

import com.tfg.exceptions.FieldFormatException;
import com.tfg.validators.Validator;

public class DoubleValidator implements Validator {

	@Override
	public boolean isValid(String texto) {
		try {
			Double.parseDouble(texto);			
			return true;	
		} catch (NumberFormatException e) {
			try {
				throw new FieldFormatException("[Field format error] Change " + texto + " format to double");
			} catch (FieldFormatException e1) {
			}
		}
		return false;
	}

}
