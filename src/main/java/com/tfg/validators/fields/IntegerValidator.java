package com.tfg.validators.fields;


public class IntegerValidator extends AbstractFieldValidator{

	@Override
	public Object doConcreteValidation(String field_value) {
		try {
			return Integer.parseInt(field_value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

}
