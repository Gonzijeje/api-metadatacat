/*package com.tfg.validators.fields;

import com.tfg.exceptions.FieldFormatException;

public class LengthValidator extends AbstractFieldValidator {
	
	int specificLength;

	public LengthValidator(int specificLength) {
		this.specificLength = specificLength;
	}

	@Override
	public Object doConcreteValidation(String field_value) throws FieldFormatException {
		if (field_value.length() == specificLength) {
			return field_value;
		} else
			throw new FieldFormatException(
					"Length of field with value " + field_value + " should have length " + specificLength);
	}

}
*/
