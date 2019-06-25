package com.tfg.adapters;

import java.util.ArrayList;
import java.util.List;

import com.tfg.model.Field;
import com.tfg.pojos.FieldModel;
import com.tfg.pojos.NewField;

public class FieldAdapter {
	
	public static Field getFieldEntity(NewField newField) {
		Field field = new Field();
		field.setCodigo(newField.getCode());
		field.setDescripcion(newField.getDescription());
		return field;
	}
	
	public static FieldModel getFieldModel(Field field) {
		FieldModel model = new FieldModel();
		model.setCode(field.getCodigo());
		model.setDescription(field.getDescripcion());
		return model;
	}
	
	public static List<FieldModel> getFieldModel(Iterable<Field> fields) {
		List<FieldModel> models = new ArrayList<FieldModel>();
		fields.forEach((field)->{
			models.add(getFieldModel(field));
		});
		return models;
	}

}
