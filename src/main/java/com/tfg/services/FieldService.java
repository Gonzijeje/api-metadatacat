package com.tfg.services;

import java.util.List;

import com.tfg.model.Field;
import com.tfg.pojos.FieldModel;
import com.tfg.pojos.NewField;

/**
 * 
 * @author gcollada
 *
 */
public interface FieldService {
	
	FieldModel add(Field field);
	
	FieldModel update(String code, Field field);
	
	Field create(NewField newField);
	
	boolean delete(String codigo);
	
	FieldModel getCampoByCodigo(String nombre);
	
	List<FieldModel> getCampos();
	
	void addListCampos(List<String> campos);
	
}
