package com.tfg.validators.fields;

public class DoubleValidator extends AbstractFieldValidator {

	@Override
	public Object doConcreteValidation(String field_value) {
		return Double.parseDouble(field_value);
	}

}
