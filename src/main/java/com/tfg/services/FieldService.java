package com.tfg.services;

import java.util.List;

import com.tfg.model.Field;
import com.tfg.services.model.FieldModel;
import com.tfg.services.model.NewField;

/**
 * 
 * @author gcollada
 *
 */
public interface FieldService {
	
	FieldModel add(Field field);
	
	FieldModel update(String code, Field field);
	
	Field create(NewField newField);
	
	void delete(String codigo);
	
	Field getCampoByCodigo(String nombre);
	
	List<FieldModel> getCampos();
	
	void addListCampos(List<Field> campos);

	void addListCamposCodes(List<String> fieldCodes);
	
}
