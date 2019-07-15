package com.tfg.services.adapters;

import java.util.ArrayList;
import java.util.List;

import com.tfg.model.Field;
import com.tfg.services.model.FieldModel;
import com.tfg.services.model.NewField;

/**
 * Clase encargada de adaptar y realizar las transformaciones entre los
 * distintos modelos de un Field y su entidad
 * @author gcollada
 *
 */
public class FieldAdapter {
	
	/**
	 * Método encargado de transformar un modelo de nuevo Field a una entidad Field
	 * @param newField El módelo JSON de tipo NewField
	 * @return un objeto Field entidad
	 */
	public static Field getFieldEntity(NewField newField) {
		Field field = new Field();
		field.setCodigo(newField.getCode());
		field.setDescripcion(newField.getDescription());
		return field;
	}
	
	/**
	 * Método encargado de transformar una entidad Field a un modelo FieldModel
	 * @param field El objeto Field entidad
	 * @return un objeto FieldModel
	 */
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
	
	public static Field getFieldEntity(FieldModel fieldModel) {
		Field field = new Field();
		field.setCodigo(fieldModel.getCode());
		field.setDescripcion(fieldModel.getDescription());
		return field;
	}

}
