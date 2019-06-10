package com.tfg.validators.entities;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.tfg.exceptions.FieldFormatException;
import com.tfg.validators.fields.DateValidator;
import com.tfg.validators.fields.DoubleValidator;
import com.tfg.validators.fields.FieldValidator;
import com.tfg.validators.fields.IntegerValidator;
import com.tfg.validators.fields.TextValidator;

@Service
public class SystemValidator implements EntityValidator {
	public FieldValidator doubleValidator = new DoubleValidator();
	public FieldValidator textValidator = new TextValidator();
	public FieldValidator dateValidator = new DateValidator();
	public FieldValidator integerValidator = new IntegerValidator();

	@Override
	public boolean checkValidFields(Map<String, Object> json) {
//		CheckAll()
		try {
			textValidator.isValid("uuid", json.get("uuid").toString());
			textValidator.isValid("description", json.get("description").toString());
			textValidator.isValid("name", json.get("name").toString());
			textValidator.isValid("type", json.get("type").toString());
			textValidator.isValid("production_contact", json.get("production_contact").toString());
			textValidator.isValid("maintenance_contact", json.get("maintenance_contact").toString());
			textValidator.isValid("manufacturer_name", json.get("manufacturer_name").toString());
			textValidator.isValid("manufacturer_model", json.get("manufacturer_model").toString());
			textValidator.isValid("manufacturer_serial", json.get("manufacturer_serial").toString());
			textValidator.isValid("manufacturer_features", json.get("manufacturer_features").toString());
			textValidator.isValid("manufacturer_documents", json.get("manufacturer_documents").toString());
			textValidator.isValid("coord_origin", json.get("coord_origin").toString());

			doubleValidator.isValid("abs_pos_latitude", json.get("abs_pos_latitude").toString());
			doubleValidator.isValid("abs_pos_longitude", json.get("abs_pos_longitude").toString());
			doubleValidator.isValid("altitude", json.get("altitude").toString());

			textValidator.isValid("reference_system", "reference_system").toString();

			doubleValidator.isValid("rel_pos_x", json.get("rel_pos_x").toString());
			doubleValidator.isValid("rel_pos_y", json.get("rel_pos_y").toString());
			doubleValidator.isValid("rel_pos_z", json.get("rel_pos_z").toString());
			doubleValidator.isValid("dimension_x", json.get("dimension_x").toString());
			doubleValidator.isValid("dimension_y", json.get("dimension_y").toString());
			doubleValidator.isValid("dimension_z", json.get("dimension_z").toString());
			doubleValidator.isValid("dimension_diameter", json.get("dimension_diameter").toString());
			doubleValidator.isValid("dimension_length", json.get("dimension_length").toString());

			textValidator.isValid("parent_uuid", ("parent_uuid").toString());
			textValidator.isValid("child_uuid", json.get("child_uuid").toString());
			textValidator.isValid("link_uuid", json.get("link_uuid").toString());
			textValidator.isValid("property_uuid", json.get("property_uuid").toString());

			doubleValidator.isValid("property_very_low", json.get("property_very_low").toString());
			doubleValidator.isValid("property_low", json.get("property_low").toString());
			doubleValidator.isValid("property_high", json.get("property_high").toString());
			doubleValidator.isValid("property_very_high", json.get("property_very_high").toString());

			textValidator.isValid("event_name", json.get("event_name").toString());
			dateValidator.isValid("event_date", json.get("event_date").toString());
			textValidator.isValid("event_description", json.get("event_description").toString());
			integerValidator.isValid("event_initial_status", json.get("event_initial_status").toString());
			integerValidator.isValid("event_validity_period", json.get("event_validity_period").toString());
			textValidator.isValid("event_time_units", json.get("event_time_units").toString());

			doubleValidator.isValid("point_time", json.get("point_time").toString());
			doubleValidator.isValid("point_value", json.get("point_value").toString());
			
			return true;

		} catch (FieldFormatException e) {
			e.printStackTrace();
		}

		return false;
	}

}
