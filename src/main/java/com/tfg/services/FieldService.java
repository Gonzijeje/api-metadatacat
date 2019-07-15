package com.tfg.services;

import java.util.List;

import com.tfg.model.Field;
import com.tfg.services.model.FieldModel;
import com.tfg.services.model.NewField;

/**
 * Interfaz de operaciones definidas relacionadas con el recurso Digital Twin
 * @author gcollada
 *
 */
public interface FieldService {
	
	/**
	 * Método para añadir un nuevo Field al sistema. Comprueba
	 * que no exista ya uno con el mismo código.
	 * @param field El objeto Field a insertar
	 * @return modelo JSON del objeto Field creado (tipo FieldModel)
	 */
	FieldModel add(Field field);
	
	/**
	 * Método para modficiar un Field del sistema.
	 * @param code Código del Field a modificar. Debe de existir en el sistema
	 * @param field Objeto Field con la información a modificar. El código no puede
	 * estar repetido salvo que sea el mismo sin modificar.
	 * @return modelo JSON del objeto Field modificado (tipo FieldModel)
	 */
	FieldModel update(String code, Field field);
	
	/**
	 * Método que sirve para obtener la entidad Field
	 * a partir del modelo JSON del nuevo Field
	 * @param newField Modelo JSON del nuevo Field a crear (tipo NewField)
	 * @return el Digital Asset creado
	 */
	Field create(NewField newField);
	
	/**
	 * Método que sirve para eliminar un Field del sistema
	 * @param codigo Código del Field a eliminar
	 */
	void delete(String codigo);
	
	/**
	 * Método para recuperar un Field del sistema
	 * @param code Código del Field que se desea recuperar. Debe de existir en el sistema.
	 * @return El objeto Field recuperado.
	 */
	Field getFieldByCode(String code);
	
	/**
	 * Método para recuperar todos los Fields del sistema.
	 * @return Lista con los modelos JSON de los Fields (tipo List<FieldModel>)
	 */
	List<FieldModel> getFields();
	
	/**
	 * Método para añadir una lista de Fields
	 * @param campos Lista de los Fields a insertar
	 */
	void addListFields(List<Field> campos);

	void addListFieldsCodes(List<String> fieldCodes);
	
}
